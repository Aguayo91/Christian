package com.coppel.preconfirmar.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

data class SurtidoRopa(
    val message: String = "",
    val type :Int = -5,
    val data: List<SurtidoRopaList> = listOf()
)
data class SurtidoRopaList(
    val id:Int,
    val sMaster: String = "",
    val sDescripcion: String = "",
    val sSerie: String = "",
    val sClave: String = "",
    val iFolioSurtido: Int = -1,
    val iCodigo: Int = -1,
    val iExistencia: Int = -1,
    val iCincho1: Int = -1,
    val iCincho2: Int = -1,
    val iKeyx: Int = -1,
    val iRubro: Int = -1,
    val iLectura: Int = -1,
    val sEstado: String = "",
    val iTipoJava: Int = -1,
    val iTipoContenedor: Int = -1,
    val iTipoContenedorCapturado: Int = -1,
    val iMoto: Int = -1,
    val iSobrante: Int = -1,
    val iIrregularidad: Int = -1,
    val iActa: Int = -1,
    val iParcial: Int = -1,
    val iClvMaster: Int = -1,
    val iFactura: Int = -1,
    val iCantidad: Int = -1,
    val iTalla: Int = -1,
    val iPrecio: Int = -1,
    val iTiendaEntrega: Int = -1,
    val iCinchosCoincidentes: Int = -1,
    val iSeccion: Int = -1,

    )
@Entity
data class SurtidoRopaListEntity(
    @PrimaryKey(autoGenerate = true)
    val id:Int,
    @SerializedName("sMaster")
    val sMaster: String = "",
    @SerializedName("sDescripcion")
    val sDescripcion: String = "",
    @SerializedName("sSerie")
    val sSerie: String = "",
    @SerializedName("sClave")
    val sClave: String = "",
    @SerializedName("iFolioSurtido")
    val iFolioSurtido: Int = -1,
    @SerializedName("iCodigo")
    val iCodigo: Int = -1,
    @SerializedName("iExistencia")
    val iExistencia: Int = -1,
    @SerializedName("iCincho1")
    val iCincho1: Int = -1,
    @SerializedName("iCincho2")
    val iCincho2: Int = -1,
    @SerializedName("iKeyx")
    val iKeyx: Int = -1,
    @SerializedName("iRubro")
    val iRubro: Int = -1,
    @SerializedName("iLectura")
    val iLectura: Int = -1,
    @SerializedName("sEstado")
    val sEstado: String = "",
    @SerializedName("iTipoJava")
    val iTipoJava: Int = -1,
    @SerializedName("iTipoContenedor")
    val iTipoContenedor: Int = -1,
    @SerializedName("iTipoContenedorCapturado")
    val iTipoContenedorCapturado: Int = -1,
    @SerializedName("iMoto")
    val iMoto: Int = -1,
    @SerializedName("iSobrante")
    val iSobrante: Int = -1,
    @SerializedName("iIrregularidad")
    val iIrregularidad: Int = -1,
    @SerializedName("iActa")
    val iActa: Int = -1,
    @SerializedName("iParcial")
    val iParcial: Int = -1,
    @SerializedName("iClvMaster")
    val iClvMaster: Int = -1,
    @SerializedName("iFactura")
    val iFactura: Int = -1,
    @SerializedName("iCantidad")
    val iCantidad: Int = -1,
    @SerializedName("iTalla")
    val iTalla: Int = -1,
    @SerializedName("iPrecio")
    val iPrecio: Int = -1,
    @SerializedName("iTiendaEntrega")
    val iTiendaEntrega: Int = -1,
    @SerializedName("iCinchosCoincidentes")
    val iCinchosCoincidentes: Int = -1,
    @SerializedName("iSeccion")
    val iSeccion: Int = -1,
)

fun SurtidoRopaList.toSurtidoRopaListEntity(): SurtidoRopaListEntity =
    SurtidoRopaListEntity(
        this.id,
        this.sMaster,
        this.sDescripcion,
        this.sSerie,
        this.sClave,
        this.iFolioSurtido,
        this.iCodigo,
        this.iExistencia,
        this.iCincho1,
        this.iCincho2,
        this.iKeyx,
        this.iRubro,
        this.iLectura,
        this.sEstado,
        this.iTipoJava,
        this.iTipoContenedor,
        this.iTipoContenedorCapturado,
        this.iMoto,
        this.iSobrante,
        this.iIrregularidad,
        this.iActa,
        this.iParcial,
        this.iClvMaster,
        this.iFactura,
        this.iCantidad,
        this.iTalla,
        this.iPrecio,
        this.iTiendaEntrega,
        this.iCinchosCoincidentes,
        this.iSeccion,
    )