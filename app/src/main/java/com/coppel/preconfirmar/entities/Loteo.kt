package com.coppel.preconfirmar.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Loteo(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    val _id: Int = 0,
    @ColumnInfo(name = "codigo")
    val codigo: String,
    @ColumnInfo(name = "description")
    val description: String,
    @ColumnInfo(name = "imei")
    val imei: Boolean = false,
    @ColumnInfo(name = "sobrante")
    val sobrante: Boolean = false,
    @ColumnInfo(name = "exhibir")
    val exhibir: Boolean = false,
    @ColumnInfo(name = "irregularidad")
    var irregularidad: Boolean = false,
    @ColumnInfo(name = "isChecked")
    var isChecked:Boolean = false,
    @ColumnInfo(name = "tipo")
    var tipo:Int,
    @ColumnInfo(name = "iKeyx")
    var ikeyx:Int,
)