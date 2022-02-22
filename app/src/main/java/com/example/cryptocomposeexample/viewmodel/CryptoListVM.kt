package com.example.cryptocomposeexample.viewmodel

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.cryptocomposeexample.model.CryptoListItem
import com.example.cryptocomposeexample.repo.CryptoRepository
import com.example.cryptocomposeexample.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CryptoListVM @Inject constructor(
    private val repository: CryptoRepository
) : ViewModel() {

    var cryptoList = mutableStateOf<List<CryptoListItem>>(listOf())
    var errorMessage = mutableStateOf("")
    var isLoading = mutableStateOf(false)

    private var initalCryptoList =
        listOf<CryptoListItem>() //İndireln veri üzeirnde arama yapınca bu list değişeceği için
    private var isSearch = true                          //her defa çekmek yerine

    init {
        loadCrypto()
    }

    fun searchCryptoList(query: String) {
        val searchToList = if (isSearch) {
            cryptoList.value
        } else {
            initalCryptoList
        }
        viewModelScope.launch {
            (Dispatchers.Default)
            if (query.isEmpty()) {
                cryptoList.value = initalCryptoList
                isSearch = true
                return@launch
            }
            val results = searchToList.filter {
                it.currency.contains(
                    query.trim(),
                    ignoreCase = true
                )//boşluklardan dolayı trim, ignore büyük küçük yazıda
            }
            if (isSearch) {
                initalCryptoList = cryptoList.value
                isSearch = false
            }
            cryptoList.value = results
        }
    }

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