package com.example.cryptocomposeexample.service

import com.example.cryptocomposeexample.model.CryptoDetailList
import com.example.cryptocomposeexample.model.CryptoList
import retrofit2.http.GET
import retrofit2.http.Query

interface CryptoAPI {
    //prices?key=742948de910213fceb505caa80b43557af621737

    @GET("prices")
    suspend fun getCryptoList(
        @Query("key") key: String
    ): CryptoList

    @GET("currencies")
    suspend fun getCrypto(
        @Query("key") key: String,
        @Query("ids") id: String,
        @Query("attributes") attributes: String,
    ) : CryptoDetailList
}