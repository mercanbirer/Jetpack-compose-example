package com.example.cryptocomposeexample.view

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cryptocomposeexample.viewmodel.CryptoListVM

@Composable
fun CryptoListScreen(
    navController: NavController,
    viewModel: CryptoListVM = hiltViewModel()
) {
   Surface(
        color = MaterialTheme.colors.secondary,
        modifier = Modifier.fillMaxSize()
    ) {

    }
}