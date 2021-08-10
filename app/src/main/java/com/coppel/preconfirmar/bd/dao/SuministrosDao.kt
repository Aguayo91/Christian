package com.coppel.preconfirmar.bd.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coppel.preconfirmar.entities.SurtidoSuministrosListEntity



@Dao
interface SuministrosDao {
    @Query("SELECT * FROM surtidosuministroslistentity")
    suspend fun getAllSuministros():List<SurtidoSuministrosListEntity>

    @Query("DELETE FROM surtidosuministroslistentity")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveSuministros(surtidosuministroslistentity: SurtidoSuministrosListEntity)

    @Query("DELETE FROM surtidosuministroslistentity  WHERE iKeyx = 0")
    suspend fun deleteNull()

    @Query("SELECT count ('iKey') FROM surtidosuministroslistentity")
    suspend fun sizeSuministros():Int


    @Query("SELECT * FROM surtidosuministroslistentity WHERE sMaster = :smaster AND iLectura = :lectura" )
    suspend fun getDescripcionMasterSuministros(smaster: String?, lectura : Int):List<SurtidoSuministrosListEntity>


    @Query("UPDATE surtidosuministroslistentity SET ilectura = 1 where iKeyx = (select distinct iKeyx from surtidosuministroslistentity where sMaster = :smaster AND iLectura <>1)")
    suspend fun updatListMastersSuministros(smaster: String?)


    @Query("SELECT * FROM surtidosuministroslistentity WHERE iCodigo = :icodigo AND iLectura = 0")
    suspend fun getDescripcionCodigo(icodigo:String?):List<SurtidoSuministrosListEntity>

    @Query("UPDATE surtidosuministroslistentity SET ilectura = 1 where iKeyx = (select distinct iKeyx from surtidosuministroslistentity where sMaster = :icodigo AND iLectura <>1)")
    suspend fun updateListCodigo(icodigo: Int?)

    @Query("UPDATE surtidosuministroslistentity SET ilectura = 1 where sMaster = :smaster AND iKeyx = :ikeyx")
    suspend fun updatListMasterSuministros(smaster: String?, ikeyx : Int)


}