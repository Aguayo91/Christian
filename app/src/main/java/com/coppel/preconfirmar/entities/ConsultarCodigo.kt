package com.coppel.preconfirmar.entities;

data class ConsultarCodigo(
    val message: String = "",
    val type: Int = -5,
    val data: List<CodioList> = listOf()
)
data class CodioList(

    val iCodigo: Int = -5,
    val sDescripciond: String = "",
    val iKeyxPda: Int = -5,
    val sArticulo: String = "",
    val sMarca: String = "",
    val iPrecio: Int = -5,
    val sDescripcionDefecto: String = "",
    val iCodigoNuevo: Int = -5,
    val iSeccion: Int = -5,
    val iDcf: Int = -5,
    val modelo: String = ""

)