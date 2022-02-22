package com.example.cryptocomposeexample.module

import com.example.cryptocomposeexample.repo.CryptoRepository
import com.example.cryptocomposeexample.util.Constants.BASE_URL
import com.example.cryptocomposeexample.service.CryptoAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideCryptoRepository(
        api: CryptoAPI
    ) = CryptoRepository(api = api)

    @Singleton
    @Provides
    fun provideCryptoApi(): CryptoAPI {
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(CryptoAPI::class.java)
    }
}