package com.example.integraplaceapp.data.state

import androidx.lifecycle.ViewModel
import com.example.integraplaceapp.data.model.WarehouseEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class WarehouseViewModel: ViewModel() {

    private val _warehouseState = MutableStateFlow<WarehouseEntity?>(null)
    val warehouseState = _warehouseState.asStateFlow()

    fun setWarehouse(warehouseAux: WarehouseEntity) {
        _warehouseState.value = warehouseAux
    }

}