package com.example.revolutassingment.features.currencies

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.revolutassingment.domain.entities.Rate
import com.example.revolutassingment.domain.usecases.GetRatesUseCase
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class CurrencyViewModel @Inject constructor(
    private val getRatesUseCase: GetRatesUseCase
) : ViewModel() {

    private val ratesState = MutableLiveData<List<Rate>>()
    val ratesViewState: LiveData<List<Rate>> = ratesState

    private val loadingState = MutableLiveData<Boolean>()
    val loadingViewState: LiveData<Boolean> = loadingState

    private val errorState = MutableLiveData<String>()
    val errorViewState: LiveData<String> = errorState

    private var disposables = CompositeDisposable()

    fun getRates() {
        disposables.add(getRatesUseCase()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { loadingState.postValue(true) }
            .doOnComplete { loadingState.postValue(false) }
            .subscribe({
                ratesState.postValue(it.rates)
            }, {
                errorState.postValue(it.localizedMessage.orEmpty())
            })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }
}