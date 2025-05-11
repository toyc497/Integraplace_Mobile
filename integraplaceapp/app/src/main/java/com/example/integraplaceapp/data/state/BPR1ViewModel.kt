package com.example.integraplaceapp.data.state

import androidx.lifecycle.ViewModel
import com.example.integraplaceapp.data.model.BPR1Entity
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class BPR1ViewModel: ViewModel() {

    private val _bpr1State = MutableStateFlow<BPR1Entity?>(null)
    val bpr1State = _bpr1State.asStateFlow()

    fun setBPR1(bpr1Aux: BPR1Entity) {
        _bpr1State.value = bpr1Aux
    }

}