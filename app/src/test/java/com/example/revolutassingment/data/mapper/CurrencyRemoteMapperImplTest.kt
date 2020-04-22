package com.example.revolutassingment.data.mapper

import com.example.revolutassingment.data.dto.CurrencyDto
import org.junit.Assert
import org.junit.Test

class CurrencyRemoteMapperImplTest {

    private val mapper = CurrencyRemoteMapperImpl()

    @Test
    fun `GIVEN a CurrencyDTO THEN mapper SHOULD map it to Currency`() {
        val currencyDto = CurrencyDto(baseCurrency = "BRL", rates = mapOf("EUR" to 4.0, "AUD" to 4.5))

        val currency = mapper.mapFromDto(currencyDto)

        Assert.assertEquals(currencyDto.baseCurrency, currency.baseCurrency)
        Assert.assertEquals(currencyDto.rates.orEmpty().count(), currency.rates.size)
    }
}