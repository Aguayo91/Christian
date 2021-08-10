package com.coppel.preconfirmar.recibir

import com.coppel.preconfirmar.application.RxApplication
import com.coppel.preconfirmar.bd.dao.*
import com.coppel.preconfirmar.entities.*
import com.coppel.preconfirmar.service.WebService

class RemoteRecibirDataSource (private val webService: WebService) {


    companion object {

        private val tienda = RxApplication.pref.obtenerTiendaCoppel()
        private val folio = RxApplication.pref.obtenerFoliodelSurtido()
        private val mfolio = folio.toInt()
        private val tipoSeleccionado = RxApplication.pref.obtenerTipoSeleccionado()
        private val mempleado = RxApplication.pref.obtenerEmpleado()
        val empleado = mempleado.toInt()
        private val dispositivo = RxApplication.pref.obtenerDevice()
        private val macpda = RxApplication.pref.obtenerMacPDA()
        private val opcion = RxApplication.pref.obtenerOptionPDA()
        private val parciales = RxApplication.pref.obtenerNoParciales()



    }



    suspend fun getSurtidoMuebles(): SurtidoMuebles =
        webService.getSurtidoMuebles(tienda, mfolio)

    suspend fun getSurtidoCelulares(): SurtidoCelulares =
        webService.getSurtidoCelulares(tienda, mfolio)

    suspend fun getSurtidoMensajeria(): SurtidoMensajeria =
        webService.getSurtidoMensajeria(tienda, mfolio)

    suspend fun getSurtidoRopa(): SurtidoRopa =
        webService.getSurtidoRopa(tienda, mfolio)

    suspend fun getSurtidoSuministros(): SurtidoSuministros =
        webService.getSurtidoSuministros(tienda, mfolio)

    suspend fun getSurtidoVentasXR(): SurtidoVentasXR =
        webService.getSurtidoVentasXR(tienda, mfolio)

    suspend fun getSurtidoMotos(): SurtidoMotos =
        webService.getSurtidoMotos(tienda, mfolio)

    suspend fun getConsultarKeyX(): ConsultarKeyX =
        webService.getConsultarKeyX(tienda, mfolio)

    suspend fun getConsultarFaltantes(): ConsultarFaltantes =
        webService.getConsultarFaltantes(tienda, mfolio)

    suspend fun getConsultarMasterCompensadas(): ConsultarMasterCompensadas =
        webService.getConsultarMasterCompensadas(mfolio, tienda)

    suspend fun getCompararTotalesSurtido(): CompararTotalesSurtido =
        webService.getCompararTotalesSurtido(tienda, mfolio)

    suspend fun getVerificarPreconfirmacion(): VerificarPreconfirmacion =
        webService.getVerificarPreconfirmacion(tienda, mfolio)

    suspend fun getConsultarDetalleMaster(smaster:String, area:String,): ConsultarDetalleMaster =
        webService.getConsultarDetalleMaster(tienda, smaster,mfolio,area)

    suspend fun getActualizartipoSurtido(): ActualizarTipoSurtido =
        webService.getActualizartipoSurtido(tienda, mfolio, opcion, tipoSeleccionado)

    suspend fun getIniciarSurtido(): IniciarSurtido =
        webService.getIniciarSurtido(
            tienda,
            mfolio,
            empleado,
            tipoSeleccionado,
            macpda,
            parciales,
            parciales
        )

    suspend fun getInformacionExistencia(): ObtenerInformacionExistencia =
        webService.getInformacionExistencia(tienda)

    suspend fun getConsultarTiempoAdicionalSinMaster(): ConsultarTiempoAdicionalSinMaster =
        webService.getConsultarTiempoAdicionalSinMaster(tienda)

    suspend fun getConsultarTiempoConfirmar(): ConsultarTiempoConfirmar =
        webService.getConsultarTiempoConfirmar(tienda, mfolio, parciales)

    suspend fun getConsultarTiendaForanea(): ConsultarTiendaForanea =
        webService.getConsultarTiendaForanea(tienda)

    suspend fun getConsultarTiempoConfirmacion(): ConsultarTiempoConfirmacion =
        webService.getConsultarTiempoConfirmacion(tienda, mfolio, tipoSeleccionado)

    suspend fun getConsultarLoteoCelulares(): ConsultarLoteoCelulares =
        webService.getConsultarLoteoCelulares(tienda, mfolio, parciales, dispositivo)


    suspend fun getConsultarBodegasRopa(): ConsultarBodegasRopa =
        webService.getConsultarBodegasRopa(tienda)

    suspend fun getMarcarLoteoMuebles():MarcarLoteoMuebles =
        webService.getMarcarLoteoMuebles(tienda,mfolio, parciales)

    suspend fun getMarcarLoteoCelulares():MarcarLoteoCelulares =
        webService.getMarcarLoteoCelulares(mfolio, tienda, parciales, dispositivo)

    suspend fun actualizaSurtidoMuebles(listaSurtidoMuebles : List<SurtidoMueblesListEntity>) : VerificarActualizacionSurtido =
        webService.actualizaSurtidoMuebles(tienda.toInt(), mfolio,listaSurtidoMuebles)

    suspend fun actualizaSurtidoCelulares(listaSurtidoCelulares : List<SurtidoCelularesListEntity>) : VerificarActualizacionSurtido =
        webService.actualizaSurtidoCelulares(tienda.toInt(), mfolio,listaSurtidoCelulares)

    suspend fun actualizaSurtidoSuminsitross(listaSurtidoSuministros : List<SurtidoSuministrosListEntity>) : VerificarActualizacionSurtido =
        webService.actualizaSurtidoSuministros(tienda.toInt(), mfolio,listaSurtidoSuministros)

    suspend fun actualizaSurtidoVentasR(listaSurtidoVentasR : List<SurtidoVentasXRListEntity>) : VerificarActualizacionSurtido =
        webService.actualizaSurtidoVentasR(tienda.toInt(), mfolio,listaSurtidoVentasR)

    suspend fun actualizaSurtidoMensajeria(listaSurtidoMensajeria : List<SurtidoMensajeriaListEntity>) : VerificarActualizacionSurtido =
        webService.actualizaSurtidoMensajeria(tienda.toInt(), mfolio,listaSurtidoMensajeria)

    suspend fun actualizaSurtidoRopa(listaSurtidoRopa : List<SurtidoRopaListEntity>) : VerificarActualizacionSurtidoRopa =
        webService.actualizaSurtidoRopa(tienda.toInt(), mfolio,listaSurtidoRopa)

    suspend fun grabarTiempo() : GrabarTiempoPreconfirmacion = webService.getGrabarTiempoPreconfirmacion(
        RxApplication.pref.obtenerTiendaCoppel(),
        RxApplication.pref.obtenerFoliodelSurtido().toInt(),
        RxApplication.pref.obtenerOptionPDA(),
        "12:00:00",
        "30",
        1)
}