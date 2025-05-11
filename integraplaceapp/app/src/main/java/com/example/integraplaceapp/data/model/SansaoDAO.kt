package com.example.integraplaceapp.data.model

data class SansaoDAO(
    val skSancao: Long,
    val skFatAcordosLeniencia: Int,
    val skFatCepim: Int,
    val cadastro: String,
    val cpfCnpj: String,
    val nomeSancionado: String,
    val ufSancionado: String,
    val categoriaSancao: String,
    val orgao: String,
    val dataPublicacao: String,
    val valorMulta: String,
    val dataInicialSancao: String,
    val dataFinalSancao: String,
    val quantidade: Int,
    val linkDetalhamento: String,
)
