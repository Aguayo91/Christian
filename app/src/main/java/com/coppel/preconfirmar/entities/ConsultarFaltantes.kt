package com.coppel.preconfirmar.entities;

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class ConsultarFaltantes(
    val message: String,
    val type: Int,
    val data: List<FaltantesList> = listOf()
)
data class FaltantesList(
    val id:Int,
    val sMaster: String = "",
    val iFaltante: Int = -5,
    val iCompensada: Int = -5
)


@Entity
data class FaltantesListEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @SerializedName("sMaster")
    val sMaster: String = "",
    @SerializedName("iFaltante")
    val iFaltante: Int = -5,
    @SerializedName("iCompensada")
    val iCompensada: Int = -5
)

fun FaltantesList.toFaltantesListEntity():FaltantesListEntity =
    FaltantesListEntity(
        this.id,
        this.sMaster,
        this.iFaltante,
        this.iCompensada
    )