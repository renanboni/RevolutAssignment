package com.example.revolutassingment.data.dto

import com.google.gson.annotations.SerializedName

data class CurrencyDto(
    @SerializedName("baseCurrency") val baseCurrency: String? = "",
    @SerializedName("rates") val rates: List<RateDto>? = listOf()
)