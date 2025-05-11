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
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.integraplaceapp.R
import com.example.integraplaceapp.data.model.BPR1Entity
import com.example.integraplaceapp.data.model.BPR1EntityForm
import com.example.integraplaceapp.data.model.ItemEntityForm
import com.example.integraplaceapp.data.network.ApiClient
import com.example.integraplaceapp.data.network.ApiService
import com.example.integraplaceapp.ui.navigation.Screens
import kotlinx.coroutines.launch

@Composable
fun AddBPR1Screen(navController: NavController){
    val apiService = ApiClient.retrofit.create(ApiService::class.java)
    val coroutineScope = rememberCoroutineScope()
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var isLoading by remember { mutableStateOf(false) }
    var isFocused by remember { mutableStateOf(false) }

    var fullname by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var cpf_cnpj by remember { mutableStateOf("") }
    var age by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var phone1 by remember { mutableStateOf("") }
    var phone2 by remember { mutableStateOf("") }
    var cep by remember { mutableStateOf("") }
    var address by remember { mutableStateOf("") }
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
            .padding(16.dp)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = "Novo Parceiro de Negócio",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 24.dp)
        )

        OutlinedTextField(
            value = fullname,
            onValueChange = { fullname = it },
            label = { Text("Nome Completo") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = type,
            onValueChange = { type = it },
            label = { Text("Tipo do PN") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

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

        OutlinedTextField(
            value = age,
            onValueChange = {
                if (it.length <= 3 && it.all { char -> char.isDigit() }) {
                    age = it
                }
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Idade") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = phone1,
            onValueChange = {
                if (it.length <= 13 && it.all { char -> char.isDigit() }) {
                    phone1 = it
                }
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Telefone 1") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
        )

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = phone2,
            onValueChange = {
                if (it.length <= 13 && it.all { char -> char.isDigit() }) {
                    phone2 = it
                }
            },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Telefone 2") },
            singleLine = true,
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
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
            value = address,
            onValueChange = { address = it },
            label = { Text("Endereço") },
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
            value = block,
            onValueChange = { block = it },
            label = { Text("Bairro") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
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
                if (fullname.isEmpty() || cpf_cnpj.isEmpty() || type.isEmpty() || email.isEmpty() || phone1.isEmpty() || cep.isEmpty()) {
                    errorMessage = "Campos obrigatórios não preenchidos"
                } else {
                    isLoading = true
                    errorMessage = null

                    coroutineScope.launch {

                        try {

                            var bpr1Aux = BPR1EntityForm(
                                fullname,
                                cpf_cnpj.toLong(),
                                age.toInt(),
                                type,
                                email,
                                phone1.toLong(),
                                phone2.toLong(),
                                cep.toLong(),
                                address,
                                city,
                                block,
                                uf
                            )

                            val response = apiService.createBPR1(bpr1Aux)

                            if (response.code() == 201){

                                errorMessage = "Cadastrado com sucesso!"

                            }else if(response.code() == 400){

                                errorMessage = "PN já cadastrado!"

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