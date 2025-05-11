package com.example.integraplaceapp.data.state

import androidx.lifecycle.ViewModel
import com.example.integraplaceapp.data.model.ItemEntity
import com.example.integraplaceapp.data.model.OrderEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class OrderViewModel: ViewModel() {

    private val _orderState = MutableStateFlow<OrderEntity?>(null)
    val orderState = _orderState.asStateFlow()

    fun setOrder(orderAux: OrderEntity) {
        _orderState.value = orderAux
    }

}