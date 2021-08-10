package com.coppel.preconfirmar.entities;

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class ConsultarKeyX(
    val id: Int ,
    val message: String ="",
    val type: Int = -5,
    val data: String = ""
)


@Entity()
data class ConsultarKeyXEntity(
@PrimaryKey()
@ColumnInfo(name = "id")
val id: Int ,
@ColumnInfo(name = "message")
val message: String ="",
@ColumnInfo(name = "type")
val type: Int = -5,
@ColumnInfo(name = "data")
val data: String = ""
)

fun ConsultarKeyX.toConsultarKeyXEntity(): ConsultarKeyXEntity =
    ConsultarKeyXEntity(
        this.id,
        this.message,
        this.type,
        this.data
    )