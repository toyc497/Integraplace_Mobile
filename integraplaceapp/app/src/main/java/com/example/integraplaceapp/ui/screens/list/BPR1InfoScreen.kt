package com.example.integraplaceapp.ui.screens.list

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
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.integraplaceapp.R
import com.example.integraplaceapp.data.state.BPR1ViewModel
import com.example.integraplaceapp.ui.navigation.Screens

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BPR1InfoScreen(navController: NavController, bpr1ViewModel: BPR1ViewModel){
    val bpr1Aux by bpr1ViewModel.bpr1State.collectAsState()

    fun bpr1Type(typeAux: String): String{
        if (typeAux == "S"){
            return "Fornecedor"
        }else{
            return "Cliente"
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detalhes do Parceiro") }
            )
        },

        floatingActionButton = {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Absolute.Center
            ) {

                FloatingActionButton(
                    onClick = {
                        navController.navigate(Screens.BPR1DetailRoute.route)
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
                    .verticalScroll(rememberScrollState()),
            ) {
                bpr1Aux?.let {
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
                            DetailRow("Nome:", it.fullname)
                            DetailRow("CPF/CNPJ:", it.cpf_cnpj.toString())
                            DetailRow("Idade:", it.age.toString())
                            DetailRow("Tipo:", bpr1Type(it.type))
                            DetailRow("Email:", it.email)
                            DetailRow("Telefone 1:", it.phone1.toString())
                            DetailRow("Telefone 2:", it.phone2.toString())
                            DetailRow("CEP:", it.cep.toString())
                            DetailRow("Endereço:", it.address)
                            DetailRow("Cidade:", it.city)
                            DetailRow("Bairro:", it.block)
                            DetailRow("UF:", it.uf)
                        }
                    }
                } ?: Text(
                    text = "Nenhum parceiro selecionado.",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.align(Alignment.Center)
                )

            }

        }
    )
}