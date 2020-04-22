package com.example.revolutassingment.features.currencies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.revolutassingment.domain.entities.Currency
import com.example.revolutassingment.domain.entities.Rate
import com.example.revolutassingment.domain.usecases.GetRatesUseCase
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val DEFAULT_VALUE = 1.0

class CurrencyViewModel @Inject constructor(
    private val getRatesUseCase: GetRatesUseCase
) : ViewModel() {

    private val ratesState = MutableLiveData<MutableList<Rate>>()
    val ratesViewState: LiveData<MutableList<Rate>> = ratesState

    private val loadingState = MutableLiveData<Boolean>()
    val loadingViewState: LiveData<Boolean> = loadingState

    private val errorState = MutableLiveData<Unit>()
    val errorViewState: LiveData<Unit> = errorState

    private var disposables = CompositeDisposable()

    init {
        getRates()
    }

    fun getRates(currency: String? = "", value: Double = 0.0) {
        if (isRateUpdatedByUser(currency, value)) {
            disposables.clear()
        }

        disposables.add(Observable.interval(1, TimeUnit.SECONDS)
            .flatMap { getRatesUseCase(currency) }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                onGetRatesSuccess(it, value)
            }, {
                errorState.postValue(Unit)
            })
        )
    }

    private fun onGetRatesSuccess(
        currency: Currency,
        value: Double
    ) {
        val rates = currency.rates.toMutableList()
        rates.add(0, Rate(currency.baseCurrency, DEFAULT_VALUE))

        if (value > 0) {
            rates.map { it.value = it.value * value }
        }

        ratesState.postValue(rates)
    }

    private fun isRateUpdatedByUser(currency: String?, value: Double) =
        !currency.isNullOrEmpty() && value >= 1

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}