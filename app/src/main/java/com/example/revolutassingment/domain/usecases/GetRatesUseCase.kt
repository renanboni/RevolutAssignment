package com.example.revolutassingment.domain.usecases

import com.example.revolutassingment.domain.repository.CurrencyRepository
import javax.inject.Inject

class GetRatesUseCase @Inject constructor(private val currencyRepository: CurrencyRepository) {
    operator fun invoke() = currencyRepository.getRates()
}