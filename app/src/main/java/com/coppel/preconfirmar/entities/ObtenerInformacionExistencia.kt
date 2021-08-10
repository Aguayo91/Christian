package com.coppel.preconfirmar.entities;

data class ObtenerInformacionExistencia(
    val message: String,
    val type: Int,
    val data: List<Existencia> = listOf()

)
data class Existencia(

    val iCodigo: Int,
    val iopcetiquetaexistencia: Int,
    val fecreplicacion: String
)