package com.example.revolutassingment.data.service

import com.example.revolutassingment.data.dto.CurrencyDto
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyService {

    @GET("api/android/latest")
    fun getRates(@Query("base") currency: String): Observable<CurrencyDto>
}