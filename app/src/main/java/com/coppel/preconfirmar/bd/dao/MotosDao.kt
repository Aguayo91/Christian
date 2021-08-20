package com.coppel.preconfirmar.bd.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coppel.preconfirmar.entities.SurtidoMotosListEntity


@Dao
interface MotosDao {
    @Query("SELECT * FROM surtidomotoslistentity")
    suspend fun getAllMotos():List<SurtidoMotosListEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMotos(surtidomotoslistentity: SurtidoMotosListEntity)

    @Query("DELETE FROM surtidomotoslistentity")
    suspend fun deleteAll()

    @Query("DELETE FROM surtidomotoslistentity  WHERE iKeyx = 0")
    suspend fun deleteNull()

    @Query("SELECT count ('iKey') FROM surtidomotoslistentity")
    suspend fun sizeMotos():Int

    @Query("SELECT * FROM surtidomotoslistentity WHERE iCodigo = :icodigo AND iLectura = 0 AND iRubro = 9")
    suspend fun getDescripcionMotosCodigo(icodigo:String?):List<SurtidoMotosListEntity>

    @Query("UPDATE surtidomotoslistentity SET ilectura = 1 where iCodigo = :smaster AND iKeyx = :ikeyx")
    suspend fun updateListMotosCodigo(smaster: String?, ikeyx : Int)

    //Ventas x R --- revisa si es cero que no hayan sido leídas
    @Query("SELECT iExistencia FROM surtidomotoslistentity WHERE iCodigo = :icodigo AND iLectura = 0")
    suspend fun getExistenciasMotos(icodigo:Int?):Int?


    @Query("UPDATE surtidomuebleslistentity SET ilectura = 1 where sMaster = :smaster AND iKeyx = :ikeyx")
    suspend fun updatListMaster(smaster: String?, ikeyx : Int)

}