package com.example.integraplaceapp.data.model

data class OritEntity(
    val id: Long,
    val code: String,
    val unit_price: Double,
    val discount: Double,
    val quantity: Long,
    val itemFather: ItemEntity
)
