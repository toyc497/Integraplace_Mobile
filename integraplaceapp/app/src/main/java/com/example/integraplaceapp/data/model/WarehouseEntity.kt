package com.example.integraplaceapp.data.model

data class WarehouseEntity(
    val id: Long,
    val code: String,
    val name: String,
    val cep: Int,
    val address: String,
    val streetnum: Int,
    val city: String,
    val block: String,
    val uf: String
)
