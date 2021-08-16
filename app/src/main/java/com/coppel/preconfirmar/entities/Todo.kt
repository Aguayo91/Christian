package com.coppel.preconfirmar.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Todo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    val _id: Int = 0,
    @ColumnInfo(name = "codigo")
    val codigo: String,

    @ColumnInfo(name = "description")
    val description: String,

    @ColumnInfo(name = "sobrante")
    val sobrante: Boolean = false,

    @ColumnInfo(name = "exhibir")
    val exhibir: Boolean = false,

    @ColumnInfo(name = "irregularidad")
    var irregularidad: Boolean = false,

    @ColumnInfo(name = "isChecked")
    var isChecked: Boolean = false,

    @ColumnInfo(name = "rubro")
    var tipo: Int,

    @ColumnInfo(name = "iKeyx")
    var ikeyx: Int,

    @ColumnInfo(name = "isMaster")
    var isMaster: Int,

    @ColumnInfo(name = "Master")
    var Master: String,

    @ColumnInfo(name = "MasterStatus")
    var MasterStatus: Int,

    @ColumnInfo(name = "IMEI", defaultValue = "")
    var imei : String = "",

    @ColumnInfo(name = "capturado", defaultValue = "0")
    var capturado: Int=0,

    @ColumnInfo(name = "recibido", defaultValue = "0")
    var  recibido: Int =0
)
