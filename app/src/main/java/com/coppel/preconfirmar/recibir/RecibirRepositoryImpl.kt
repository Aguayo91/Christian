package com.coppel.preconfirmar.recibir

import com.coppel.preconfirmar.entities.*

class RecibirRepositoryImpl(
    private val dataSourceRemote: RemoteRecibirDataSource,
    private val dataSourceLocal: LocalRecibirDataSource
) : RecibirRepository {


    override suspend fun getIniciarSurtido(): IniciarSurtido {
        dataSourceRemote.getIniciarSurtido().data.forEach { inicio->
            dataSourceLocal.saveInicioSUrtido(inicio.toIniciarSurtidoListEntity())
        }
        return dataSourceRemote.getIniciarSurtido()
    }

    override suspend fun getSurtidoMuebles(): SurtidoMuebles {
        dataSourceRemote.getSurtidoMuebles().data.forEach { surtido ->
            dataSourceLocal.saveMuebles(surtido.toSurtidoMueblesListEntity())
        }
        return dataSourceRemote.getSurtidoMuebles()
    }

    override suspend fun getSurtidoCelulares(): SurtidoCelulares {
        dataSourceRemote.getSurtidoCelulares().data.forEach { celulares ->
            dataSourceLocal.saveCelulares(celulares.toSurtidoCelularesListEntity())
        }
        return dataSourceRemote.getSurtidoCelulares()
    }

    override suspend fun getSurtidoMensajeria(): SurtidoMensajeria {
        dataSourceRemote.getSurtidoMensajeria().data.forEach { mensajeria ->
            dataSourceLocal.saveMensajeria(mensajeria.toSurtidoMensajeriaListEntity())
        }
        return dataSourceRemote.getSurtidoMensajeria()
    }



    override suspend fun getSurtidoRopa(): SurtidoRopa {
        dataSourceRemote.getSurtidoRopa().data.forEach { ropa ->
            dataSourceLocal.saveRopa(ropa.toSurtidoRopaListEntity())
        }
        return dataSourceRemote.getSurtidoRopa()
    }

    override suspend fun getSurtidoSuministros(): SurtidoSuministros {
        dataSourceRemote.getSurtidoSuministros().data.forEach { suministros ->
            dataSourceLocal.saveSuministros(suministros.toSurtidoSuministrosListEntity())
        }
        return dataSourceRemote.getSurtidoSuministros()
    }


    override suspend fun getSurtidoVentasXR(): SurtidoVentasXR {
        dataSourceRemote.getSurtidoVentasXR().data.forEach { ventasxr ->
            dataSourceLocal.saveVentasR(ventasxr.toSurtidoVentasXRListEntity())
        }
        return dataSourceRemote.getSurtidoVentasXR()
    }

    override suspend fun getSurtidoVentasMotos(): SurtidoMotos {
        dataSourceRemote.getSurtidoMotos().data.forEach { motos ->
            dataSourceLocal.saveMotos(motos.toSurtidoMotosListEntity())
        }
        return dataSourceRemote.getSurtidoMotos()
    }

    override suspend fun getConsultarKeyX(): ConsultarKeyX {
        dataSourceRemote.getConsultarKeyX().toConsultarKeyXEntity()
        dataSourceLocal.saveConsultarKeyX(dataSourceRemote.getConsultarKeyX().toConsultarKeyXEntity())
        return  dataSourceRemote.getConsultarKeyX()
    }


    override suspend fun getConsultarFaltantes(): ConsultarFaltantes =
        dataSourceRemote.getConsultarFaltantes()

    override suspend fun getConsultarMasterCompensadas(): ConsultarMasterCompensadas =
        dataSourceRemote.getConsultarMasterCompensadas()

    override suspend fun getCompararTotalesSurtido(): CompararTotalesSurtido =
        dataSourceRemote.getCompararTotalesSurtido()

    override suspend fun getVerificarPreconfirmacion(): VerificarPreconfirmacion =
        dataSourceRemote.getVerificarPreconfirmacion()

    override suspend fun getActualizartipoSurtido(): ActualizarTipoSurtido =
        dataSourceRemote.getActualizartipoSurtido()

    override suspend fun getInformacionExistencia(): ObtenerInformacionExistencia =
        dataSourceRemote.getInformacionExistencia()

    override suspend fun getConsultarTiempoAdicionalSinMaster(): ConsultarTiempoAdicionalSinMaster =
        dataSourceRemote.getConsultarTiempoAdicionalSinMaster()

    override suspend fun getConsultarTiempoConfirmar(): ConsultarTiempoConfirmar =
        dataSourceRemote.getConsultarTiempoConfirmar()

    override suspend fun getConsultarTiendaForanea(): ConsultarTiendaForanea =
        dataSourceRemote.getConsultarTiendaForanea()

    override suspend fun getConsultarTiempoConfirmacion(): ConsultarTiempoConfirmacion =
        dataSourceRemote.getConsultarTiempoConfirmacion()

    override suspend fun getConsultarLoteoCelulares(): ConsultarLoteoCelulares =
        dataSourceRemote.getConsultarLoteoCelulares()

    override suspend fun getConsultarBodegasRopa(): ConsultarBodegasRopa =
        dataSourceRemote.getConsultarBodegasRopa()

    override suspend fun getMarcarLoteoMuebles(): MarcarLoteoMuebles =
        dataSourceRemote.getMarcarLoteoMuebles()

    override suspend fun getMarcarLoteoCelulares(): MarcarLoteoCelulares =
        dataSourceRemote.getMarcarLoteoCelulares()

    override suspend fun actualizaSurtidoMuebles(listaSurtido: SurtidoMueblesList) {
        dataSourceRemote
    }


}