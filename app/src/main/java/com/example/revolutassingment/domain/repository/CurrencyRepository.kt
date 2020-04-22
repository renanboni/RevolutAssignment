package com.example.revolutassingment.domain.repository

import com.example.revolutassingment.domain.entities.Currency
import io.reactivex.Observable

interface CurrencyRepository {
    fun getRates(): Observable<Currency>
}