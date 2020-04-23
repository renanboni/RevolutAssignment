package com.example.revolutassingment.domain.usecases

import com.example.revolutassingment.TestSchedulerProvider
import com.example.revolutassingment.domain.entities.Currency
import com.example.revolutassingment.domain.entities.Rate
import com.example.revolutassingment.domain.repository.CurrencyRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Test

class GetRatesUseCaseTest {

    private val repository: CurrencyRepository = mock()

    private val getRates = GetRatesUseCase(repository, TestSchedulerProvider())

    @Test
    fun `GIVEN non empty currency THEN getRates SHOULD complete`() {
        whenever(repository.getRates(any())).thenReturn(Observable.just(getCurrency()))

        getRates("BRL")
            .test()
            .assertComplete()
    }

    @Test
    fun `GIVEN non empty currency THEN getRates SHOULD return Currency`() {
        val currency = getCurrency()

        whenever(repository.getRates(any())).thenReturn(Observable.just(currency))

        getRates("BRL")
            .test()
            .assertValue(currency)
    }

    @Test
    fun `GIVEN empty currency THEN getRates SHOULD return Currency`() {
        val currency = getCurrency()

        whenever(repository.getRates(any())).thenReturn(Observable.just(currency))

        getRates()
            .test()
            .assertValue(currency)
    }

    private fun getCurrency(): Currency {
        return Currency(
            baseCurrency = "BRL", rates = listOf(
                Rate("EUR", 4.0)
            )
        )
    }
}