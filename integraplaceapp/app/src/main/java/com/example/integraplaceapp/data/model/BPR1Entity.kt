package com.example.integraplaceapp.data.model

data class BPR1Entity(
    val id: Long,
    val code: String,
    val fullname: String,
    val cpf_cnpj: Long,
    val age: Int,
    val type: String,
    val email: String,
    val phone1: Long,
    val phone2: Long,
    val cep: Long,
    val address: String,
    val city: String,
    val block: String,
    val uf: String
)
