package com.example.revolutassingment.di.module

import com.example.revolutassingment.core.DefaultSchedulerProvider
import com.example.revolutassingment.domain.SchedulerProvider
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Singleton
    @Binds
    abstract fun provideScheduler(schedulerProvider: DefaultSchedulerProvider): SchedulerProvider
}