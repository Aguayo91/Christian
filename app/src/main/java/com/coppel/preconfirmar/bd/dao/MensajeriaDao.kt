package com.coppel.preconfirmar.bd.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coppel.preconfirmar.entities.SurtidoMensajeriaListEntity


@Dao
interface MensajeriaDao {
    @Query("SELECT * FROM surtidomensajerialistentity")
    suspend fun getAllMensajeria():List<SurtidoMensajeriaListEntity>

    @Query("DELETE FROM surtidomensajerialistentity")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMensajeria(surtidoMensajeriaListEntity: SurtidoMensajeriaListEntity)


    @Query("DELETE FROM surtidomensajerialistentity  WHERE iKeyx = 0")
    suspend fun deleteNull()

    @Query("SELECT count ('iKey') FROM surtidomensajerialistentity")
    suspend fun sizeMensajeria():Int

    @Query("SELECT * FROM surtidomensajerialistentity WHERE sMaster = :smaster AND iLectura = :lectura" )
    suspend fun getDescripcionMasterMensajeria(smaster: String?, lectura :Int):List<SurtidoMensajeriaListEntity>


    @Query("UPDATE surtidomensajerialistentity SET ilectura = 1 where iKeyx = (select distinct iKeyx from surtidomensajerialistentity where sMaster = :smaster AND iLectura <>1)")
    suspend fun updatListMensajeria(smaster: String?)

    //Ventas x R --- revisa si es cero que no hayan sido leídas
    @Query("SELECT iExistencia FROM surtidomensajerialistentity WHERE sMaster = :icodigo AND iLectura = 0")
    suspend fun getExistencianMensajeria(icodigo:Int?):Int?


    @Query("UPDATE surtidomensajerialistentity SET ilectura = 1 where sMaster = :smaster AND iKeyx = :ikeyx")
    suspend fun updatListMasterMensajeria(smaster: String?, ikeyx : Int)





}