package com.coppel.preconfirmar.bd.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coppel.preconfirmar.entities.ConsultarKeyXEntity



@Dao
interface ConsultarKeyXDao {
    @Query("SELECT * FROM consultarkeyxentity")
    suspend fun getAllConsultarKeyX(): ConsultarKeyXEntity

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveConsultarKeyX(consultarKeyXListEntity: ConsultarKeyXEntity)

    @Query("DELETE FROM consultarkeyxentity")
    suspend fun deleteAll()
}