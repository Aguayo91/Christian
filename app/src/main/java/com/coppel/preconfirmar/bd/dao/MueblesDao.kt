package com.coppel.preconfirmar.bd.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.coppel.preconfirmar.entities.SurtidoMueblesListEntity

@Dao
interface MueblesDao {
    @Query("SELECT * FROM surtidomuebleslistentity")
    suspend fun getAllMuebles():List<SurtidoMueblesListEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveMuebles(surtidoMueblesListEntity: SurtidoMueblesListEntity)




    @Query("SELECT * FROM surtidomuebleslistentity WHERE iCodigo = :icodigo AND iLectura = 0")
    suspend fun getDescripcionCodigo(icodigo:String?):List<SurtidoMueblesListEntity>


    @Query("UPDATE surtidomuebleslistentity SET ilectura = 1 where sMaster = :smaster AND iKeyx = :ikeyx")
    suspend fun updatListMaster(smaster: String?, ikeyx : Int)






    @Query("DELETE FROM surtidocelulareslistentity  WHERE iKeyx = 0")
    suspend fun deleteNull()

    @Query("DELETE FROM surtidomuebleslistentity")
    suspend fun deleteAll()

    @Query("SELECT * FROM surtidomuebleslistentity WHERE  iLectura = 0 AND iCodigo<> -1 UNION SELECT * FROM surtidocelulareslistentity WHERE  iLectura = 0 AND iCodigo<> -1 UNION SELECT * FROM surtidomensajerialistentity WHERE  iLectura = 0 AND iCodigo<> -1 UNION SELECT * FROM surtidomotoslistentity WHERE  iLectura = 0 AND iCodigo<> -1 UNION SELECT * FROM surtidoropalistentity WHERE  iLectura = 0 AND iCodigo<> -1 UNION SELECT * FROM surtidosuministroslistentity WHERE  iLectura = 0 AND iCodigo<> -1 UNION SELECT * FROM surtidoventasxrlistentity WHERE  iLectura = 0 AND iCodigo<> -1")
    fun getAllFaltantes(): LiveData<List<SurtidoMueblesListEntity>>

    @Query("SELECT * FROM surtidomuebleslistentity WHERE  iLectura = 1 AND iCodigo<> -1")
    fun getAllLeidos(): LiveData<List<SurtidoMueblesListEntity>>


    @Query("SELECT iMoto FROM surtidomuebleslistentity WHERE iCodigo = :icodigo" )
    suspend fun getDescripcionMoto(icodigo: Int?):Int?

    @Query("SELECT * FROM surtidomuebleslistentity WHERE sMaster = :smaster AND iLectura = :lectura AND :iRubro" )
    suspend fun getDescripcionsMasterMuebles(smaster: String?, lectura : Int, iRubro:Int):List<SurtidoMueblesListEntity>



    @Query("DELETE FROM surtidomuebleslistentity  WHERE iFolioSurtido <> :folioactual")
    suspend fun deleteNull(folioactual:Int)


    @Query("SELECT sDescripcion  FROM surtidomuebleslistentity WHERE iLectura = 0 UNION SELECT sDescripcion  FROM surtidocelulareslistentity WHERE iLectura = 0")
    suspend fun sizeMueblesDieo():String

    @Query("SELECT *  FROM surtidomuebleslistentity WHERE iLectura = 0 UNION SELECT *  FROM surtidocelulareslistentity WHERE iLectura = 0")
    suspend fun sizeChris():List<SurtidoMueblesListEntity>

    @Query("SELECT iExistencia FROM surtidomuebleslistentity WHERE iCodigo = :icodigo AND iLectura = 0")
    suspend fun getExistencianCodigo(icodigo:Int?):Int?

    @Query("SELECT iFolioSurtido FROM surtidomuebleslistentity")
    suspend fun getFolioSurtido():Int?


    @Query("UPDATE surtidomuebleslistentity SET ilectura = 1 where iKeyx = (select distinct iKeyx from surtidomuebleslistentity where iCodigo = :icodigo AND iLectura <>1)")
    suspend fun updateListCodigo(icodigo: Int?)


    @Query("UPDATE surtidomuebleslistentity SET sSerie = :serie, ilectura = 1 where iKeyx = (select distinct iKeyx from surtidomuebleslistentity where iCodigo = :icodigo AND iLectura <>1)")
    suspend fun updateListMotoSerie(serie: Int?,icodigo: Int?)


    @Query("UPDATE surtidomuebleslistentity SET iActa = :iacta where iKeyx = (select distinct iKeyx from surtidomuebleslistentity where iCodigo = :icodigo AND iLectura =1)")
    suspend fun updateListActa(iacta: Int, icodigo: Int?)



    @Query("UPDATE surtidomuebleslistentity SET ilectura = 1 where iCodigo = :smaster AND iKeyx = :ikeyx")
    suspend fun updatListCodigo(smaster: String?, ikeyx : Int)

    @Query("SELECT (select count(*) from surtidoMueblesListEntity where iFolioSurtido = :surtido AND iCodigo <> -1) + (select count(*) from surtidocelulareslistentity where iFolioSurtido = :surtido AND iCodigo <> -1)  + (select count(*) from surtidosuministroslistentity where iFolioSurtido = :surtido AND iCodigo <> -1) + (select count(*) from surtidomensajerialistentity where iFolioSurtido = :surtido AND iCodigo <> -1) + (select count(*) from surtidomotoslistentity where iFolioSurtido = :surtido AND iCodigo <> -1) + (select count(*) from surtidoventasxrlistentity where iFolioSurtido = :surtido AND iCodigo <> -1) + (select count(*) from surtidoropalistentity where iFolioSurtido = :surtido AND iCodigo <> -1) as total")
    suspend fun updateCountUI(surtido:Int):Int
}