package com.example.revolutassingment.data.mapper

import com.example.revolutassingment.data.dto.CurrencyDto
import com.example.revolutassingment.domain.entities.Currency
import com.example.revolutassingment.domain.entities.Rate
import javax.inject.Inject

class CurrencyRemoteMapperImpl @Inject constructor() : CurrencyRemoteMapper<CurrencyDto, Currency> {
    override fun mapFromDto(dto: CurrencyDto): Currency {
        return Currency(
            baseCurrency = dto.baseCurrency.orEmpty(),
            rates = dto.rates
                .orEmpty()
                .map { Rate(symbol = it.key, value = it.value) })
    }
}