package com.example.integraplaceapp.ui.navigation

enum class Screen{
    LOGIN,
    ADDUSER,
    HOME,
    OPPORTUNITIES,
    CALENDAR,
    WAREHOUSE,
    WAREHOUSESDETAIL,
    ADDWAREHOUSE,
    ITEM,
    ITEMLIST,
    ITEMDETAIL,
    BPR1DETAIL,
    BPR1DETAILINFO,
    ADDBPR1,
    ORDERLIST,
    ORDERDETAIL,
    DASHBOARD,
    COMPETITOR,
    NOTIFICATION
}

sealed class Screens (val route: String){

    object LoginRoute : Screens(Screen.LOGIN.name)
    object AddUserRoute : Screens(Screen.ADDUSER.name)
    object HomeRoute : Screens(Screen.HOME.name)
    object OportunidadeRoute : Screens(Screen.OPPORTUNITIES.name)
    object CalendarRoute : Screens(Screen.CALENDAR.name)
    object WarehouseRoute : Screens(Screen.WAREHOUSE.name)
    object WarehouseDetailRoute : Screens(Screen.WAREHOUSESDETAIL.name)
    object AddWarehouseRoute: Screens(Screen.ADDWAREHOUSE.name)
    object ItemRoute: Screens(Screen.ITEM.name)
    object ItemListRoute: Screens(Screen.ITEMLIST.name)
    object ItemDetailRoute: Screens(Screen.ITEMDETAIL.name)
    object BPR1DetailRoute: Screens(Screen.BPR1DETAIL.name)
    object BPR1DetailInfoRoute: Screens(Screen.BPR1DETAILINFO.name)
    object AddBPR1: Screens(Screen.ADDBPR1.name)
    object OrdersListRoute : Screens(Screen.ORDERLIST.name)
    object OrdersDetailRoute : Screens(Screen.ORDERDETAIL.name)
    object DashboardRoute: Screens(Screen.DASHBOARD.name)
    object CompetitorRoute: Screens(Screen.COMPETITOR.name)
    object NotificationRoute: Screens(Screen.NOTIFICATION.name)

}