package com.example.integraplaceapp.data.model

import java.util.Date

data class OrderEntity (
    val id: Long,
    val code: String,
    val status: String,
    val data_doc: Date,
    val totalprice: Double,
    val bpr1Client: BPR1Entity?
)