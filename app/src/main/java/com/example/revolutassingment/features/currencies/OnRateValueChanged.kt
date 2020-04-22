package com.example.revolutassingment.features.currencies

interface OnRateValueChanged {
    fun onChanged(currency: String, value: Double)
}