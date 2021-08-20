package com.coppel.preconfirmar.bd.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.coppel.preconfirmar.entities.SinEtiqueta
import com.coppel.preconfirmar.entities.Todo

@Dao
interface SinEtiquetaDao {

  /*  @Query("SELECT * FROM sinetiqueta")
    suspend fun getAllTodo(): <List<SinEtiqueta>*/

    @Query("DELETE FROM sinetiqueta")
    suspend fun deleteAll()

    @Query("SELECT COUNT(*) FROM sinetiqueta")
    suspend fun getSobrante():Int

}