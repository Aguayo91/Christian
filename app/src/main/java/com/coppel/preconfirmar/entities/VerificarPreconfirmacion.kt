package com.coppel.preconfirmar.entities

data class VerificarPreconfirmacion(
    val message: String = "",
    val type: Int = -5,
    val data: VPreconfirmacion
)
data class VPreconfirmacion(

    val iTipoLoteo: Int = -5,
    val iPreconfirmacion: Int = -5,
    val iConfirmacion: Int = -5,
    val iTipoParcial: Int =-5,
    val iFolioParcial: Int =-5,
    val iTipoLoteoCelulares: Int =-5,
    val iTipoLoteoRopa: Int =-5,
    val idConfirmacionMuebles: Int =-5,
    val idConfirmacionCelulares: Int =-5,
    val idConfirmacionRopa: Int =-5,
    val iRespuesta: Int =-5,
    val iEtapa: Int =-5,
    val iKeyxCtl: Int =-5,
    val iPreconfirmacionPDA: Int =-5,
)