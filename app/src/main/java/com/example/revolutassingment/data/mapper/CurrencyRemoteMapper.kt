package com.example.revolutassingment.data.mapper

import com.example.revolutassingment.data.dto.CurrencyDto
import com.example.revolutassingment.domain.entities.Currency

interface CurrencyRemoteMapper<in D, out E> {
    fun mapFromDto(dto: CurrencyDto): Currency
}