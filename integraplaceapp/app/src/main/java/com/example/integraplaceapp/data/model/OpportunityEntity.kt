package com.example.integraplaceapp.data.model

data class OpportunityEntity (
    val id: String,
    val data_publicacao_pncp: String,
    val url: String,
    val orgao_cnpj: String,
    val orgao_nome: String,
    val description: String,
    val esfera_nome: String,
    val uf: String,
    val modalidade_licitacao_nome: String,
    val situacao_nome: String,
    var participar: String
)