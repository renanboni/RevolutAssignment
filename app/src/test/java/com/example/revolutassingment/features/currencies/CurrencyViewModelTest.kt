package com.example.revolutassingment.features.currencies

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.revolutassingment.domain.entities.Currency
import com.example.revolutassingment.domain.entities.Rate
import com.example.revolutassingment.domain.usecases.GetRatesUseCase
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.times
import io.reactivex.Observable
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.TestScheduler
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule
import java.util.concurrent.TimeUnit
import kotlin.test.assertEquals

class CurrencyViewModelTest {

    @get:Rule
    var rule: TestRule = InstantTaskExecutorRule()

    private var getRatesUseCase: GetRatesUseCase = mock()
    private val rateObserver: Observer<MutableList<Rate>> = mock()
    private val errorObserver: Observer<Unit> = mock()

    private lateinit var currencyViewModel: CurrencyViewModel

    private val testScheduler = TestScheduler()

    @Before
    fun setup() {
        RxJavaPlugins.setComputationSchedulerHandler { testScheduler }

        currencyViewModel = CurrencyViewModel(getRatesUseCase)
        currencyViewModel.ratesViewState.observeForever(rateObserver)
        currencyViewModel.errorViewState.observeForever(errorObserver)
    }

    @Test
    fun `WHEN getRates is called THEN currencyViewModel SHOULD call use case`() {
        whenever(getRatesUseCase(any())).thenReturn(Observable.just(getCurrency()))

        currencyViewModel.getRates("EUR", 4.0)

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)

        verify(getRatesUseCase, times(1)).invoke(any())
    }

    @Test
    fun `WHEN getRates is called THEN currencyViewModel SHOULD emit currency`() {
        val currency = Currency(baseCurrency = "BRL", rates = listOf(Rate("EUR", 1.0)))
        val expectedCurrency =
            Currency(baseCurrency = "BRL", rates = listOf(Rate("BRL", 2.0), Rate("EUR", 2.0)))

        whenever(getRatesUseCase(any())).thenReturn(Observable.just(currency))

        currencyViewModel.getRates("BRL", 2.0)

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)

        argumentCaptor<MutableList<Rate>>().run {
            verify(rateObserver).onChanged(capture())

            assertEquals(firstValue, expectedCurrency.rates)
        }
    }

    @Test
    fun `WHEN getRates is called THEN currencyViewModel SHOULD throw exception`() {
        whenever(getRatesUseCase(any())).thenReturn(Observable.error(Throwable()))

        currencyViewModel.getRates("EUR", 4.0)

        testScheduler.advanceTimeBy(1, TimeUnit.SECONDS)

        argumentCaptor<Unit>().run {
            verify(errorObserver).onChanged(capture())
        }
    }

    private fun getCurrency(): Currency {
        return Currency(
            baseCurrency = "BRL", rates = listOf(
                Rate("EUR", 4.0)
            )
        )
    }
}