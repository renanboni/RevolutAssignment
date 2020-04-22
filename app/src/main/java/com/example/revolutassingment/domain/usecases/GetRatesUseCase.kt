package com.example.revolutassingment.domain.usecases

import com.example.revolutassingment.domain.entities.Currency
import com.example.revolutassingment.domain.repository.CurrencyRepository
import io.reactivex.Observable
import java.util.*
import javax.inject.Inject

private const val DEFAULT_CURRENCY = "EUR"

class GetRatesUseCase @Inject constructor(private val currencyRepository: CurrencyRepository) {
    operator fun invoke(currency: String? = ""): Observable<Currency> {
        return if (currency.isNullOrEmpty()) {
            currencyRepository.getRates(getCurrency())
        } else {
            currencyRepository.getRates(currency)
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