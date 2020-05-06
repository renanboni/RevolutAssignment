package com.example.revolutassingment.domain.usecases

import com.example.revolutassingment.domain.SchedulerProvider
import com.example.revolutassingment.domain.entities.Currency
import com.example.revolutassingment.domain.repository.CurrencyRepository
import io.reactivex.Observable
import java.util.Locale
import javax.inject.Inject

class GetRatesUseCase @Inject constructor(
    private val currencyRepository: CurrencyRepository,
    private val scheduler: SchedulerProvider
) {
    operator fun invoke(currency: String): Observable<Currency> {
        return currencyRepository.getRates(currency)
            .subscribeOn(scheduler.io)
    }
}