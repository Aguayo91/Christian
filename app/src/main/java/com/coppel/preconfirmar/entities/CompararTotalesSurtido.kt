package com.coppel.preconfirmar.entities;

data class CompararTotalesSurtido(

    val message: String = "",
    val type: Int = -5,
    val data: List<TolatesSurtidoList> = listOf()
)
data class TolatesSurtidoList(

    val ltotalMuebles: Int = -5,
    val ltotalCelulares: Int = -5,
    val ltotalRopa: Int = -5,
    val ltotalSuministros: Int = -5,
    val ltotalMensajeria: Int = -5,
    val ltotalVentaR: Int = -5,
    val ltotalMotos: Int = -5,
)