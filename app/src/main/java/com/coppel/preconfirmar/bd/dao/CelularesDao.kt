package com.coppel.preconfirmar.bd.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coppel.preconfirmar.entities.SurtidoCelularesListEntity


@Dao
interface CelularesDao {
    @Query("SELECT * FROM surtidocelulareslistentity")
    suspend fun getAllCelulares():List<SurtidoCelularesListEntity>


    @Query("DELETE FROM surtidocelulareslistentity")
    suspend fun deleteAll()



    @Query("UPDATE surtidocelulareslistentity SET ilectura = 1 where sMaster = :smaster AND iKeyx = :iKey")
    suspend fun updatListMasterCelulares(smaster: String?,iKey :Int?)














    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveCelulares(surtidoCelularesListEntity: SurtidoCelularesListEntity)

    @Query("DELETE FROM surtidocelulareslistentity  WHERE iKeyx = 0")
    suspend fun deleteNull()

    @Query("SELECT count ('iKey') FROM surtidocelulareslistentity")
    suspend fun sizeCeleulares():Int

    @Query("SELECT * FROM surtidocelulareslistentity WHERE sMaster = :smaster AND iLectura = :lectura" )
    suspend fun getDescripcionMasterCelulares(smaster: String?, lectura :Int ): List<SurtidoCelularesListEntity>


    @Query("SELECT * FROM surtidocelulareslistentity WHERE iCodigo = :icodigo AND iLectura = 0")
    suspend fun getDescripcionCelularesCodigo(icodigo:String):List<SurtidoCelularesListEntity>

    @Query("UPDATE surtidocelulareslistentity SET ilectura = 1 where iKeyx = (select distinct iKeyx from surtidocelulareslistentity where sMaster = :icodigo AND iLectura <>1)")
    suspend fun updateListCelularesCodigo(icodigo: Int?)



}