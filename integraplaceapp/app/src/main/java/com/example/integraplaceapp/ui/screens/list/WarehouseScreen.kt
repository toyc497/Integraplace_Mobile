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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
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
import com.example.integraplaceapp.data.model.WarehouseEntity
import com.example.integraplaceapp.data.network.ApiClient
import com.example.integraplaceapp.data.network.ApiService
import com.example.integraplaceapp.data.state.WarehouseViewModel
import com.example.integraplaceapp.ui.navigation.Screens
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WarehouseScreen(navController: NavController, viewModel: WarehouseViewModel) {
    val apiService = ApiClient.retrofit.create(ApiService::class.java)
    val coroutineScope = rememberCoroutineScope()
    var listWarehouses by remember { mutableStateOf<List<WarehouseEntity>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    fun getWarehouses(){
        coroutineScope.launch {
            try {
                val response = apiService.getWarehouses()

                if (response.code() == 200) {
                    listWarehouses = response.body()!!
                }else {
                    errorMessage = "Falha ao carregar estoques"
                }

            }catch (e: Exception) {
                errorMessage = "Falha ao carregar estoques"
                Log.e("Warehouse:","$e")
            }finally {
                isLoading = false
            }
        }
    }

    LaunchedEffect(Unit) {
        getWarehouses()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Estoques",
                        textAlign = TextAlign.Center
                    )
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    navController.navigate(Screens.AddWarehouseRoute.route)
                },
                containerColor = colorResource(id = R.color.primaryColor)
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Cadastrar Estoque")
            }
        },
        content = { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                if (listWarehouses.isEmpty()) {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Nenhum estoque encontrado.")
                    }
                } else {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(listWarehouses) {
                            listWarehouses -> EstoqueItem(listWarehouses) {
                                viewModel.setWarehouse(listWarehouses)
                                navController.navigate(Screens.WarehouseDetailRoute.route)
                            }
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun EstoqueItem(warehouse: WarehouseEntity, onClick: () -> Unit) {
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
            Text(text = "Código: ${warehouse.code}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Nome: ${warehouse.name}", style = MaterialTheme.typography.bodyLarge)
        }
    }
}