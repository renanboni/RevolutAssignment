package com.example.revolutassingment.data.service

import com.example.revolutassingment.data.dto.CurrencyDto
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET

interface CurrencyService {

    @GET("")
    fun getRates(): Observable<CurrencyDto>
}