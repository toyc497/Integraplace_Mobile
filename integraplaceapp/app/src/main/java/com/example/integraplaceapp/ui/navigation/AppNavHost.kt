package com.example.integraplaceapp.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.integraplaceapp.data.state.BPR1ViewModel
import com.example.integraplaceapp.data.state.ItemViewModel
import com.example.integraplaceapp.data.state.NotificationViewModel
import com.example.integraplaceapp.data.state.OrderViewModel
import com.example.integraplaceapp.data.state.WarehouseViewModel
import com.example.integraplaceapp.ui.screens.list.AddBPR1Screen
import com.example.integraplaceapp.ui.screens.list.AddWarehouseScreen
import com.example.integraplaceapp.ui.screens.list.BPR1DetailScreen
import com.example.integraplaceapp.ui.screens.list.BPR1InfoScreen
import com.example.integraplaceapp.ui.screens.list.CadastroUsuarioScreen
import com.example.integraplaceapp.ui.screens.list.CalendarScreen
import com.example.integraplaceapp.ui.screens.list.CompetitorScreen
import com.example.integraplaceapp.ui.screens.list.DashboardScreen
import com.example.integraplaceapp.ui.screens.list.HomeScreen
import com.example.integraplaceapp.ui.screens.list.ItemDetailScreen
import com.example.integraplaceapp.ui.screens.list.ItemListScreen
import com.example.integraplaceapp.ui.screens.list.ItemScreen
import com.example.integraplaceapp.ui.screens.list.LoginScreen
import com.example.integraplaceapp.ui.screens.list.NotificationScreen
import com.example.integraplaceapp.ui.screens.list.OportunidadeScreen
import com.example.integraplaceapp.ui.screens.list.OrderDetail
import com.example.integraplaceapp.ui.screens.list.OrderListScreen
import com.example.integraplaceapp.ui.screens.list.WarehouseDetailScreen
import com.example.integraplaceapp.ui.screens.list.WarehouseScreen

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController,
    startDestination: String = Screens.LoginRoute.route
) {
    val warehouseViewModel: WarehouseViewModel = viewModel()
    val itemViewModel: ItemViewModel = viewModel()
    val bpr1ViewModel: BPR1ViewModel = viewModel()
    val notificationViewModel: NotificationViewModel = viewModel()
    val orderViewModel: OrderViewModel = viewModel()

    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Screens.LoginRoute.route) {
            LoginScreen(navController)
        }
        composable(Screens.AddUserRoute.route) {
            CadastroUsuarioScreen(navController)
        }
        composable(Screens.HomeRoute.route) {
            HomeScreen(navController, notificationViewModel)
        }
        composable(Screens.OportunidadeRoute.route) {
            OportunidadeScreen()
        }
        composable(Screens.CalendarRoute.route) {
            CalendarScreen()
        }
        composable(Screens.WarehouseRoute.route) {
            WarehouseScreen(navController, warehouseViewModel)
        }
        composable(Screens.WarehouseDetailRoute.route) {
            WarehouseDetailScreen(navController, warehouseViewModel)
        }
        composable(Screens.AddWarehouseRoute.route) {
            AddWarehouseScreen(navController)
        }
        composable(Screens.ItemRoute.route) {
            ItemScreen(navController)
        }
        composable(Screens.ItemListRoute.route) {
            ItemListScreen(navController, itemViewModel)
        }
        composable(Screens.ItemDetailRoute.route) {
            ItemDetailScreen(navController, itemViewModel)
        }
        composable(Screens.BPR1DetailRoute.route) {
            BPR1DetailScreen(navController, bpr1ViewModel)
        }
        composable(Screens.BPR1DetailInfoRoute.route) {
            BPR1InfoScreen(navController, bpr1ViewModel)
        }
        composable(Screens.AddBPR1.route) {
            AddBPR1Screen(navController)
        }
        composable(Screens.OrdersListRoute.route) {
            OrderListScreen(navController, orderViewModel)
        }
        composable(Screens.OrdersDetailRoute.route) {
            OrderDetail(navController, orderViewModel)
        }
        composable(Screens.DashboardRoute.route) {
            DashboardScreen()
        }
        composable(Screens.CompetitorRoute.route) {
            CompetitorScreen()
        }
        composable(Screens.NotificationRoute.route) {
            NotificationScreen(notificationViewModel)
        }
    }
}