package com.example.revolutassingment.di.module

import androidx.lifecycle.ViewModel
import com.example.revolutassingment.di.ViewModelFactoryModule
import com.example.revolutassingment.di.ViewModelKey
import com.example.revolutassingment.domain.CurrencyCalculator
import com.example.revolutassingment.domain.CurrencyCalculatorImpl
import com.example.revolutassingment.features.currencies.CurrencyFragment
import com.example.revolutassingment.features.currencies.CurrencyViewModel
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class CurrencyModule {

    @ContributesAndroidInjector(modules = [ViewModelFactoryModule::class])
    internal abstract fun currencyFragment(): CurrencyFragment

    @Binds
    @IntoMap
    @ViewModelKey(CurrencyViewModel::class)
    abstract fun bindViewModel(viewModel: CurrencyViewModel): ViewModel

    @Binds
    abstract fun bindsCurrencyCalculator(currencyCalculatorImpl: CurrencyCalculatorImpl): CurrencyCalculator
}