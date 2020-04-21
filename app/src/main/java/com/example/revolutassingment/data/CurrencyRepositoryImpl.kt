package com.example.revolutassingment.data

import com.example.revolutassingment.data.dto.CurrencyDto
import com.example.revolutassingment.data.mapper.CurrencyRemoteMapper
import com.example.revolutassingment.data.service.CurrencyService
import com.example.revolutassingment.domain.entities.Currency
import com.example.revolutassingment.domain.repository.CurrencyRepository
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor(
    private val service: CurrencyService,
    private val mapper: CurrencyRemoteMapper<CurrencyDto, Currency>
) : CurrencyRepository {

    override fun getRates(): Observable<Currency> {
        return service.getRates().map { mapper.mapFromDto(it) }
    }
}