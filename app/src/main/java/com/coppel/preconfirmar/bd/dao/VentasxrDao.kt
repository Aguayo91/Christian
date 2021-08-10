package com.coppel.preconfirmar.bd.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coppel.preconfirmar.entities.SurtidoVentasXRListEntity


@Dao
interface VentasxrDao {
    @Query("SELECT * FROM surtidoventasxrlistentity")
    suspend fun getAllVentasR():List<SurtidoVentasXRListEntity>

    @Query("DELETE FROM surtidoventasxrlistentity")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveVentasR(surtidoventasxrlistentity: SurtidoVentasXRListEntity)


    @Query("DELETE FROM surtidoventasxrlistentity  WHERE iKeyx = 0")
    suspend fun deleteNull()


    @Query("SELECT count ('iKey') FROM surtidoventasxrlistentity")
    suspend fun sizeVentasR():Int

    @Query("SELECT * FROM surtidoventasxrlistentity WHERE iCodigo = :icodigo AND iLectura = 0" )
    suspend fun getDescripcionVentasxR(icodigo:String?):List<SurtidoVentasXRListEntity>

    //Ventas x R --- revisa si es cero que no hayan sido leídas
    @Query("SELECT iExistencia FROM surtidoventasxrlistentity WHERE iCodigo = :icodigo AND iLectura = 0")
    suspend fun getExistencianVentasxR(icodigo:Int?):Int?

    //Actualiza la tabla codigo a Leído
    @Query("UPDATE surtidoventasxrlistentity SET ilectura = 1 where iCodigo = :smaster AND iKeyx = :ikeyx")
    suspend fun updatListVentasxR(smaster: String?, ikeyx: Int)

}