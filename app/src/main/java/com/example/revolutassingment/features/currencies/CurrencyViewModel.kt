package com.example.revolutassingment.features.currencies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.revolutassingment.domain.entities.Currency
import com.example.revolutassingment.domain.entities.Rate
import com.example.revolutassingment.domain.usecases.GetRatesUseCase
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

private const val DEFAULT_VALUE = 1.0

class CurrencyViewModel @Inject constructor(
    private val getRatesUseCase: GetRatesUseCase
) : ViewModel() {

    private val ratesState = MutableLiveData<MutableList<Rate>>()
    val ratesViewState: LiveData<MutableList<Rate>> = ratesState

    private val errorState = MutableLiveData<Unit>()
    val errorViewState: LiveData<Unit> = errorState

    private var disposables = CompositeDisposable()

    init {
        getRates()
    }

    fun getRates(currency: String? = "", amount: Double = 0.0) {
        if (isRateUpdatedByUser(currency)) {
            disposables.clear()
        }

        disposables.add(Observable.interval(1, TimeUnit.SECONDS)
            .flatMap { getRatesUseCase(currency) }
            .distinctUntilChanged()
            .subscribe({
                onGetRatesSuccess(it, amount)
            }, {
                errorState.postValue(Unit)
            })
        )
    }

    private fun onGetRatesSuccess(
        currency: Currency,
        amount: Double
    ) {
        val rates = currency.rates.toMutableList()
        rates.add(0, Rate(currency.baseCurrency, DEFAULT_VALUE))

        if (amount > 0) {
            rates.map { it.value = it.value * amount }
        }

        ratesState.postValue(rates)
    }

    private fun isRateUpdatedByUser(currency: String?) = !currency.isNullOrEmpty()

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}