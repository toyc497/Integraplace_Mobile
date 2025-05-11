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
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.integraplaceapp.R
import com.example.integraplaceapp.data.model.ItemEntityForm
import com.example.integraplaceapp.data.network.ApiClient
import com.example.integraplaceapp.data.network.ApiService
import com.example.integraplaceapp.ui.navigation.Screens
import kotlinx.coroutines.launch

@Composable
fun ItemScreen(navController: NavController) {
    val apiService = ApiClient.retrofit.create(ApiService::class.java)
    val coroutineScope = rememberCoroutineScope()
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }

    var name by remember { mutableStateOf("") }
    var itemType by remember { mutableStateOf("") }
    var itemLength by remember { mutableStateOf("") }
    var itemheight by remember { mutableStateOf("") }
    var itemWidth by remember { mutableStateOf("") }
    var itemWeight by remember { mutableStateOf("") }
    var quantity by remember { mutableStateOf("") }
    var minimalQuantity by remember { mutableStateOf("") }
    var wrhsFather by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Novo Item",
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
            value = itemType,
            onValueChange = { itemType = it },
            label = { Text("Tipo do item") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = itemLength,
            onValueChange = {
                if (it.length <= 5 && it.all { char -> char.isDigit() }) {
                    itemLength = it
                }
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Comprimento") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = itemheight,
            onValueChange = {
                if (it.length <= 5 && it.all { char -> char.isDigit() }) {
                    itemheight = it
                }
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Altura") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = itemWidth,
            onValueChange = {
                if (it.length <= 5 && it.all { char -> char.isDigit() }) {
                    itemWidth = it
                }
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Largura") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = itemWeight,
            onValueChange = {
                if (it.length <= 5 && it.all { char -> char.isDigit() }) {
                    itemWeight = it
                }
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Peso") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = quantity,
            onValueChange = {
                if (it.length <= 5 && it.all { char -> char.isDigit() }) {
                    quantity = it
                }
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Quantidade Atual") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = minimalQuantity,
            onValueChange = {
                if (it.length <= 5 && it.all { char -> char.isDigit() }) {
                    minimalQuantity = it
                }
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Quantidade mínima") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = wrhsFather,
            onValueChange = { wrhsFather = it },
            label = { Text("Código do Estoque") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        errorMessage?.let {
            Spacer(modifier = Modifier.height(5.dp))
            Text(
                text = it,
                fontSize = 16.sp,
                color = colorResource(id = R.color.secondaryColor),
                style = MaterialTheme.typography.bodySmall
            )
        }

        Button(
            onClick = {
                if (name.isEmpty() || name.isEmpty() || itemType.isEmpty() || quantity.isEmpty() || minimalQuantity.isEmpty() || wrhsFather.isEmpty()) {
                    errorMessage = "Campos obrigatórios não preenchidos"
                } else {
                    isLoading = true
                    errorMessage = null

                    coroutineScope.launch {

                        try {
                            var quantAux = quantity.toInt()
                            var lengthAux = itemLength.toDouble()
                            var heightAux = itemheight.toDouble()
                            var widthAux = itemWidth.toDouble()
                            var weightAux = itemWeight.toDouble()

                            var itemAux = ItemEntityForm(
                                name,
                                itemType,
                                quantAux,
                                minimalQuantity.toLong(),
                                lengthAux,
                                heightAux,
                                widthAux,
                                weightAux,
                                wrhsFather
                            )

                            val response = apiService.createItem(itemAux)

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
