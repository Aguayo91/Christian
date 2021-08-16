package com.coppel.preconfirmar.application

import android.content.Context

class Pref(val context: Context) {

    val COPPELSHARED = "Preferences"
    val storage = context.getSharedPreferences(COPPELSHARED, 0)

    val TIENDA_COPPEL = "TIENDA_COPPEL"
    fun guardarTiendaCoppel(value: String) {
        storage.edit().putString(TIENDA_COPPEL, value).apply()
    }

    fun obtenerTiendaCoppel(): String {
        return storage.getString(TIENDA_COPPEL, "")!!
    }


    val TIPO_SELECCIONADO = "TIPO_SELECCIONADO"
    fun guardarSeleccion(value: Int) {
        storage.edit().putInt(TIPO_SELECCIONADO, value).apply()
    }

    fun obtenerTipoSeleccionado(): Int {
        return storage.getInt(TIPO_SELECCIONADO, -1)
    }


    val FOLIO_DEL_SURTIDO = "FOLIO_DEL_SURTIDO"
    fun obtenerFoliodelSurtido(): String {
        return storage.getString(FOLIO_DEL_SURTIDO, "")!!
    }

    fun guardarFoliodeSurtido(value: String) {
        storage.edit().putString(FOLIO_DEL_SURTIDO, value).apply()
    }

    val DISPOSITIVO = "DISPOSITIVO"
    fun guardarDevice(value: Int) {
        storage.edit().putInt(DISPOSITIVO, value).apply()
    }

    fun obtenerDevice(): Int {
        return storage.getInt(DISPOSITIVO, 1)
    }


    val MACPAD = "MACPAD"
    fun guardarMacPda(value: String) {
        storage.edit().putString(MACPAD, value).apply()
    }

    fun obtenerMacPDA(): String {
        return storage.getString(MACPAD, "")!!
    }

    val SDKVERSION = "SDKVERSION"
    fun guardarSDKVersion(value: String) {
        storage.edit().putString(SDKVERSION, value).apply()
    }

    fun obtenerSDKVersion(): String {
        return storage.getString(SDKVERSION, "")!!
    }

    val EMPLEADO = "EMPLEADO"
    fun guardarEmpleado(value: String) {
        storage.edit().putString(EMPLEADO, value).apply()
    }

    fun obtenerEmpleado(): String {
        return storage.getString(EMPLEADO, "")!!
    }

    val CHOFER = "CHOFER"
    fun guardarChofer(value:String){
        storage.edit().putString(CHOFER,value).apply()
    }
    fun obtenerChrofer():String{
        return storage.getString(CHOFER,"")!!
    }

    val IP_WSERVICES = "IP_WSERVICES"
    fun guardarIPwebservices(value: String) {
        storage.edit().putString(IP_WSERVICES, value).apply()
    }
    fun obtenerIPwebservices(): String {
        return storage.getString(IP_WSERVICES, "")!!
    }


    val PUERTO_WSERVICES = "PUERTO_WSERVICES"
    fun guardarPuertoWservices(value: String) {
        storage.edit().putString(PUERTO_WSERVICES, value).apply()
    }

    fun obtenerPuertoWservices(): String {
        return storage.getString(PUERTO_WSERVICES, "")!!
    }

    val PRECONFIRMAR = "PRECONFIRMAR"
    fun guardarPreconfirmarFlag(value:Boolean){
        storage.edit().putBoolean(PRECONFIRMAR,value).apply()
    }
    fun obtenerPreconfirmarFlag():Boolean{
        return storage.getBoolean(PRECONFIRMAR, false)
    }

    val NO_PARCIALES = "NO_PARCIALES"
    fun guardarNoParciales(value: Int) {
        storage.edit().putInt(NO_PARCIALES, value).apply()
    }

    fun obtenerNoParciales(): Int {
        return storage.getInt(NO_PARCIALES, 1)
    }

    val SCANNER = "Scanner"
    fun guardarScanner(value: String) {
        storage.edit().putString(SCANNER, value).apply()
    }

    fun obtenerScanner(): String {
        return storage.getString(SCANNER, "")!!
    }

    val OPTION_PDA = "OPTION_PDA"
    fun guardarOptionPDA(value: Int) {
        storage.edit().putInt(OPTION_PDA, value).apply()
    }

    fun obtenerOptionPDA(): Int {
        return storage.getInt(OPTION_PDA, 1)
    }

    val HORA_PRECONFIRMAR = "HORA_PRECONFIRMAR"
    fun guardarHoraPreconfirmar(value: Int) {
        storage.edit().putInt(HORA_PRECONFIRMAR, value).apply()
    }

    fun obtenerHoraPreconfirmar(): Int {
        return storage.getInt(HORA_PRECONFIRMAR, 0)
    }

    val MINUTO_PRECONFIRMAR = "MINUTO_PRECONFIRMAR"
    fun guardarMinutoPreconfirmar(value: Int) {
        storage.edit().putInt(MINUTO_PRECONFIRMAR, value).apply()
    }

    fun obtenerMinutoPreconfirmar(): Int {
        return storage.getInt(MINUTO_PRECONFIRMAR, 1)
    }

    val HORA_CONFIRMAR = "HORA_CONFIRMAR"
    fun guardarHoraconfirmar(value: Int) {
        storage.edit().putInt(HORA_CONFIRMAR, value).apply()
    }

    fun obtenerHoraconfirmar(): Int {
        return storage.getInt(HORA_CONFIRMAR, 0)
    }

    val MINUTO_CONFIRMAR = "MINUTO_CONFIRMAR"
    fun guardarMinutoconfirmar(value: Int) {
        storage.edit().putInt(MINUTO_CONFIRMAR, value).apply()
    }

    fun obtenerMinutoconfirmar(): Int {
        return storage.getInt(MINUTO_CONFIRMAR, 0)
    }




    val TIEMPO_MILLI_PRECONFIRMAR = "TIEMPO_MILLIPRECONFIRMAR"
    fun guardarMillifinishPreconfirmar(value: Long) {
        storage.edit().putLong(TIEMPO_MILLI_PRECONFIRMAR, value).apply()
    }

    fun obtenerMillifinishPreconfirmar(): Long {
        return storage.getLong(TIEMPO_MILLI_PRECONFIRMAR, -1)
    }


    val TIEMPO_MILLI_CONFIRMAR = "TIEMPO_MILLICONFIRMAR"
    fun guardarMilliFinishconfirmar(value: Long) {
        storage.edit().putLong(TIEMPO_MILLI_CONFIRMAR, value).apply()
    }

    fun obtenerMilliFinishconfirmar():Long{
        return storage.getLong(TIEMPO_MILLI_CONFIRMAR, -1)
    }

    val MILLISUNTILFINISHED  = "MILLISUNTILFINISHED"
    fun guardarMillisPausa(value: Long) {
        storage.edit().putLong(MILLISUNTILFINISHED, value).apply()
    }

    fun obtenerMillisPausa(): Long {
        return storage.getLong(MILLISUNTILFINISHED, 0)
    }

    val RESULTADO_PROGRESS = "ResultadoProgress"
    fun guardarResultadoProgress(value: Int) {
        storage.edit().putInt(RESULTADO_PROGRESS, value).apply()
    }

    fun obtenerResultadoProgress():Int{
        return storage.getInt(RESULTADO_PROGRESS, -1)
    }

    val SIN_ETIQUETA = "SIN_ETIQUETA"
    fun storageSinEtiqueta(value: Int) {
        storage.edit().putInt(SIN_ETIQUETA, value).apply()
    }

    fun getSinEtiqueta(): Int {
        return storage.getInt(SIN_ETIQUETA, 0)
    }

    val FINALIZAR_PRECONFIRMACION = "FINALIZAR_PRECONFIRMACION"
    fun guardarFINALPRECONFIRMACION(value: Boolean) {
        storage.edit().putBoolean(FINALIZAR_PRECONFIRMACION, value).apply()
    }

    fun obtenerFINALPRECONFIRMACION(): Boolean {
        return storage.getBoolean(FINALIZAR_PRECONFIRMACION, false)
    }
}