package com.coppel.preconfirmar.service

import com.coppel.preconfirmar.application.AppConstants.CLIENT_ENTER
import com.coppel.preconfirmar.application.AppConstants.WS_MUEBLES
import com.coppel.preconfirmar.application.RxApplication
import com.coppel.preconfirmar.entities.*
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import java.util.concurrent.TimeUnit

interface WebService {

    //http://10.40.129.4:8080/WsActualizarSurtidoPDA/Surtido/Actualizar/
    //ActualizarTipoParcial?tienda=4&folio=104&tipoparcial=1

    suspend fun getActualizarTipoParcial(
        @Query("tienda") tienda: String,
        @Query("foliosurtido") folio: Int,
        @Query("tipoparcial") tipoparcial: Int,
    ): ActualizarTipoParcial


    //http://10.40.129.4:8080/WsSurtidoMueblesPDA/Surtido/SurtidoMuebles/
    // actualizartipoSurtido?tienda=4&foliosurtido=104&opcion=1&tipo=2


    @GET("actualizartipoSurtido")
    suspend fun getActualizartipoSurtido(
        @Query("tienda") tienda: String,
        @Query("foliosurtido") folio: Int,
        @Query("opcion") opcion: Int,
        @Query("tipo") tipo: Int,
    ): ActualizarTipoSurtido

    /*http://10.40.129.4:8080/WsSurtidoMueblesPDA/Surtido/SurtidoMuebles/
    IniciarSurtidoDao/?tienda=4&folio=107&empleado=93843811&tipoParcial=2&IpPDA=877e53dd34f662e5&tipoSurtido=1&folioParcial=1*/

    @GET("IniciarSurtido/")
    suspend fun getIniciarSurtido(
        @Query("tienda") tienda: String,
        @Query("folio") folio: Int,
        @Query("empleado") empleado: Int,
        @Query("tipoParcial") tipoparcial: Int,
        @Query("IpPDA") macpda: String,
        @Query("tipoSurtido") tipoSurtido: Int,
        @Query("folioParcial") folioParcial: Int,
    ): IniciarSurtido

    /*http://10.40.129.4:8080/WsSurtidoMueblesPDA/Surtido/SurtidoMuebles/
    ObtenerInformacionSurtidoMuebles/?tienda=4&folio=107*/

    @GET("ObtenerInformacionSurtidoMuebles/")
    suspend fun getSurtidoMuebles(
        @Query("tienda") tienda: String,
        @Query("folio") folio: Int,
    ): SurtidoMuebles

    //http://10.40.129.4:8080/WsSurtidoMueblesPDA/Surtido/SurtidoMuebles/
    // ObtenerInformacionSurtidoCelulares?tienda=4&folio=104

    @GET("ObtenerInformacionSurtidoCelulares")
    suspend fun getSurtidoCelulares(
        @Query("tienda") tienda: String,
        @Query("folio") folio: Int,
    ): SurtidoCelulares

    //http://10.40.129.4:8080/WsSurtidoMueblesPDA/Surtido/SurtidoMuebles/
    // ObtenerInformacionSurtidoMensajeria?tienda=4&folio=104

    @GET("ObtenerInformacionSurtidoMensajeria")
    suspend fun getSurtidoMensajeria(
        @Query("tienda") tienda: String,
        @Query("folio") folio: Int,
    ): SurtidoMensajeria

//http://10.40.129.4:8080/WsSurtidoMueblesPDA/Surtido/SurtidoMuebles/
// ObtenerInformacionSurtidoRopa?tienda=4&folio=104

    @GET("ObtenerInformacionSurtidoRopa")
    suspend fun getSurtidoRopa(
        @Query("tienda") tienda: String,
        @Query("folio") folio: Int,
    ): SurtidoRopa

    //http://10.40.129.4:8080/WsSurtidoMueblesPDA/Surtido/SurtidoMuebles/
// ObtenerInformacionSurtidoSuministros?tienda=4&folio=104

    @GET("ObtenerInformacionSurtidoSuministros")
    suspend fun getSurtidoSuministros(
        @Query("tienda") tienda: String,
        @Query("folio") folio: Int,
    ): SurtidoSuministros

    //http://10.40.129.4:8080/WsSurtidoMueblesPDA/Surtido/SurtidoMuebles/
// ObtenerInformacionSurtidoVentasXR?tienda=4&folio=104

    @GET("ObtenerInformacionSurtidoVentasXR")
    suspend fun getSurtidoVentasXR(
        @Query("tienda") tienda: String,
        @Query("folio") folio: Int,
    ): SurtidoVentasXR

    //http://10.40.129.4:8080/WsSurtidoMueblesPDA/Surtido/SurtidoMuebles/
// ObtenerInformacionSurtidoVentaMotos?tienda=4&folio=104

    @GET("ObtenerInformacionSurtidoVentaMotos")
    suspend fun getSurtidoMotos(
        @Query("tienda") tienda: String,
        @Query("folio") folio: Int,
    ): SurtidoMotos

//http://10.40.129.4:8080/WsSurtidoMueblesPDA/Surtido/SurtidoMuebles/
// ObtenerVentasXR?tienda=4&folio=104&master=M67400042909201717

    @GET("ObtenerVentasXR")
    suspend fun getObtenerVentasxR(
        @Query("tienda") tienda: String,
        @Query("folio") folio: Int,
        @Query("master") master: String,
    ):ObtenerVentasXR

//http://10.40.129.4:8080/WsSurtidoMueblesPDA/Surtido/SurtidoMuebles/
// ObtenerInformacionExistencia?tienda=4

    @GET("ObtenerInformacionExistencia")
    suspend fun getInformacionExistencia(
        @Query("tienda") tienda: String
    ): ObtenerInformacionExistencia

    // http://10.40.129.4:8080/WsSurtidoMueblesPDA/Surtido/SurtidoMuebles/
// Consultarkeyx?tienda=4&folio=104

    @GET("Consultarkeyx")
    suspend fun getConsultarKeyX(
        @Query("tienda") tienda: String,
        @Query("folio") folio: Int
    ): ConsultarKeyX

//http://10.40.129.4:8080/WsSurtidoMueblesPDA/Surtido/SurtidoMuebles/
// ConsultarFaltantes?tienda=4&folio=104

    @GET("ConsultarFaltantes")
    suspend fun getConsultarFaltantes(
        @Query("tienda") tienda: String,
        @Query("folio") folio: Int
    ): ConsultarFaltantes

    // http://10.40.129.4:8080/WsSurtidoMueblesPDA/Surtido/SurtidoMuebles/
// ConsultarMasterCompensadas?folio=104&tienda=4

    @GET("ConsultarMasterCompensadas")
    suspend fun getConsultarMasterCompensadas(
        @Query("folio") folio: Int,
        @Query("tienda") tienda: String
    ): ConsultarMasterCompensadas

//http://10.40.129.4:8080/WsSurtidoMueblesPDA/Surtido/SurtidoMuebles/
// consultartiempoadicionalsinmaster?tienda=4

    @GET("consultartiempoadicionalsinmaster")
    suspend fun getConsultarTiempoAdicionalSinMaster(
        @Query("tienda") tienda: String
    ): ConsultarTiempoAdicionalSinMaster

    //http://10.40.129.4:8080/WsSurtidoMueblesPDA/Surtido/SurtidoMuebles/
// consultartiempoparaconfirmar?tienda=4&folio=104&parcial=1

    @GET("consultartiempoparaconfirmar")
    suspend fun getConsultarTiempoConfirmar(
        @Query("tienda") tienda: String,
        @Query("folio") folio: Int,
        @Query("parcial") parcial: Int
    ): ConsultarTiempoConfirmar

    // http://10.40.129.4:8080/WsSurtidoMueblesPDA/Surtido/SurtidoMuebles/
// consultartiendaforanea?tienda=4

    @GET("consultartiendaforanea")
    suspend fun getConsultarTiendaForanea(
        @Query("tienda") tienda: String
    ): ConsultarTiendaForanea

//http://10.40.129.4:8080/WsSurtidoMueblesPDA/Surtido/SurtidoMuebles/
// consultamasterenproceso?tienda=4&foliosurtido=104&master=M67400042909201717&opcion=1&sMacPDA=877e53dd34f662e5

    @GET("consultamasterenproceso")
    suspend fun getConsultarMasterEnProceso(
        @Query("tienda") tienda: String,
        @Query("foliosurtido") foliosurtido: Int,
        @Query("master") master: String,
        @Query("opcion") opcion: String,
        @Query("MacPDA") MacPDA: String,
    ): ConsultarMasterEnProceso

    //  http://10.40.129.4:8080/WsSurtidoMueblesPDA/Surtido/SurtidoMuebles/
//  ConsultarTiempoConfirmacion?tienda=4&folio=104&folioparcial=2

    @GET("ConsultarTiempoConfirmacion")
    suspend fun getConsultarTiempoConfirmacion(
        @Query("tienda") tienda: String,
        @Query("foliosurtido") foliosurtido: Int,
        @Query("folioparcial") folioparcial:Int,
    ): ConsultarTiempoConfirmacion

    //http://10.40.129.4:8080/WsSurtidoMueblesPDA/Surtido/SurtidoMuebles/
// ConsultarDetalleMaster?tienda=4&master=M674 00042909201717&folio=104&area=Muebles

    @GET("ConsultarDetalleMaster")
    suspend fun getConsultarDetalleMaster(
        @Query("tienda") tienda: String,
        @Query("master") master: String,
        @Query("folio") folio: Int,
        @Query("area") area:String,
    ): ConsultarDetalleMaster

//http://10.40.129.4:8080/WsSurtidoMueblesPDA/Surtido/SurtidoMuebles/
// ConsultarLoteoCelulares?tienda=4&folio=104&folioparcial=2&dispositivo=1

    @GET("ConsultarLoteoCelulares")
    suspend fun getConsultarLoteoCelulares(
        @Query("tienda") tienda: String,
        @Query("folio") folio: Int,
        @Query("folioparcial") folioparcial:Int,
        @Query("dispositivo") dispositivo:Int
    ): ConsultarLoteoCelulares

    //http://10.40.129.4:8080/WsSurtidoMueblesPDA/Surtido/SurtidoMuebles/
// consultarImeiEnOtraMaster?tienda=4&folio=104&imei=309336042064228&master=C13900040611201801&parcial=0&opcion=1

    @GET("consultarImeiEnOtraMaster")
    suspend fun getConsultarImeiEnOtraMaster(
        @Query("tienda") tienda: String,
        @Query("folio") folio: Int,
        @Query("imei") imei:String,
        @Query("master") master:String,
        @Query("parcial") parcial:String,
        @Query("opcion") opcion:Int
    ): ConsultarImeiEnOtraMaster

//http://10.40.129.4:8080/WsSurtidoMueblesPDA/Surtido/SurtidoMuebles/
// ConsultarCodigoImei?tienda=4&folio=104&imei=309336042064228&opcion=1

    @GET("ConsultarCodigoImei")
    suspend fun getConsultarCodigoImei(
        @Query("tienda") tienda: String,
        @Query("folio") folio: Int,
        @Query("imei") imei:String,
        @Query("opcion") opcion:Int
    ): ConsultarCodigoImei

//http://10.40.129.4:8080/WsSurtidoMueblesPDA/Surtido/SurtidoMuebles/
// ConsultarCentrosDeBodega?tienda=4&tipoMovto=A&area=5&foliosurtido=104

    @GET("ConsultarCentrosDeBodega")
    suspend fun getConsultarCentrosBodega(
        @Query("tienda") tienda: String,
        @Query("tipoMovto") tipoMovto: String,
        @Query("area") area:Int,
        @Query("foliosurtido") foliosurtido:Int
    ): ConsultarCentrosBodega

    //http://10.40.129.4:8080/WsSurtidoMueblesPDA/Surtido/SurtidoMuebles/
// ConsultarCodigo?tienda=4&Codigo=101281

    @GET("ConsultarCodigo")
    suspend fun getConsultarCodigo(
        @Query("tienda") tienda: String,
        @Query("Codigo") codigo: Int,
    ): ConsultarCodigo

//http://10.40.129.4:8080/WsSurtidoMueblesPDA/Surtido/SurtidoMuebles/
// consultarCodigotarjetascontenido?tienda=4&upc=07675026715&opcion=1&foliosurtido=104

    @GET("consultarCodigotarjetascontenido")
    suspend fun getConsultarCodigoTarjetas(
        @Query("tienda") tienda: String,
        @Query("upc") upc: Int,
        @Query("opcion") opcion: Int,
        @Query("foliosurtido") foliosurtido:Int
    ): ConsultarCodigoTarjetas


//http://10.40.129.4:8080/WsSurtidoMueblesPDA/Surtido/SurtidoMuebles/
// ConsultarMaestroMuebles?tienda=4&codigo=101281

    @GET("ConsultarMaestroMuebles")
    suspend fun getConsultarMaestroMuebles(
        @Query("tienda") tienda: String,
        @Query("codigo") codigo: Int,
    ): ConsultarMaestroMuebles

    // http://10.40.129.4:8080/WsSurtidoMueblesPDA/Surtido/SurtidoMuebles/
// ConsultarCodigoMaestroMuebles?tienda=4&codigo=1267

    @GET("ConsultarCodigoMaestroMuebles")
    suspend fun getConsultarCodigoMaestroMuebles(
        @Query("tienda") tienda: String,
        @Query("codigo") codigo: Int,
    ): ConsultarCodigoMaestroMuebles


//http://10.40.129.4:8080/WsSurtidoMueblesPDA/Surtido/SurtidoMuebles/
// ConsultarInsumos?tienda=4&codigo=5332

    @GET("ConsultarInsumos")
    suspend fun getConsultarInsumos(
        @Query("tienda") tienda: String,
        @Query("codigo") codigo: Int,
    ): ConsultarInsumos

    //http://10.40.129.4:8080/WsSurtidoMueblesPDA/Surtido/SurtidoMuebles/
// ConsultarValeSurtido?foliosurtido=104&vale=901250728

    @GET("ConsultarValeSurtido")
    suspend fun getConsultarValeSurtido(
        @Query("foliosurtido") foliosurtido: Int,
        @Query("vale") vale: Int,
    ): ConsultarValeSurtido

// http://10.40.129.4:8080/WsSurtidoMueblesPDA/Surtido/SurtidoMuebles/
// ConsultarVale?vale=901250728&tienda=4&folio=104

    @GET("ConsultarVale")
    suspend fun getConsultarVale(
        @Query("vale") vale: Int,
        @Query("tienda") tienda: String,
        @Query("foliosurtido") foliosurtido: Int
    ): ConsultarVale

//http://10.40.129.4:8080/WsSurtidoMueblesPDA/Surtido/SurtidoMuebles/
// consultarBodegasRopa?tienda=4

    @GET("consultarBodegasRopa")
    suspend fun getConsultarBodegasRopa(
        @Query("tienda") tienda: String,
    ): ConsultarBodegasRopa

    //http://10.40.129.4:8080/WsSurtidoMueblesPDA/Surtido/SurtidoMuebles/
// CompararTotalesSurtido?tienda=4&foliosurtido=104

    @GET("CompararTotalesSurtido")
    suspend fun getCompararTotalesSurtido(
        @Query("tienda") tienda: String,
        @Query("foliosurtido") foliosurtido: Int
    ): CompararTotalesSurtido

//http://10.40.129.4:8080/WsSurtidoMueblesPDA/Surtido/SurtidoMuebles/
// eliminarControlSurtidoPDA?tienda=4&folio=104&keyx=1534&ippda=877e53dd34f662e5&parcial=1

    @GET("eliminarControlSurtidoPDA")
    suspend fun getEliminarControlSurtidoPda(
        @Query("tienda") tienda: String,
        @Query("folio") folio: Int,
        @Query("folio") foliosurtido: Int,
        @Query("keyx") keyx: Int,
        @Query("ippda") macpda: String,
        @Query("parcial") parcial: Int,
    ): EliminarControlSurtidoPda

    //http://10.40.129.4:8080/WsSurtidoMueblesPDA/Surtido/SurtidoMuebles/
// grabarTeimpoPreconfirmacion?tienda=4&folio=104&opcion=1&tiempo=00:23:00&tiempoextra=30&folioparcial=104

    @GET("grabarTeimpoPreconfirmacion")
    suspend fun getGrabarTiempoPreconfirmacion(
        @Query("tienda") tienda: String,
        @Query("folio") folio: Int,
        @Query("opcion") opcion: Int,
        @Query("tiempo") tiempo: String,
        @Query("tiempoextra") tiempoextra: String,
        @Query("folioparcial") folioparcial: Int,
    ): GrabarTiempoPreconfirmacion


    //http://10.40.129.4:8080/WsSurtidoMueblesPDA/Surtido/SurtidoMuebles/
// grabarErrorSurtido?tienda=4&mensaje=Ha Ocurrido Un Error&clase=ConsultasDAL()&metodo=grabarError()&query=“SELECTfun_GrabarError(?, ?, ?,?, ?, ?, ?, ?);”

    @GET("grabarErrorSurtido")
    suspend fun getGrabarErrorSurtido(
        @Query("tienda") tienda: String,
        @Query("mensaje") mensaje: String,
        @Query("clase") clase: String,
        @Query("metodo") metodo: String,
        @Query("query") tiempo: String,
    ): GrabarErrorSurtido

    //http://10.40.129.4:8080/WsSurtidoMueblesPDA/Surtido/SurtidoMuebles/
// marcarLoteoMuebles?tienda=7646&folio=201361&folioparcial=1

    @GET("marcarLoteoMuebles")
    suspend fun getMarcarLoteoMuebles(
        @Query("tienda") tienda: String,
        @Query("folio") folio: Int,
        @Query("folioparcial") folioparcial: Int
    ): MarcarLoteoMuebles

//http://10.40.129.4:8080/WsSurtidoMueblesPDA/Surtido/SurtidoMuebles/
// marcarLoteoCelulares?folio=104&tienda=4&folioparcial=1&dispositivo=1

    @GET("marcarLoteoCelulares")
    suspend fun getMarcarLoteoCelulares(
        @Query("folio") folio: Int,
        @Query("tienda") tienda: String,
        @Query("folioparcial") folioparcial: Int,
        @Query("dispositivo") dispositivo: Int
    ): MarcarLoteoCelulares

    // http://10.40.129.4:8080/WsSurtidoMueblesPDA/Surtido/SurtidoMuebles/
// RegresarMasterCambiadaCelulares?tienda=4&folio=104&master=C13900040611201801&imei=309336042064228

    @GET("RegresarMasterCambiadaCelulares")
    suspend fun getRegeresarMasterCambiadaCalulares(
        @Query("folio") folio: Int,
        @Query("tienda") tienda: String,
        @Query("master") master: String,
        @Query("imei") imei: String,
    ): RegeresarMasterCambiadaCalulares

//http://10.40.129.4:8080/WsSurtidoMueblesPDA/Surtido/SurtidoMuebles/
// reiniciarDescargaSurtido?tienda=4&folio=104&keyx=2341&ippda=d9bf002aabe72b26&rubro=1

    @GET("reiniciarDescargaSurtido")
    suspend fun getReiniciarDescargaSurtido(
        @Query("tienda") tienda: String,
        @Query("folio") folio: Int,
        @Query("keyx") keyx: String,
        @Query("ippda") ippda: String,
        @Query("rubro") rubro: String,
    ): ReiniciarDescargaSurtido

    //http://10.40.129.4:8080/WsSurtidoMueblesPDA/Surtido/SurtidoMuebles/
// verificarpreconfirmacion?tienda=4&folio=104

    @GET("verificarpreconfirmacion")
    suspend fun getVerificarPreconfirmacion(
        @Query("tienda") tienda: String,
        @Query("folio") folio: Int,
    ): VerificarPreconfirmacion

    @POST("ActualizarSurtidoMuebles")
    suspend fun actualizaSurtidoMuebles(
        @Query("tienda") tienda : Int,
        @Query("foliosurtido") folioSurtido : Int,
        @Body body : List<SurtidoMueblesListEntity>
    ): VerificarActualizacionSurtido

    @POST("ActualizarSurtidoCelulares")
    suspend fun actualizaSurtidoCelulares(
        @Query("tienda") tienda : Int,
        @Query("foliosurtido") folioSurtido : Int,
        @Body body : List<SurtidoCelularesListEntity>
    ): VerificarActualizacionSurtido

    @POST("ActualizarSurtidoRopa")
    suspend fun actualizaSurtidoRopa(
        @Query("tienda") tienda : Int,
        @Query("foliosurtido") folioSurtido : Int,
        @Body body : List<SurtidoRopaListEntity>
    ): VerificarActualizacionSurtidoRopa

    @POST("ActualizarSurtidoSuministros")
    suspend fun actualizaSurtidoSuministros(
        @Query("tienda") tienda : Int,
        @Query("foliosurtido") folioSurtido : Int,
        @Body body : List<SurtidoSuministrosListEntity>
    ): VerificarActualizacionSurtido

    @POST("ActualizarSurtidoMensajeria")
    suspend fun actualizaSurtidoMensajeria(
        @Query("tienda") tienda : Int,
        @Query("foliosurtido") folioSurtido : Int,
        @Body body : List<SurtidoMensajeriaListEntity>
    ): VerificarActualizacionSurtido

    @POST("ActualizarSurtidoVentasR")
    suspend fun actualizaSurtidoVentasR(
        @Query("tienda") tienda : Int,
        @Query("foliosurtido") folioSurtido : Int,
        @Body body : List<SurtidoVentasXRListEntity>
    ): VerificarActualizacionSurtido

}

object RetrofitClient {


    val WebService by lazy {

        val baseIPWSURL = RxApplication.pref.obtenerPuertoWservices()
        val basePORTWSURL = RxApplication.pref.obtenerIPwebservices()
        val BASE_URL = "$CLIENT_ENTER$baseIPWSURL:$basePORTWSURL$WS_MUEBLES"

        val logging: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
        val httpClient = OkHttpClient.Builder()
            .addInterceptor(logging)
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .build()


        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient)
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().create()))
            .build().create(WebService::class.java)

    }
}