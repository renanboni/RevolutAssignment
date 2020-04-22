package com.example.revolutassingment.data

import com.example.revolutassingment.data.dto.CurrencyDto
import com.example.revolutassingment.data.mapper.CurrencyRemoteMapperImpl
import com.example.revolutassingment.data.service.CurrencyService
import com.example.revolutassingment.domain.entities.Currency
import com.example.revolutassingment.domain.entities.Rate
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import io.reactivex.Observable
import org.junit.Test
import org.mockito.ArgumentMatchers.anyString

class CurrencyRepositoryImplTest {

    private val service: CurrencyService = mock()
    private val mapper: CurrencyRemoteMapperImpl = mock()

    private val repository = CurrencyRepositoryImpl(service, mapper)

    @Test
    fun `WHEN getRates is called THEN repository SHOULD complete`() {
        whenever(service.getRates(anyString())).thenReturn(Observable.just(getCurrencyDto()))
        whenever(mapper.mapFromDto(any())).thenReturn(getCurrency())

        repository.getRates(anyString())
            .test()
            .assertComplete()
    }

    @Test
    fun `WHEN getRates is called THEN service SHOULD be called`() {
        whenever(service.getRates(anyString())).thenReturn(Observable.just(getCurrencyDto()))
        whenever(mapper.mapFromDto(any())).thenReturn(getCurrency())

        repository.getRates(anyString())

        verify(service).getRates(any())
    }

    @Test
    fun `WHEN getRates is called THEN service SHOULD return Currency`() {
        val currencyDto = getCurrencyDto()
        val currency = getCurrency()

        whenever(service.getRates(anyString())).thenReturn(Observable.just(currencyDto))
        whenever(mapper.mapFromDto(currencyDto)).thenReturn(currency)

        repository.getRates(anyString())
            .test()
            .assertValue(currency)
    }

    private fun getCurrencyDto(): CurrencyDto {
        return CurrencyDto(
            "BRL",
            rates = mapOf("EUR" to 4.0)
        )
    }

    private fun getCurrency(): Currency {
        return Currency(
            baseCurrency = "BRL", rates = listOf(
                Rate("EUR", 4.0)
            )
        )
    }
}










