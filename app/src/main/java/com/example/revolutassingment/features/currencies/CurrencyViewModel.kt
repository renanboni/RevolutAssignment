package com.example.revolutassingment.features.currencies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.revolutassingment.domain.domain.GetRatesUseCase
import com.example.revolutassingment.domain.entities.Rate
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import javax.inject.Inject

class CurrencyViewModel @Inject constructor(
    private val getRatesUseCase: GetRatesUseCase
) : ViewModel() {

    private val viewState = MutableLiveData<CurrencyViewState>()
    val currencyViewState: LiveData<CurrencyViewState> = viewState

    private var disposables = CompositeDisposable()

    fun getRates() {
        disposables.add(getRatesUseCase.execute()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { viewState.postValue(CurrencyViewState.ShowLoading) }
            .subscribe({
                viewState.postValue(CurrencyViewState.HideLoading)
                viewState.postValue(CurrencyViewState.Rates(it.rates))
            }, {
                viewState.postValue(CurrencyViewState.HideLoading)
                viewState.postValue(CurrencyViewState.Error(it.localizedMessage.orEmpty()))
            })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    sealed class CurrencyViewState {
        object ShowLoading : CurrencyViewState()
        object HideLoading : CurrencyViewState()
        data class Rates(val rates: List<Rate>) : CurrencyViewState()
        data class Error(val message: String) : CurrencyViewState()
    }
}