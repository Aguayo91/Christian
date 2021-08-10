package com.coppel.preconfirmar.bd.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coppel.preconfirmar.entities.SurtidoRopaListEntity



@Dao

interface RopaDao {
    @Query("SELECT * FROM surtidoropalistentity")
    suspend fun getAllRopa():List<SurtidoRopaListEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveRopa(surtidoRopaListEntity: SurtidoRopaListEntity)

    @Query("DELETE FROM surtidoropalistentity")
    suspend fun deleteAll()

    @Query("DELETE FROM surtidoropalistentity  WHERE iKeyx = 0")
    suspend fun deleteNull()

    @Query("SELECT count ('iKey') FROM surtidoropalistentity")
    suspend fun sizeRopa():Int

    //Ventas x R --- revisa si es cero que no hayan sido leídas
    @Query("SELECT iExistencia FROM surtidoropalistentity WHERE iCodigo = :icodigo AND iLectura = 0")
    suspend fun getExistencianRopa(icodigo:Int?):Int?

    //Actualiza la tabla codigo a Leído
    @Query("UPDATE surtidoropalistentity SET ilectura = 1 where iKeyx = (select distinct iKeyx from surtidoropalistentity where sMaster = :icodigo AND iLectura <>1)")
    suspend fun updatListRopas(icodigo: String?)

    @Query("SELECT * FROM surtidoropalistentity WHERE sMaster = :smaster AND iLectura = :lectura" )
    suspend fun getDescripcionMasterRopa(smaster: String?, lectura : Int):List<SurtidoRopaListEntity>


    @Query("UPDATE surtidoropalistentity SET ilectura = 1 where sMaster = :smaster AND iKeyx = :ikeyx")
    suspend fun updatListRopa(smaster: String?, ikeyx : Int)

}