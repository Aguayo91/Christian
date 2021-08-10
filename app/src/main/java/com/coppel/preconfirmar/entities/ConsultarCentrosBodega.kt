package com.coppel.preconfirmar.entities;

data class ConsultarCentrosBodega(
    val message: String = "",
    val type: Int = -5,
    val data: CentrosBodega
)
data class CentrosBodega(
    val lCentroGteDistribucionTienda: Int = -5,
    val lCentroGteDistribucionBodega: Int = -5,
    val lCentroGteArea: Int = -5,
    val lCentroGteAuditor: Int = -5,
    val lNumeroGteDistribucionTienda: Int = -5,
    val lNumeroGteDistribucionBodega: Int = -5,
    val lNumeroGteArea: Int = -5,
    val lNumeroGteAuditor: Int = -5,
    val cNombreGteDistribucionTienda: String = "",
    val cNombreGteDistribucionBodega: String = "",
    val cNombreGteAuditor: String = ""
)