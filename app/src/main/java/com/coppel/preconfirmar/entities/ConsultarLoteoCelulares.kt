package com.coppel.preconfirmar.entities;

data class ConsultarLoteoCelulares(
    val message: String = "",
    val type: Int = -5,
    val `data`: List<LoteCelularesList> = listOf()
)
data class LoteCelularesList(
    val iRespuesta: Int
)