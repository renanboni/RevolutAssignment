package com.example.revolutassingment.core

import com.example.revolutassingment.domain.entities.Currency
import com.example.revolutassingment.domain.entities.Rate
import javax.inject.Inject

class CurrencyCalculatorImpl @Inject constructor() : CurrencyCalculator {

    private var currencyRates = mutableListOf<Rate>()

    private var amount = 1.0
    private var baseCurrency = ""

    override fun onNewBaseCurrencySelected(currency: String, amount: Double, currentList: MutableList<Rate>): MutableList<Rate> {
        this.amount = amount
        this.baseCurrency = currency

        currencyRates.find { it.symbol == currency }?.let { swap(it) }

        val list = currencyRates
            .toMutableList()

        list.forEach { rate ->
            currentList.find { it.symbol == rate.symbol }?.let {
                rate.value = it.value
            }
        }

        return list
    }

    private fun swap(rate: Rate) {
        val first = currencyRates.first()
        val rateIndex = currencyRates.indexOf(rate)

        currencyRates[0] = rate
        currencyRates[rateIndex] = first
    }

    override fun onNewRates(currency: Currency): List<Rate> {
        if (currencyRates.isEmpty()) {
            baseCurrency = currency.baseCurrency

            currencyRates = currency.rates.toMutableList()
            currencyRates.add(0, Rate(currency.baseCurrency, amount))
            return currencyRates
        } else {
            currency.rates.toMutableList().forEach { new ->
                currencyRates.find { old -> new.symbol == old.symbol }?.let {
                    it.value = new.value
                }
            }

            val list = currencyRates
                .map { Rate(it.symbol, it.value * amount) }
                .toMutableList()

            list.find { it.symbol == baseCurrency }?.value = amount

            return list
        }
    }

    override fun onCurrencyChanged(currency: String, amount: Double): MutableList<Rate> {
        this.amount = amount

       val list = currencyRates
           .map { Rate(it.symbol, it.value * amount) }
           .toMutableList()

        list.find { it.symbol == currency }?.value = this.amount

        return list
    }
}


