package com.example.revolutassingment.domain.usecases

import com.example.revolutassingment.domain.SchedulerProvider
import com.example.revolutassingment.domain.entities.Currency
import com.example.revolutassingment.domain.repository.CurrencyRepository
import io.reactivex.Observable
import java.util.Locale
import javax.inject.Inject

private const val DEFAULT_CURRENCY = "EUR"

class GetRatesUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository,
    private val scheduler: SchedulerProvider
) {
    operator fun invoke(currency: String? = ""): Observable<Currency> {
        return if (currency.isNullOrEmpty()) {
            currencyRepository.getRates(getCurrency())
                .subscribeOn(scheduler.io)
                .observeOn(scheduler.mainThread)
        } else {
            currencyRepository.getRates(currency)
                .subscribeOn(scheduler.io)
                .observeOn(scheduler.mainThread)
        }
    }

    private fun getCurrency(): String {
        val currency = java.util.Currency.getInstance(Locale.getDefault())
        val symbol = currency.currencyCode

        return if (symbol.isEmpty()) {
            DEFAULT_CURRENCY
        } else {
            symbol
        }
    }
}