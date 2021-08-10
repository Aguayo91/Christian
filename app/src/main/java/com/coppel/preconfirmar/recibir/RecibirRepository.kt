package com.coppel.preconfirmar.recibir

import com.coppel.preconfirmar.entities.*

interface RecibirRepository {
    suspend fun getIniciarSurtido(): IniciarSurtido
    suspend fun getSurtidoMuebles(): SurtidoMuebles
    suspend fun getSurtidoCelulares(): SurtidoCelulares
    suspend fun getSurtidoMensajeria(): SurtidoMensajeria
    suspend fun getSurtidoRopa(): SurtidoRopa
    suspend fun getSurtidoSuministros(): SurtidoSuministros
    suspend fun getSurtidoVentasXR(): SurtidoVentasXR
    suspend fun getSurtidoVentasMotos(): SurtidoMotos
    suspend fun getConsultarKeyX():ConsultarKeyX
    suspend fun getConsultarFaltantes(): ConsultarFaltantes
    suspend fun getConsultarMasterCompensadas():ConsultarMasterCompensadas
    suspend fun getCompararTotalesSurtido(): CompararTotalesSurtido
    suspend fun getVerificarPreconfirmacion(): VerificarPreconfirmacion
    suspend fun getActualizartipoSurtido(): ActualizarTipoSurtido
    suspend fun getInformacionExistencia(): ObtenerInformacionExistencia
    suspend fun getConsultarTiempoAdicionalSinMaster(): ConsultarTiempoAdicionalSinMaster
    suspend fun getConsultarTiempoConfirmar(): ConsultarTiempoConfirmar
    suspend fun getConsultarTiendaForanea(): ConsultarTiendaForanea
    suspend fun getConsultarTiempoConfirmacion(): ConsultarTiempoConfirmacion
    suspend fun getConsultarLoteoCelulares(): ConsultarLoteoCelulares
    suspend fun getConsultarBodegasRopa(): ConsultarBodegasRopa
    suspend fun getMarcarLoteoMuebles():MarcarLoteoMuebles
    suspend fun getMarcarLoteoCelulares():MarcarLoteoCelulares
    suspend fun actualizaSurtidoMuebles(listaSurtido : SurtidoMueblesList)
}