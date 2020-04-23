package com.example.revolutassingment.di.component

import android.content.Context
import com.example.revolutassingment.RevolutApp
import com.example.revolutassingment.di.module.AppModule
import com.example.revolutassingment.di.module.CurrencyModule
import com.example.revolutassingment.di.module.NetworkModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        CurrencyModule::class,
        NetworkModule::class,
        AppModule::class
    ]
)
interface AppComponent : AndroidInjector<RevolutApp> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): AppComponent
    }
}