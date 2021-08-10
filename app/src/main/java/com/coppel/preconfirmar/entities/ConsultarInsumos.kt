package com.coppel.preconfirmar.entities;

data class ConsultarInsumos(
    val message: String = "",
    val type: Int = -5,
    val data: Insumos
)

data class Insumos(

    val iStatus: Int = -5,
    val sMensaje: Any? = null,
    val sRespuesta: Any? = null,
    val sTipoError: Any? = null
)
