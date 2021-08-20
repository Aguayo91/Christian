package com.coppel.preconfirmar.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class SinEtiqueta(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "_id")
    val id: Int = 0,

    @ColumnInfo(name = "codigo")
    val codigo: String = "",

    @ColumnInfo(name = "description")
    val descripcion: String = "SIN MASTER",

    @ColumnInfo(name = "cantidad")
    val cantidad: Int = -5,

    @ColumnInfo(name = "isChecked")
    var isChecked: Boolean = false,

    @ColumnInfo(name = "irregularidad")
    var irregularidad: Boolean = false,
)