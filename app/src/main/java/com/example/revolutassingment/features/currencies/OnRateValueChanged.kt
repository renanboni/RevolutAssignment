package com.example.revolutassingment.features.currencies

import com.example.revolutassingment.domain.entities.Rate

interface OnRateValueChanged {
    fun onRateChanged(currency: String, value: Double)
    fun onBaseCurrencyChanged(currency: String, value: Double, currentList: MutableList<Rate>)
}