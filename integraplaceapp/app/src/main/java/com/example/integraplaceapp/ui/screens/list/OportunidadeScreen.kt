package com.example.integraplaceapp.ui.screens.list

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ProgressIndicatorDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.integraplaceapp.data.model.OpportunityEntity
import com.example.integraplaceapp.data.network.ApiClient
import com.example.integraplaceapp.data.network.ApiService
import kotlinx.coroutines.launch
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.integraplaceapp.R
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter

@Composable
fun OportunidadeScreen() {
    val apiService = ApiClient.retrofit.create(ApiService::class.java)
    val coroutineScope = rememberCoroutineScope()
    var listOportunities by remember { mutableStateOf<List<OpportunityEntity>>(emptyList()) }
    var oportunityAux by remember { mutableStateOf<OpportunityEntity?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMessage by remember { mutableStateOf<String?>(null) }
    var counter: Int = 0

    fun getOportunities(){
        coroutineScope.launch {
            try {
                val response = apiService.getOpportunities()

                if (response.code() == 200) {
                    listOportunities = response.body()!!
                }else {
                    errorMessage = "Falha ao carregar Licitações"
                }

            }catch (e: Exception) {
                errorMessage = "Falha ao carregar Licitações"
                Log.e("Oportunitie:","$e")
            }finally {
                isLoading = false
            }
        }
    }

    LaunchedEffect(Unit) {
        getOportunities()
    }

    if (isLoading) {

        CircularProgressIndicator(
            color = colorResource(id = R.color.primaryColor),
            modifier = Modifier.size(50.dp).fillMaxWidth().fillMaxHeight()
        )

    } else if (errorMessage != null) {

        Text(text = errorMessage ?: "Sem novas Licitações", color = Color.Red)

    } else {

        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Novas Licitações",
                modifier = Modifier.padding(bottom = 15.dp),
                fontSize = 25.sp
            )

            Row(
                modifier = Modifier.fillMaxWidth()
            ) {
                Card(
                    modifier = Modifier
                        .size(width = 380.dp, height = 780.dp )
                        .padding(5.dp),
                    shape = MaterialTheme.shapes.large,
                    colors = CardDefaults.cardColors(containerColor = colorResource(id = R.color.borderColor))
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp)
                    ) {
                        oportunityAux?.let {
                            Text(
                                text = it.orgao_nome,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Modalidade: ${oportunityAux?.modalidade_licitacao_nome}"
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "UF: ${oportunityAux?.uf}"
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "CNPJ: ${oportunityAux?.orgao_cnpj}"
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Descrição: ${oportunityAux?.description}"
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Link: ${oportunityAux?.url}"
                        )

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = "Data da Divulgação: ${oportunityAux?.data_publicacao_pncp?.let { formatDateTime(it) }}"
                        )
                    }
                }
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.Bottom,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = {
                        coroutineScope.launch {
                            try {
                                if (oportunityAux != null){
                                    listOportunities[counter].participar = "N"
                                    apiService.setOportunityParticipation(listOportunities[counter])
                                }
                                oportunityAux = listOportunities[counter]
                                counter += 1
                            }catch (e: Exception){
                                Log.e("Oportunitie:","$e")
                            }

                        }
                    },
                    modifier = Modifier.weight(1f).clip(CircleShape),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.wrongText))
                ) {
                    Icon(Icons.Filled.Close, contentDescription = "Recusar")
                }

                Spacer(modifier = Modifier.width(16.dp))

                Button(
                    onClick = {
                        coroutineScope.launch {
                            try {
                                if (oportunityAux != null){
                                    listOportunities[counter].participar = "Y"
                                    apiService.setOportunityParticipation(listOportunities[counter])
                                }
                                oportunityAux = listOportunities[counter]
                                counter += 1
                            }catch (e: Exception){
                                Log.e("Oportunitie:","$e")
                            }

                        }
                    },
                    modifier = Modifier.weight(1f).clip(CircleShape),
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.primaryColor))

                ) {
                    Icon(Icons.Filled.Check, contentDescription = "Aceitar")
                }
            }
        }
    }

}

fun formatDateTime(dateTime: String): String {
    return try {
        val parsedDate = ZonedDateTime.parse(dateTime)
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")
        parsedDate.format(formatter)
    } catch (e: Exception) {
        "Data inválida"
    }
}

@Preview
@Composable
fun OportunidadeScreenPreview() {
    OportunidadeScreen()
}