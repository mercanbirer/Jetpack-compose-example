package com.example.cryptocomposeexample.repo

import com.example.cryptocomposeexample.model.CryptoDetailList
import com.example.cryptocomposeexample.model.CryptoList
import com.example.cryptocomposeexample.util.Constants.API_KEY
import com.example.cryptocomposeexample.service.CryptoAPI
import com.example.cryptocomposeexample.util.Constants.CALL_ATTRIBUTES
import com.example.cryptocomposeexample.util.Resource
import dagger.hilt.android.scopes.ActivityScoped
import java.lang.Exception
import javax.inject.Inject

@ActivityScoped
class CryptoRepository @Inject constructor(
    private val api: CryptoAPI
) {
    suspend fun getCryptoList(): Resource<CryptoList> {
        val response = try {
            api.getCryptoList(API_KEY)
        } catch (e: Exception) {
            return Resource.Error("Error")
        }
        return Resource.Success(response)
    }

    suspend fun getCrypto(id : String) : Resource<CryptoDetailList>{
        val response = try {
            api.getCrypto(API_KEY, id, CALL_ATTRIBUTES)
        } catch (e: Exception) {
            return Resource.Error("Error")
        }
        return Resource.Success(response)
    }
}