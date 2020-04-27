package com.example.revolutassingment.util

import java.util.Currency
import java.util.Locale

object CurrencyUtils {
    private const val PREFIX = "ic_"
    private const val DEFAULT_VALUE = 1.0

    fun getCurrencySymbol(currency: String): String {
        return Currency.getInstance(currency).displayName
    }

    fun normalizeCode(code: String): String {
        return if (code.length == 3) {
            "$PREFIX${code.toLowerCase(Locale.getDefault())}"
        } else {
            ""
        }
    }

    fun normalizeValue(value: String): Double {
        return if (value.isEmpty()) {
            DEFAULT_VALUE
        } else {
            return try {
                value.toDouble()
            } catch (e: NumberFormatException) {
                DEFAULT_VALUE
            }
        }
    }
}

