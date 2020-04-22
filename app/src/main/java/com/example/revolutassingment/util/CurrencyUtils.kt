package com.example.revolutassingment.util

import java.util.*

object CurrencyUtils {
    private const val PREFIX = "ic_"

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
}