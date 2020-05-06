package com.example.revolutassingment.features.currencies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.revolutassingment.domain.CurrencyCalculator
import com.example.revolutassingment.domain.entities.Rate
import com.example.revolutassingment.domain.usecases.GetCurrencyUseCase
import com.example.revolutassingment.domain.usecases.GetRatesUseCase
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class CurrencyViewModel @Inject constructor(
    private val getRatesUseCase: GetRatesUseCase,
    private val getCurrencyUseCase: GetCurrencyUseCase,
    private val currencyCalculator: CurrencyCalculator
) : ViewModel() {

    private val ratesState = MutableLiveData<MutableList<Rate>>()
    val ratesViewState: LiveData<MutableList<Rate>> = ratesState

    private val errorState = MutableLiveData<Unit>()
    val errorViewState: LiveData<Unit> = errorState

    private var disposables = CompositeDisposable()

    fun updateRates(currency: String, amount: Double) {
        ratesState.value = (currencyCalculator.onCurrencyChanged(currency, amount))
    }

    fun updateBaseCurrency(currency: String, amount: Double, currentList: MutableList<Rate>) {
        getRates(currency, amount, true, currentList)
    }

    fun getRates(
        currency: String = "",
        amount: Double = 1.0,
        shouldUpdate: Boolean = false,
        currentList: MutableList<Rate> = mutableListOf()
    ) {
        if (!ratesState.value.isNullOrEmpty() && !shouldUpdate) {
            return
        }

        if (shouldUpdate) {
            disposables.clear()
            ratesState.value = (currencyCalculator.onNewBaseCurrencySelected(currency, amount, currentList).toMutableList())
        }

        disposables.add(Observable.interval(1, TimeUnit.SECONDS)
            .flatMap {
                if (currency.isEmpty()) {
                    getRatesUseCase(getCurrencyUseCase())
                } else {
                    getRatesUseCase(currency)
                }
            }
            .subscribe({
                ratesState.postValue(currencyCalculator.onNewRates(it).toMutableList())
            }, {
                errorState.postValue(Unit)
            })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}