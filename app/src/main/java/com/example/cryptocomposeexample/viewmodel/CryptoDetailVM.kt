package com.example.cryptocomposeexample.viewmodel

import androidx.lifecycle.ViewModel
import com.example.cryptocomposeexample.model.CryptoDetailList
import com.example.cryptocomposeexample.repo.CryptoRepository
import com.example.cryptocomposeexample.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CryptoDetailVM @Inject constructor(
    private val repository: CryptoRepository
) : ViewModel() {

    suspend fun getCrypto(id: String): Resource<CryptoDetailList> {
        return repository.getCrypto(id)
    }
}