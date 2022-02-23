package com.example.cryptocomposeexample.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.cryptocomposeexample.model.CryptoList
import com.example.cryptocomposeexample.model.CryptoListItem
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
        Column {
            Text(
                text = "Crypto Currencies",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(20.dp),
                fontSize = 40.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.primary
            )

            Spacer(modifier = Modifier.height(10.dp))
            SearchBar(
                hint = "Search...",
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                viewModel.searchCryptoList(it)
            }
            Spacer(modifier = Modifier.height(10.dp))
            CrptoList(navController = navController)
        }
    }
}

@Composable
fun SearchBar(
    modifier: Modifier = Modifier,
    hint: String = "",
    onSearch: (String) -> Unit = {}//Arama yapıldığında kod bloğu açıp işlem yapmak için
) {

    var isHint by remember { mutableStateOf(hint != "") }
    var text by remember { mutableStateOf("") }
    Box(modifier = modifier) {
        BasicTextField(
            value = text, onValueChange = {
                text = it
                onSearch(it)
            },
            maxLines = 1,
            singleLine = true, //tek satır
            textStyle = TextStyle(color = Color.Black),
            modifier = Modifier
                .fillMaxWidth()
                .shadow(5.dp, CircleShape)
                .background(Color.White, CircleShape)
                .padding(horizontal = 20.dp, vertical = 12.dp)
                .onFocusChanged {
                    isHint = it.isFocused != true && text.isEmpty()
                })
        if (isHint) {
            Text(
                text = hint,
                color = Color.LightGray,
                modifier = Modifier.padding(horizontal = 20.dp, vertical = 12.dp)
            )
        }
    }

}

@Composable
fun CryptoRow(
    navController: NavController,
    crypto: CryptoListItem
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 20.dp)
            .background(color = MaterialTheme.colors.secondary)
            .clickable {
                navController.navigate("detail_list")
            }
    ) {

        Text(
            text = crypto.currency,
            style = MaterialTheme.typography.h4,
            modifier = Modifier.padding(2.dp),
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colors.primary
        )

        Text(
            text = crypto.price,
            style = MaterialTheme.typography.h5,
            color = MaterialTheme.colors.primaryVariant
        )
    }
}

@Composable
fun CryptoListVM(
    cryptos: List<CryptoListItem>,
    navController: NavController
) {
    LazyColumn(contentPadding = PaddingValues(5.dp)) {
        items(cryptos) { crypto ->
            CryptoRow(navController = navController, crypto = crypto)
        }
    }
}

@Composable
fun CrptoList(
    navController: NavController,
    viewModel: CryptoListVM = hiltViewModel()
) {
    val crptoList by remember { viewModel.cryptoList }
    val errorMessage by remember { viewModel.errorMessage }
    val isLoad by remember { viewModel.isLoading }
    CryptoListVM(cryptos = crptoList, navController = navController)
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier.fillMaxSize()
    ) {
        if (isLoad) {
            CircularProgressIndicator(color = Color.Red)
        }
        if (errorMessage.isNotEmpty()) {
            viewModel.loadCrypto()
        }

    }
}
