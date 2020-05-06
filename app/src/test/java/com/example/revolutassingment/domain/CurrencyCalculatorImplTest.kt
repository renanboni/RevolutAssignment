package com.example.revolutassingment.domain

import com.example.revolutassingment.domain.entities.Currency
import com.example.revolutassingment.domain.entities.Rate
import org.junit.Assert.*
import org.junit.Test

class CurrencyCalculatorImplTest {

    private val calculator = CurrencyCalculatorImpl()

    @Test
    fun `WHEN currency is multiplied THEN calculator SHOULD emit new entire list`() {
        calculator.currencyRates = getRateList()

        val newList = calculator.onCurrencyChanged("BRL", 2.0)

        assertEquals(newList, expectedList())
    }

    @Test
    fun `WHEN new base currency is selected THEN calculator SHOULD swap the currencies`() {
        calculator.currencyRates = getRateList()

        val newList = calculator.onNewBaseCurrencySelected("EUA", 2.0, getRateList())

        assertEquals(newList, newCurrencyExpectedList())
        assertEquals(calculator.amount, 2.0, 0.0)
        assertEquals(calculator.baseCurrency, "EUA")
    }

    @Test
    fun `WHEN new rates arrives THEN calculator SHOULD update the base currency`() {
        val newList = calculator.onNewRates(Currency(baseCurrency = "AUD", rates = getRateList()))

        assertEquals(calculator.currencyRates, newList)
        assertEquals(calculator.baseCurrency, "AUD")
    }

    private fun getRateList(): MutableList<Rate> {
        return mutableListOf(
            Rate("BRL", 2.0),
            Rate("EUA", 4.0),
            Rate("AUD", 5.0),
            Rate("CAD", 6.0)
        )
    }

    private fun expectedList(): MutableList<Rate> {
        return mutableListOf(
            Rate("BRL", 2.0),
            Rate("EUA", 8.0),
            Rate("AUD", 10.0),
            Rate("CAD", 12.0)
        )
    }

    private fun newCurrencyExpectedList(): MutableList<Rate> {
        return mutableListOf(
            Rate("EUA", 4.0),
            Rate("BRL", 2.0),
            Rate("AUD", 5.0),
            Rate("CAD", 6.0)
        )
    }
}