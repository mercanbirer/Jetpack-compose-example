package com.example.cryptocomposeexample.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocomposeexample.model.CryptoListItem
import com.example.cryptocomposeexample.repo.CryptoRepository
import com.example.cryptocomposeexample.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoListVM @Inject constructor(
    private val repository: CryptoRepository
) : ViewModel() {

    var cryptoList = mutableStateOf<List<CryptoListItem>>(listOf())
    var errorMessage = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    fun loadCrypto() {
        viewModelScope.launch {
            isLoading.value = true
            val result = repository.getCryptoList()
            when (result) {
                is Resource.Success -> {
                    val cryptoItems = result.data!!.mapIndexed { index, it ->
                        CryptoListItem(it.currency, it.price)
                    }
                    errorMessage.value = ""
                    isLoading.value = false
                    cryptoList.value += cryptoItems
                }
                is Resource.Error -> {
                    errorMessage.value = result.message!!
                }
            }
        }
    }
}