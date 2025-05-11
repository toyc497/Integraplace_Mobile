package com.example.integraplaceapp.ui.screens.list

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.integraplaceapp.R
import com.example.integraplaceapp.data.model.OrderDetailForm
import com.example.integraplaceapp.data.model.OrderEntity
import com.example.integraplaceapp.data.model.OritEntity
import com.example.integraplaceapp.data.network.ApiClient
import com.example.integraplaceapp.data.network.ApiService
import com.example.integraplaceapp.data.state.OrderViewModel
import com.example.integraplaceapp.ui.navigation.Screens
import kotlinx.coroutines.launch
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun OrderDetail(navController: NavController, orderViewModel: OrderViewModel){
    val orderAux by orderViewModel.orderState.collectAsState()
    val apiService = ApiClient.retrofit.create(ApiService::class.java)
    val coroutineScope = rememberCoroutineScope()
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var orderForm by remember { mutableStateOf<OrderDetailForm?>(null) }

    fun getOrderByCode(){

        coroutineScope.launch {
            try {
                isLoading = true
                val response = orderAux?.let { apiService.getOrderByCode(it.code) }
                if (response != null) {
                    if (response.code() == 200){
                        orderForm = response.body()!!
                    }
                }
            }catch (e: Exception){
                errorMessage = "Erro ao buscar pedido de venda"
                Log.e("Order", "$e")
            } finally {
                isLoading = false
            }
        }

    }

    LaunchedEffect(Unit) {
        getOrderByCode()
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhes do Pedido de Venda") }
            )
        },

        floatingActionButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.Center
            ) {

                FloatingActionButton(
                    onClick = {
                        navController.navigate(Screens.OrdersListRoute.route)
                    },
                    containerColor = colorResource(id = R.color.primaryColor),
                    modifier = Modifier.padding(start = 30.dp)
                ) {
                    Text(
                        text = "Voltar"
                    )
                }

                FloatingActionButton(
                    onClick = {
                        navController.navigate(Screens.HomeRoute.route)
                    },
                    containerColor = colorResource(id = R.color.primaryColor),
                    modifier = Modifier.padding(start = 80.dp)
                ) {
                    Text(
                        text = "Módulos"
                    )
                }

            }
        },
        content = { paddingValues ->

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(paddingValues)
                    .padding(12.dp)
                    .padding(bottom = 60.dp)
            ) {
                orderForm?.let {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .wrapContentHeight()
                            .padding(8.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.secondaryColor))
                    ) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .verticalScroll(rememberScrollState())
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(40.dp)
                        ) {
                            DetailRow("Código:", it.code)
                            DetailRow("Status:", it.status)
                            DetailRow("Data de Criação:", it.data_doc.toString().take(10))
                            DetailRow("Valor Total:", it.totalprice.toString())
                            it.bpr1_client?.let { it1 -> DetailRow("Código do Cliente:", it1.code) }
                            it.bpr1_client?.let { it1 -> DetailRow("Nome:", it1.fullname) }
                            it.bpr1_client?.let { it1 -> DetailRow("CPF/CNPJ:", it1.cpf_cnpj.toString()) }
                            it.bpr1_client?.let { it1 -> DetailRow("Email:", it1.email) }
                            it.bpr1_client?.let { it1 -> DetailRow("Endereço:", it1.address) }
                            it.bpr1_client?.let { it1 -> DetailRow("Telefone:", it1.phone1.toString()) }

                            Text(
                                text = "Itens",
                                style = MaterialTheme.typography.titleMedium,
                                color = colorResource(id = R.color.fontColorSecondary)
                            )

                            it.orit_collection.forEach { orit ->
                                OritCard(orit)
                            }
                        }
                    }
                } ?: Text(
                    text = "Nenhum pedido selecionado.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.Center)
                )

            }

        }
    )
}

@Composable
fun OritCard(orit: OritEntity) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        elevation = CardDefaults.cardElevation(8.dp),
        colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.primaryColor))
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            Text(text = "Item: ${orit.itemFather.name}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Preço Unitário: ${orit.unit_price}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Desconto(%): ${orit.discount}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "Quantidade: ${orit.quantity}", style = MaterialTheme.typography.bodyLarge)
        }
    }
}
