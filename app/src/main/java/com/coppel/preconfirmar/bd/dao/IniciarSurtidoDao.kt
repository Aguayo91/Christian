package com.coppel.preconfirmar.bd.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coppel.preconfirmar.entities.IniciarSurtidoListEntity


@Dao
interface IniciarSurtidoDao {

    @Query("SELECT * FROM iniciarsurtidolistentity")
    suspend fun getAllInicioSurtido(): List<IniciarSurtidoListEntity>

    @Query("DELETE FROM iniciarsurtidolistentity")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveInicioSurtido(iniciarSurtidoListEntity: IniciarSurtidoListEntity)

    @Query("SELECT iHorarecepcion FROM iniciarsurtidolistentity")
    suspend fun getHoraRecepcion():Int

    @Query("SELECT iMinutorecepcion FROM iniciarsurtidolistentity")
    suspend fun getMinutoRecepcion():Int

    @Query("SELECT iHoraconfirmacion FROM iniciarsurtidolistentity")
    suspend fun getHoraConfirmacion():Int

    @Query("SELECT iMinutoconfirmacion FROM iniciarsurtidolistentity")
    suspend fun getMinutoConfirmacion():Int


    @Query("SELECT iForanea FROM iniciarsurtidolistentity")
    suspend fun getTiendaForanea():Int
}