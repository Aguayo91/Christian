package com.coppel.preconfirmar.bd.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.coppel.preconfirmar.entities.Todo



@Dao
interface TodoDao {
    @Query("SELECT * FROM todo")
    suspend fun getAllTodo():List<Todo>

    @Query("DELETE FROM todo")
    suspend fun deleteAll()

    @Query("DELETE FROM todo  WHERE _id = :id")
    suspend fun deleteitem(id: Int?)

    @Query("SELECT COUNT(*) FROM todo WHERE sobrante = 1")
    suspend fun getSobrante():Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveTodo(todo:Todo)

    @Query("SELECT description FROM Todo WHERE codigo = :smaster" )
    suspend fun getMasterleida(smaster: String?):String?

    @Query("SELECT * FROM Todo WHERE codigo = :smaster" )
    suspend fun getMastercapturada(smaster: String?):List<Todo>

    @Query("SELECT * FROM Todo WHERE codigo = :smaster AND MasterStatus = 1" )
    suspend fun getMasterConfirmada(smaster: String?):List<Todo>

    @Query("SELECT * FROM Todo WHERE Master = '' ")
    fun getAllRowsTodo() : LiveData<List<Todo>>

    //Traer Master Pendientes por Capturar Lotear
    @Query("SELECT * FROM todo WHERE isMaster = 1 AND MasterStatus = 0")
    suspend fun getPendinetesLotear():List<Todo>

    //Loteo la Master
    @Query("SELECT * FROM todo WHERE isMaster = 0 AND Master = :smaster")
    suspend fun getLoteado(smaster:String):List<Todo>

    @Query("UPDATE todo SET MasterStatus = :estatus WHERE isMaster = 1 AND codigo = :sMaster")
    suspend fun updateMaster(estatus : Int, sMaster : String)

    @Update
    suspend fun updateTodo(todo: Todo)


}