package com.coppel.preconfirmar.bd.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coppel.preconfirmar.entities.FaltantesListEntity

@Dao
interface FaltantesDao {
    @Query("DELETE FROM faltanteslistentity")
    suspend fun deleteAll()

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveFaltantes(faltanteslistentity: FaltantesListEntity)

    @Query("SELECT iFaltante FROM faltanteslistentity")
    suspend fun getiFaltante():Int

    @Query("SELECT sMaster FROM faltanteslistentity")
    suspend fun getsMaster():String
}