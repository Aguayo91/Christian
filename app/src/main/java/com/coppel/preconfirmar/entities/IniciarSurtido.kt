package com.coppel.preconfirmar.entities;
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

data class IniciarSurtido(

    val message: String = "",
    val type: Int = -5,
    val data: List<IniciarSurtidoList> = listOf()
)
data class IniciarSurtidoList(
    val id: Int ,
    val lEmpleado: Int = -1,
    val iTipoParcial: Int = -1,
    val iFolioParcial: Int = -1,
    val sIpPDA: String = "",
    val sTiempo: String = "",
    val iHorarecepcion: Int = -1,
    val iMinutorecepcion: Int = -1,
    val iHoraconfirmacion: Int = -1,
    val iMinutoconfirmacion: Int = -1,
    val iForanea: Int = -1,
    val iFlagMasterOtraTienda: Int = -1,
    val iTipoSurtido: Int = -1,
    val ihoraservidor: Int = -1,
    val iminutoservidor: Int = -1,
    val isegundoservidor: Int = -1,
    val stiempo: String = "",
    val iKeyxCtl: Int = -1,
    val iEtapa: Int = -1,
    val iReingreso: Int = -1
)

@Entity()
data class IniciarSurtidoListEntity(
    @PrimaryKey()
    @ColumnInfo(name = "id")
    val id: Int ,
    @ColumnInfo(name = "lEmpleado")
    val lEmpleado: Int = -1,

    @ColumnInfo(name = "iTipoParcial")
    val iTipoParcial: Int = -1,
    @ColumnInfo(name = "iFolioParcial")
    val iFolioParcial: Int = -1,
    @ColumnInfo(name = "sIpPDA")
    val sIpPDA: String = "",
    @ColumnInfo(name = "sTiempo")
    val sTiempo: String = "",
    @ColumnInfo(name = "iHorarecepcion")
    val iHorarecepcion: Int = -1,
    @ColumnInfo(name = "iMinutorecepcion")
    val iMinutorecepcion: Int = -1,
    @ColumnInfo(name = "iHoraconfirmacion")
    val iHoraconfirmacion: Int = -1,
    @ColumnInfo(name = "iMinutoconfirmacion")
    val iMinutoconfirmacion: Int = -1,
    @ColumnInfo(name = "iForanea")
    val iForanea: Int = -1,
    @ColumnInfo(name = "iFlagMasterOtraTienda")
    val iFlagMasterOtraTienda: Int = -1,
    @ColumnInfo(name = "iTipoSurtido")
    val iTipoSurtido: Int = -1,
    @ColumnInfo(name = "ihoraservidor")
    val ihoraservidor: Int = -1,
    @ColumnInfo(name = "sEiminutoservidorstado")
    val iminutoservidor: Int = -1,
    @ColumnInfo(name = "isegundoservidor")
    val isegundoservidor: Int = -1,
    @ColumnInfo(name = "iKeyxCtl")
    val iKeyxCtl: Int = -1,
    @ColumnInfo(name = "iEtapa")
    val iEtapa: Int = -1,
    @ColumnInfo(name = "iReingreso")
    val iReingreso: Int = -1
)

fun IniciarSurtidoList.toIniciarSurtidoListEntity(): IniciarSurtidoListEntity =
    IniciarSurtidoListEntity(
        this.id,
        this.lEmpleado,
        this.iTipoParcial,
        this.iFolioParcial,
        this.sIpPDA,
        this.sTiempo,
        this.iHorarecepcion,
        this.iMinutorecepcion,
        this.iHoraconfirmacion,
        this.iMinutoconfirmacion,
        this.iForanea,
        this.iFlagMasterOtraTienda,
        this.iTipoSurtido,
        this.ihoraservidor,
        this.iminutoservidor,
        this.isegundoservidor,
        this.iKeyxCtl,
        this.iEtapa,
        this.iReingreso,
    )
