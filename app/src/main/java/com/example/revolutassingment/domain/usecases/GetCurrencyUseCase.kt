package com.example.revolutassingment.domain.usecases

import java.util.*
import javax.inject.Inject

const val DEFAULT_CURRENCY = "EUR"

class GetCurrencyUseCase @Inject constructor() {

    operator fun invoke(): String {
        val currency = Currency.getInstance(Locale.getDefault())
        val symbol = currency.currencyCode

        return if (symbol.isEmpty()) {
            DEFAULT_CURRENCY
        } else {
            symbol
        }
    }
}