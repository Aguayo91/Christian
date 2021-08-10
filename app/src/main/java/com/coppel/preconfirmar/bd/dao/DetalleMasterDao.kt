package com.coppel.preconfirmar.bd.dao

import androidx.room.*
import com.coppel.preconfirmar.entities.DetalleMasterListEntity


@Dao
interface DetalleMasterDao {
    @Query("SELECT * FROM detallemasterlistentity")
    suspend fun getAllMuebles(): List<DetalleMasterListEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveDetalle(detallemasterlistentity: DetalleMasterListEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveAllDetalle(detallemasterlistentity: List<DetalleMasterListEntity>)

    @Query("DELETE FROM detallemasterlistentity")
    suspend fun deleteNull()

    @Query("DELETE FROM detallemasterlistentity")
    suspend fun deleteAll()




    //buscar codigo

    @Query("SELECT *  FROM detallemasterlistentity WHERE iCodigo = :icodigo AND sMaster = :smaster")
    suspend fun buscarcodigo(icodigo:Int, smaster:String):List<DetalleMasterListEntity>

    @Query("SELECT *  FROM detallemasterlistentity WHERE sImei = :icodigo AND sMaster = :smaster")
    suspend fun buscarcodigoByImei(icodigo:String, smaster:String):List<DetalleMasterListEntity>

    @Update
    suspend fun updateDetalleMaster(producto : DetalleMasterListEntity)


    @Query("SELECT COUNT(*) FROM DetalleMasterListEntity WHERE sMaster = :sMaster AND iCodigo <> 0")
    suspend fun obtieneTotaleByMaster(sMaster :String ) : Int

}