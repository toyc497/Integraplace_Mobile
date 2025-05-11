package com.example.integraplaceapp.ui.screens.list

import android.text.util.Linkify
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.integraplaceapp.R
import com.example.integraplaceapp.data.model.UsersEntity
import com.example.integraplaceapp.data.network.ApiClient
import com.example.integraplaceapp.data.network.ApiService
import com.example.integraplaceapp.ui.navigation.Screens
import kotlinx.coroutines.launch

@Composable
fun LoginScreen(navController: NavController) {
    val apiService = ApiClient.retrofit.create(ApiService::class.java)
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoading by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Integraplace Login",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(bottom = 32.dp),
            color = colorResource(id = R.color.fontColorPrimary),
            fontStyle = FontStyle.Normal
        )

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("Email") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email)
        )

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Senha") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth().padding(bottom = 16.dp),
            visualTransformation = PasswordVisualTransformation(),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
        )

        errorMessage?.let {
            Text(
                text = it,
                modifier = Modifier.padding(bottom = 16.dp),
                color = colorResource(id = R.color.wrongText)
            )
        }

        Button(
            onClick = {
                if (email.isEmpty() || password.isEmpty()) {
                    errorMessage = "Por favor, preencha todos os campos."
                    return@Button
                }

                isLoading = true
                errorMessage = null

                val users = UsersEntity(username = "", email, password)

                coroutineScope.launch {
                    try {
                        val response = apiService.login(users)

                        if (response.body()?.status.equals("Authorized")) {
                            navController.navigate(Screens.HomeRoute.route)
                        } else {
                            errorMessage = "Email ou Senha incorretos"
                        }
                    } catch (e: Exception) {
                        Log.e("Resposta:","$e")
                        errorMessage = "Email ou Senha incorretos"
                    } finally {
                        isLoading = false
                    }
                }

            },
            modifier = Modifier.fillMaxWidth(),
            enabled = !isLoading,
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.primaryColor))
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    color = Color.White,
                    modifier = Modifier.size(20.dp)
                )
            } else {
                Text("Entrar")
            }
        }

        Text(
            text = "Cadastre-se",
            modifier = Modifier.padding(top = 10.dp).clickable{
                navController.navigate(Screens.AddUserRoute.route)
            },
            fontStyle = FontStyle.Normal,
            fontSize = 18.sp
        )

    }
}