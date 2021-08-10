package com.coppel.preconfirmar.recibir

import com.coppel.preconfirmar.bd.dao.*
import com.coppel.preconfirmar.entities.*

class LocalRecibirDataSource(

    private val mueblesDao: MueblesDao,
    private val celularesDao: CelularesDao,
    private val mensajeriaDao: MensajeriaDao,
    private val ropaDao: RopaDao,
    private val ventasxrDao: VentasxrDao,
    private val suministrosDao: SuministrosDao,
    private val motosDao: MotosDao,
    private val iniciosurtidoDao: IniciarSurtidoDao,
    private val consultarKeyXDao: ConsultarKeyXDao,
){


    suspend fun getSurtidoMuebles(): List<SurtidoMueblesListEntity> {
        return mueblesDao.getAllMuebles()
    }

    suspend fun saveMuebles(mueblesListEntity: SurtidoMueblesListEntity){

        mueblesDao.saveMuebles(mueblesListEntity)
    }


    suspend fun getSurtidoCelulares(): List<SurtidoCelularesListEntity> {
        return celularesDao.getAllCelulares()
    }


    suspend fun saveCelulares(celularesListEntity: SurtidoCelularesListEntity){

        celularesDao.saveCelulares(celularesListEntity)
    }

    suspend fun getSurtidoMensajeria():List<SurtidoMensajeriaListEntity>{
        return mensajeriaDao.getAllMensajeria()
    }

    suspend fun saveMensajeria(mueblesListEntity: SurtidoMensajeriaListEntity){

        mensajeriaDao.saveMensajeria(mueblesListEntity)
    }

    suspend fun getSurtidoRopa():List<SurtidoRopaListEntity>{
        return ropaDao.getAllRopa()
    }
    suspend fun saveRopa(ropaListEntity:SurtidoRopaListEntity){

        ropaDao.saveRopa(ropaListEntity)
    }

    suspend fun getVentasXR():List<SurtidoVentasXRListEntity>{
        return ventasxrDao.getAllVentasR()
    }

    suspend fun saveVentasR(surtidoVentasXRListEntity: SurtidoVentasXRListEntity){

        ventasxrDao.saveVentasR(surtidoVentasXRListEntity)
    }

    suspend fun getSuministros():List<SurtidoSuministrosListEntity>{
        return suministrosDao.getAllSuministros()
    }

    suspend fun saveSuministros(surtidoSuministrosListEntity: SurtidoSuministrosListEntity){

        suministrosDao.saveSuministros(surtidoSuministrosListEntity)
    }

    suspend fun getMotos():List<SurtidoMotosListEntity>{
        return motosDao.getAllMotos()
    }

    suspend fun saveMotos(surtidoMotosListEntity: SurtidoMotosListEntity){

        motosDao.saveMotos(surtidoMotosListEntity)
    }
    suspend fun getIniciarSurtido(): List<IniciarSurtidoListEntity> {
        return iniciosurtidoDao.getAllInicioSurtido()
    }

    suspend fun saveInicioSUrtido(iniciarSurtidoListEntity: IniciarSurtidoListEntity) {

        iniciosurtidoDao.saveInicioSurtido(iniciarSurtidoListEntity)
    }

    suspend fun saveConsultarKeyX(consultarKeyXEntity: ConsultarKeyXEntity){

        consultarKeyXDao.saveConsultarKeyX(consultarKeyXEntity)
    }
}