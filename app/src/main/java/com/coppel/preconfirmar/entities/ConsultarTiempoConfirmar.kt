package com.coppel.preconfirmar.entities;

data class ConsultarTiempoConfirmar(
    val message: String = "",
    val type: Int = -5,
    val data: TiempoConfirmar
)
data class TiempoConfirmar(

    val num_hora_preconfirmacion: Int = -5,
    val num_minutos_preconfirmacion: Int = -5,
    val num_hora_confirmacion: Int = -5,
    val num_minutos_confirmacion: Int = -5
)