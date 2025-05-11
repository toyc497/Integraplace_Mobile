package com.example.integraplaceapp.data.model

import java.util.Date

data class OrderDetailForm(
    val id: Long,
    val code: String,
    val status: String,
    val data_doc: Date,
    val totalprice: Double,
    val bpr1_client: BPR1Entity?,
    val orit_collection: List<OritEntity>
)
