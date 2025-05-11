package com.example.integraplaceapp.data.model

data class ItemEntity(
    val id: Long,
    val code: String,
    val name: String,
    val type: String,
    val quantity: Int,
    val minimal_quantity: Long,
    val item_length: Double,
    val item_height: Double,
    val item_width: Double,
    val item_weight: Double,
    val wrhs_father: WarehouseEntity
)
