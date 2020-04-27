package com.example.revolutassingment.core

import com.example.revolutassingment.domain.entities.Currency
import com.example.revolutassingment.domain.entities.Rate

interface CurrencyCalculator {
    fun onNewBaseCurrencySelected(currency: String, amount: Double, currentList: MutableList<Rate>): MutableList<Rate>
    fun onNewRates(currency: Currency): List<Rate>
    fun onCurrencyChanged(currency: String, amount: Double): MutableList<Rate>
}
