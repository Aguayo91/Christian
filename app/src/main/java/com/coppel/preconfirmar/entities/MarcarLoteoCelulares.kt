package com.coppel.preconfirmar.entities;

data class MarcarLoteoCelulares(
    val message: String = "",
    val type: Int = -5,
    val data: List<MarcarLoteoCelularesList> = listOf()
)
data class MarcarLoteoCelularesList(
    val iRespuesta: Int = -5
)
