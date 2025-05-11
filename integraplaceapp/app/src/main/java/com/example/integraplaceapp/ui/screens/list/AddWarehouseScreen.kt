package com.example.integraplaceapp.ui.screens.list

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.integraplaceapp.R
import com.example.integraplaceapp.data.model.WarehouseEntity
import com.example.integraplaceapp.data.network.ApiClient
import com.example.integraplaceapp.data.network.ApiService
import com.example.integraplaceapp.ui.navigation.Screens
import kotlinx.coroutines.launch

@Composable
fun AddWarehouseScreen(navController: NavController){
    val apiService = ApiClient.retrofit.create(ApiService::class.java)
    val coroutineScope = rememberCoroutineScope()
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }

    var name by remember { mutableStateOf("") }
    var cep by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
    var streetnum by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var block by remember { mutableStateOf("") }
    var uf by remember { mutableStateOf("") }

    fun cepApi(cepAux: String) {
        if (cepAux.length == 8){

            coroutineScope.launch {
                try {
                    isLoading = true
                    errorMessage = null
                    val response = apiService.cepInfo(cepAux)
                    if (response.code() == 200){
                        uf = response.body()?.uf.toString()
                        address = response.body()?.logradouro.toString()
                        city = response.body()?.localidade.toString()
                        block = response.body()?.bairro.toString()
                    }
                } catch (e: Exception){
                    errorMessage = "Cep sem preenchimento automático"
                }finally {
                    isLoading = false
                }

            }

        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Novo Estoque",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = name,
            onValueChange = { name = it },
            label = { Text("Nome") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = cep,
            onValueChange = {
                if (it.length <= 8 && it.all { char -> char.isDigit() }) {
                    cep = it
                }
            },
            modifier = Modifier
                .onFocusChanged { focusState ->
                    if (isFocused && !focusState.isFocused) {
                        cepApi(cep)
                    }
                    isFocused = focusState.isFocused
                }
                .fillMaxWidth(),
            label = { Text("CEP") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = uf,
            onValueChange = { uf = it },
            label = { Text("UF") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = city,
            onValueChange = { city = it },
            label = { Text("Cidade") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = address,
            onValueChange = { address = it },
            label = { Text("Endereço") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = block,
            onValueChange = { block = it },
            label = { Text("Bairro") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = streetnum,
            onValueChange = {
                if (it.length <= 5 && it.all { char -> char.isDigit() }) {
                    streetnum = it
                }
            },
            label = { Text("Número da rua") },
            modifier = Modifier.fillMaxWidth(),
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
                if (name.isEmpty() || cep.isEmpty() || address.isEmpty() || city.isEmpty() || uf.isEmpty()) {
                    errorMessage = "Campos obrigatórios não preenchidos"
                } else {
                    isLoading = true
                    errorMessage = null

                    coroutineScope.launch {

                        try {

                            var cepAux = cep.toIntOrNull()
                            var streetnumAux = streetnum.toIntOrNull()

                            if(cepAux == null){
                                cepAux = 0
                            }

                            if(streetnumAux == null){
                                streetnumAux = 0
                            }

                            var warehouseAux = WarehouseEntity(0, "", name, cepAux, address, streetnumAux, city, block, uf)
                            var response = apiService.createWarehouse(warehouseAux);

                            if (response.code() == 201){

                                errorMessage = "Cadastrado com sucesso!"

                            }else if(response.code() == 400){

                                errorMessage = "Usuário já cadastrado!"

                            }else{
                                errorMessage = "Erro na criação"
                            }

                        } catch (e: Exception){

                            Log.e("Cadastro:","$e")
                            errorMessage = "Erro na criação"

                        } finally {

                            isLoading = false

                        }

                    }

                }
            },
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.primaryColor)),
            enabled = !isLoading
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            } else {
                Text("Cadastrar")
            }
        }

        Button(
            onClick = {
                navController.navigate(Screens.HomeRoute.route)
            },
            modifier = Modifier.fillMaxWidth().padding(top = 5.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.secondaryColor)),
        ) {
            Text(
                text = "Voltar"
            )
        }

    }
}