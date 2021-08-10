package com.coppel.preconfirmar.entities;

data class ConsultarImeiEnOtraMaster(
    val message: String = "",
    val type: Int = -5,
    val data: ImeiOtraMaster
)
data class ImeiOtraMaster(
    val iStatus: Int =-5,
    val sMensaje: String = "",
    val sRespuesta: Any? = null,
    val sTipoError: Any? = null
)