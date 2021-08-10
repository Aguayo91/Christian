package com.coppel.preconfirmar.entities;

data class ConsultarFaltantes(
    val message: String,
    val type: Int,
    val data: List<Faltantes> = listOf()
)
data class Faltantes(

    val sMaster: String = "",
    val iFaltante: Int = -5,
    val iCompensada: Int = -5
)