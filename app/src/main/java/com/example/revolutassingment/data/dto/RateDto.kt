package com.example.revolutassingment.data.dto

import com.google.gson.annotations.SerializedName

data class RateDto(
    @SerializedName("symbol") val symbol: String? = "",
    @SerializedName("value") val value: Double? = 0.0
)