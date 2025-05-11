package com.example.integraplaceapp.ui.screens.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.integraplaceapp.R
import com.example.integraplaceapp.data.model.SansaoDAO
import com.example.integraplaceapp.data.model.SansaoListDAO
import com.example.integraplaceapp.data.model.WarehouseEntity
import com.example.integraplaceapp.data.network.ApiClient
import com.example.integraplaceapp.data.network.ApiService
import kotlinx.coroutines.launch

@Composable
fun CompetitorScreen(){
    val apiService = ApiClient.retrofit.create(ApiService::class.java)
    val coroutineScope = rememberCoroutineScope()
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    var cpf_cnpj by remember { mutableStateOf("") }
    var sansaoList by remember { mutableStateOf<SansaoListDAO?>(null) }

    fun analyseCompetitor(cpfcnpjAux: String){
        if (cpfcnpjAux.length == 11 || cpfcnpjAux.length == 14){
            isLoading = true

            coroutineScope.launch {
                try {
                    errorMessage = null

                    val response = apiService.getCompetitor(cpfcnpjAux)

                    if (response.code() == 200){
                        if (response.body()?.data?.isEmpty() == false){
                            sansaoList = response.body()
                        }else{
                            errorMessage = "Não foram encontradas sanções"
                            sansaoList = null
                        }
                    }else{
                        errorMessage = "Não encontrado"
                    }

                }catch (e: Exception){
                    errorMessage = "Não encontrado"
                }finally {
                    isLoading = false
                }
            }

        }else{
            errorMessage = "CPF ou CNPJ inválido"
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Analisar Concorrente",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = cpf_cnpj,
            onValueChange = {
                if (it.length <= 14 && it.all { char -> char.isDigit() }) {
                    cpf_cnpj = it
                }
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("CPF/CNPJ") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        errorMessage?.let {
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = it,
                fontSize = 16.sp,
                color = colorResource(id = R.color.secondaryColor),
                style = MaterialTheme.typography.bodySmall)
        }

        Button(
            onClick = {
                analyseCompetitor(cpf_cnpj)
            }
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            }else{
                Text("Consultar")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (sansaoList != null){
            for (sansao: SansaoDAO in sansaoList!!.data){
                CompetitorCard(sansao)
            }
        }
    }
}

@Composable
fun CompetitorCard(sansao: SansaoDAO) {
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
            Text(text = "Cadastro: ${sansao.cadastro}", style = MaterialTheme.typography.bodyLarge)
            Text(text = "CPF/CNPJ: ${sansao.cpfCnpj}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Nome: ${sansao.nomeSancionado}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "UF: ${sansao.ufSancionado}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Órgão: ${sansao.orgao}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Quantidade: ${sansao.quantidade}", style = MaterialTheme.typography.bodyMedium)
            Text(text = "Categoria: ${sansao.categoriaSancao}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}