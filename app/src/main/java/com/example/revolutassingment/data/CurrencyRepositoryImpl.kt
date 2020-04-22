package com.example.revolutassingment.data

import com.example.revolutassingment.data.mapper.CurrencyRemoteMapperImpl
import com.example.revolutassingment.data.service.CurrencyService
import com.example.revolutassingment.domain.entities.Currency
import com.example.revolutassingment.domain.repository.CurrencyRepository
import io.reactivex.Observable
import javax.inject.Inject

class CurrencyRepositoryImpl @Inject constructor (
    private val service: CurrencyService,
    private val mapper: CurrencyRemoteMapperImpl
) : CurrencyRepository {

    override fun getRates(currency: String): Observable<Currency> {
        return service.getRates(currency).map { mapper.mapFromDto(it) }
    }
}