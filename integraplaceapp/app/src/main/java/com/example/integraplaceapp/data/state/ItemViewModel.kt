package com.example.integraplaceapp.data.state

import androidx.lifecycle.ViewModel
import com.example.integraplaceapp.data.model.ItemEntity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class ItemViewModel: ViewModel() {

    private val _itemState = MutableStateFlow<ItemEntity?>(null)
    val itemState = _itemState.asStateFlow()

    fun setItem(itemAux: ItemEntity) {
        _itemState.value = itemAux
    }

}