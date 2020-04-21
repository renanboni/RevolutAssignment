package com.example.revolutassingment.data.service

import com.example.revolutassingment.data.dto.CurrencyDto
import io.reactivex.rxjava3.core.Observable

interface CurrencyService {
    fun getRates(): Observable<CurrencyDto>
}