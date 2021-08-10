package com.coppel.preconfirmar.entities;

data class ConsultarCodigoTarjetas(
    val message: String = "",
    val type: Int = -5,
    val data: CodigoTarjetas
)
data class CodigoTarjetas(

    val iStatus: Int = 5,
    val sMensaje: Any? = null,
    val sRespuesta: Any? = null,
    val sTipoError: Any? = null
)
