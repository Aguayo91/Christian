package com.coppel.preconfirmar.entities;

import androidx.annotation.Nullable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class ConsultarDetalleMaster(
    val message: String = "",
    val type: Int = -5,
    val data: List<DetalleMasterListEntity> = listOf()
)


data class DetalleMasterList(
    val id: Int,
    val iEstatus: Int = - 5,
    val iFoliosurtido: Int = -5,
    val sMaster: String = "",
    val iCodigo: Int =-6,
    val sDescripcion: String = "",
    val sDescripciond: String = "",
    val sImei : String = "",
    val sSerie: String ="",
    val iDcf: Int =-5,
    val iCodigonuevo: Int = -5,
    val iExistencia: Int = -5,
    val sSeccion: Int = -5,
    val iKeyxPda: Int = -5,
    val iClvTarjeta: Int = -5
)

@Entity
data class DetalleMasterListEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    @SerializedName("iEstatus")
    @Nullable
    var iEstatus: Int = - 5,
    @SerializedName("iFoliosurtido")
    @Nullable
    val iFoliosurtido: Int = - 5,
    @SerializedName("sMaster")
    @Nullable
    val sMaster: String= "",
    @SerializedName("iCodigo")
    @Nullable
    val iCodigo: Int = - 5,

    @ColumnInfo(name = "sDescripcion" , defaultValue = ""  )
    @Nullable
    @SerializedName("sDescripcion")
    val sDescripcion: String,

    @ColumnInfo(name = "sDescripciond" , defaultValue = ""  )
    @Nullable
    @SerializedName("sDescripciond")
    val sDescripciond: String,

    @ColumnInfo(name = "sImei" , defaultValue = ""  )
    @Nullable
    @SerializedName("sImei")
    val sImei: String,

    @ColumnInfo(name = "sSerie" , defaultValue = ""  )
    @SerializedName("sSerie" )
    @Nullable
    val sSerie: String= "",
    @SerializedName("iDcf")
    @Nullable
    val iDcf: Int = - 5,
    @SerializedName("iCodigonuevo")
    @Nullable
    val iCodigonuevo: Int = - 5,
    @SerializedName("iExistencia")
    @Nullable
    val iExistencia: Int = - 5,
    @SerializedName("sSeccion")
    @Nullable
    val sSeccion: Int = - 5,
    @SerializedName("iKeyxPda")
    @Nullable
    val iKeyxPda: Int = - 5,
    @SerializedName("iClvTarjeta")
    @Nullable
    val iClvTarjeta: Int = - 5
)

fun DetalleMasterList.toDetalleMasterListEntity(): DetalleMasterListEntity =
    DetalleMasterListEntity(
        this.id,
        this.iEstatus,
        this.iFoliosurtido,
        this.sMaster,
        this.iCodigo,
        this.sDescripcion,
        this.sDescripciond,
        this.sSerie,
        this.sImei,
        this.iDcf,
        this.iCodigonuevo,
        this.iExistencia,
        this.sSeccion,
        this.iKeyxPda,
        this.iClvTarjeta
    )