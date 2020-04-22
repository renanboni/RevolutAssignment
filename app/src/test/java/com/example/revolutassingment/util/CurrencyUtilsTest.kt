package com.example.revolutassingment.util

import org.junit.Assert.*
import org.junit.Test

class CurrencyUtilsTest {

    @Test
    fun `GIVEN BRL string THEN CurrencyUtils SHOULD return ic_eur`() {
        val code = "BRL"

        assertEquals(CurrencyUtils.normalizeCode(code), "ic_brl")
    }

    @Test
    fun `GIVEN a empty value THEN CurrencyUtils SHOULD return a default value`() {
        val value = ""

        assertEquals(CurrencyUtils.normalizeValue(value), 1.0, 0.0)
    }

    @Test
    fun `GIVEN an invalid value THEN CurrencyUtils SHOULD return a default value`() {
        val value = "."

        assertEquals(CurrencyUtils.normalizeValue(value), 1.0, 0.0)
    }

    @Test
    fun `GIVEN a valid value THEN CurrencyUtils SHOULD return a double value`() {
        val value = "4.0"

        assertEquals(CurrencyUtils.normalizeValue(value), 4.0, 0.0)
    }
}