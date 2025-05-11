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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.integraplaceapp.R
import com.example.integraplaceapp.data.state.ItemViewModel
import com.example.integraplaceapp.ui.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun ItemDetailScreen(navController: NavController, itemViewModel: ItemViewModel){
    val itemAux by itemViewModel.itemState.collectAsState()

    fun itemType(typeAux: String): String{
        if (typeAux == "S"){
            return "Serviço"
        }

        return "Material"
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhes do Item") }
            )
        },

        floatingActionButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.Center
            ) {

                FloatingActionButton(
                    onClick = {
                        navController.navigate(Screens.ItemListRoute.route)
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
                itemAux?.let {
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
                            DetailRow("Tipo:", itemType(it.type))
                            DetailRow("Quantidade:", it.quantity.toString())
                            DetailRow("Quantidade Mínima:", it.minimal_quantity.toString())
                            DetailRow("Comprimento:", it.item_length.toString())
                            DetailRow("Altura:", it.item_height.toString())
                            DetailRow("Largura:", it.item_width.toString())
                            DetailRow("Peso:", it.item_weight.toString())
                            DetailRow("Código do Estoque:", it.wrhs_father.code)
                        }
                    }
                } ?: Text(
                    text = "Nenhum item selecionado.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.Center)
                )

            }

        }
    )

}
