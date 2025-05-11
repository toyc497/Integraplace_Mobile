@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.integraplaceapp.ui.screens.list
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.integraplaceapp.R
import com.example.integraplaceapp.data.state.WarehouseViewModel
import com.example.integraplaceapp.ui.navigation.Screens

@Composable
fun WarehouseDetailScreen(navController: NavController, warehouseViewModel: WarehouseViewModel) {
    val warehouseAux by warehouseViewModel.warehouseState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhes do Estoque") }
            )
        },

        floatingActionButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.Center
            ) {

                FloatingActionButton(
                    onClick = {
                        navController.navigate(Screens.WarehouseRoute.route)
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
                    .fillMaxSize()
                    .padding(paddingValues)
                    .padding(12.dp)
            ) {
                warehouseAux?.let {
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
                                .padding(16.dp),
                            verticalArrangement = Arrangement.spacedBy(40.dp)
                        ) {
                            DetailRow("Código:", it.code)
                            DetailRow("Nome:", it.name)
                            DetailRow("CEP:", it.cep.toString())
                            DetailRow("Endereço:", it.address)
                            DetailRow("Número:", it.streetnum.toString())
                            DetailRow("Cidade:", it.city)
                            DetailRow("Bairro:", it.block)
                            DetailRow("UF:", it.uf)
                        }
                    }
                } ?: Text(
                    text = "Nenhum estoque selecionado.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.Center)
                )

            }

        }
    )
}

@Composable
fun DetailRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Bold),
            color = colorResource(id = R.color.borderColor),
            fontSize = 18.sp
        )
        Text(
            text = value,
            style = MaterialTheme.typography.bodyMedium,
            color = colorResource(id = R.color.primaryColor),
            fontSize = 18.sp
        )
    }
}
