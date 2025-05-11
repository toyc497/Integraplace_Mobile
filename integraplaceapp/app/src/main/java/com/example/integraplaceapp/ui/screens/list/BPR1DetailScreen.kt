package com.example.integraplaceapp.ui.screens.list

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.integraplaceapp.R
import com.example.integraplaceapp.data.model.BPR1Entity
import com.example.integraplaceapp.data.network.ApiClient
import com.example.integraplaceapp.data.network.ApiService
import com.example.integraplaceapp.data.state.BPR1ViewModel
import com.example.integraplaceapp.ui.navigation.Screens
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BPR1DetailScreen(navController: NavController, bpr1ViewModel: BPR1ViewModel){
    val apiService = ApiClient.retrofit.create(ApiService::class.java)
    val coroutineScope = rememberCoroutineScope()
    var listBPR1 by remember { mutableStateOf<List<BPR1Entity>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    fun getBPR1(){
        coroutineScope.launch {
            try {
                val response = apiService.getAllBPR1()

                if (response.code() == 200) {
                    listBPR1 = response.body()!!
                }else {
                    errorMessage = "Falha ao carregar parceiros"
                }

            }catch (e: Exception) {
                errorMessage = "Falha ao carregar parceiros"
                Log.e("parceiros:","$e")
            }finally {
                isLoading = false
            }
        }
    }

    LaunchedEffect(Unit) {
        getBPR1()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Parceiros de Negócio",
                        textAlign = TextAlign.Center
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screens.AddBPR1.route)
                },
                containerColor = colorResource(id = R.color.primaryColor)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Cadastrar Parceiro")
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (listBPR1.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Nenhum Parceiro de Negócio encontrado.")
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(listBPR1) {
                                listBPR1 -> BPR1(listBPR1) {
                                bpr1ViewModel.setBPR1(listBPR1)
                                navController.navigate(Screens.BPR1DetailInfoRoute.route)
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun BPR1(bpr1: BPR1Entity, onClick: () -> Unit) {
    fun bpr1Type(typeAux: String): String{
        if (typeAux == "S"){
            return "Fornecedor"
        }else{
            return "Cliente"
        }
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .wrapContentHeight(),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.primaryColor))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = "Nome: ${bpr1.fullname}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Tipo: ${bpr1Type(bpr1.type)}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}