package com.example.revolutassingment.di.module

import com.example.revolutassingment.data.CurrencyRepositoryImpl
import com.example.revolutassingment.data.dto.CurrencyDto
import com.example.revolutassingment.data.mapper.CurrencyRemoteMapper
import com.example.revolutassingment.data.mapper.CurrencyRemoteMapperImpl
import com.example.revolutassingment.data.service.CurrencyService
import com.example.revolutassingment.domain.entities.Currency
import com.example.revolutassingment.domain.repository.CurrencyRepository
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofit(
        gsonConverterFactory: GsonConverterFactory,
        okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://hiring.revolut.codes/")
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return Gson()
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        return OkHttpClient()
            .newBuilder()
            .build()
    }

    @Provides
    @Singleton
    fun providesGsonConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideCurrencyRepository(
        service: CurrencyService,
        mapper: CurrencyRemoteMapperImpl
    ): CurrencyRepository {
        return CurrencyRepositoryImpl(service, mapper)
    }

    @Singleton
    @Provides
    fun provideCurrencyService(retrofit: Retrofit): CurrencyService {
        return retrofit.create(CurrencyService::class.java)
    }
}