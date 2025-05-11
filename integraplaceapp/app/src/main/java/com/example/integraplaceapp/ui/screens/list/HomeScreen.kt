package com.example.integraplaceapp.ui.screens.list

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Badge
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.integraplaceapp.R
import com.example.integraplaceapp.data.model.NotificationEntity
import com.example.integraplaceapp.data.network.ApiClient
import com.example.integraplaceapp.data.network.ApiService
import com.example.integraplaceapp.data.state.NotificationViewModel
import com.example.integraplaceapp.ui.navigation.Screens
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavController, notificationViewModel: NotificationViewModel) {
    val apiService = ApiClient.retrofit.create(ApiService::class.java)
    val coroutineScope = rememberCoroutineScope()
    var listNotifications by remember { mutableStateOf<List<NotificationEntity>>(emptyList()) }

    fun getNotifications(){
        coroutineScope.launch {
            try {
                val response = apiService.getAllNotifications()
                if (response.code() == 200){
                    listNotifications = response.body()!!
                    notificationViewModel.setNotification(listNotifications)
                }
            }catch (e: Exception){
              Log.e("Notificação Error: ", "$e")
            } finally {

            }
        }
    }

    LaunchedEffect(Unit) {
        getNotifications()
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = "Módulos",

                    )
                },
                actions = {
                    Box {
                        IconButton(onClick = {
                            navController.navigate(Screens.NotificationRoute.route)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Notifications,
                                contentDescription = "Notificações",
                                modifier = Modifier.size(28.dp),
                                tint = colorResource(id = R.color.secondaryColor)
                            )
                        }

                        Badge(
                            modifier = Modifier.align(Alignment.TopEnd),
                            content = {
                                Text(text = "${listNotifications.size}")
                            },
                            containerColor = colorResource(id = R.color.primaryColor)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            MenuButton(
                text = "Pedido de Venda",
                onClick = {
                    navController.navigate(Screens.OrdersListRoute.route)
                }
            )

            MenuButton(
                text = "Parceiro de Negócio",
                onClick = {
                    navController.navigate(Screens.BPR1DetailRoute.route)
                }
            )

            MenuButton(
                text = "Item",
                onClick = {
                    navController.navigate(Screens.ItemListRoute.route)
                }
            )

            MenuButton(
                text = "Estoque",
                onClick = {
                    navController.navigate(Screens.WarehouseRoute.route)
                }
            )

            MenuButton(
                text = "Licitações",
                onClick = {
                    navController.navigate(Screens.OportunidadeRoute.route)
                }
            )

            MenuButton(
                text = "Analisar Concorrente",
                onClick = {
                    navController.navigate(Screens.CompetitorRoute.route)
                }
            )

            MenuButton(
                text = "Dashboard",
                onClick = {
                    navController.navigate(Screens.DashboardRoute.route)
                }
            )
        }
    }
}

@Composable
fun MenuButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp),
        colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.secondaryColor))
    ) {
        Text(text = text, color = Color.White, fontSize = 18.sp)
    }
}
