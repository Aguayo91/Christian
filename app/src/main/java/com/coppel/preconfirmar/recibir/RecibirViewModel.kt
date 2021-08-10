package com.coppel.preconfirmar.recibir

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.liveData
import com.coppel.preconfirmar.R
import com.coppel.preconfirmar.application.RxApplication
import com.coppel.preconfirmar.bd.dao.IniciarSurtidoDao
import com.coppel.preconfirmar.bd.room.AppDatabase
import com.coppel.preconfirmar.service.Resource
import kotlinx.coroutines.Dispatchers

class RecibirViewModel(
    private val repo: RecibirRepository
): ViewModel()  {

    companion object {
        private val TAG = RecibirViewModel::class.qualifiedName

        private val iniciarSurtidoDao: IniciarSurtidoDao =
            AppDatabase.getDatabase(
            RxApplication.applicationContext()
        ).iniciarSurtidoDao()
    }

    fun redInternetOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(
                Context.CONNECTIVITY_SERVICE)
                    as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(
                connectivityManager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    Log.d(
                        TAG, RxApplication.applicationContext().resources.getString(R.string.TransporCelular))
                    return true}
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Log.d(
                        TAG, RxApplication.applicationContext().resources.getString(R.string.TransporWIFI))
                    return true}
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    Log.d(
                        TAG, RxApplication.applicationContext().resources.getString(R.string.TransporEthernet))
                    return true }
            }
        }
        //true PointMobile
        return true
        //false NormalApp
    }

    fun iniciarValores() {

        RxApplication.pref.guardarDevice(1)
        Log.d(
            TAG, RxApplication.applicationContext()
                .resources.getString(
                    R.string.device,
                    RxApplication.pref.obtenerDevice()
                )
        )

        RxApplication.pref.guardarNoParciales(1)
        Log.d(
            TAG,
            RxApplication.applicationContext()
                .resources.getString(
                    R.string.get_parciales,
                    RxApplication.pref.obtenerNoParciales()
                )
        )
        RxApplication.pref.guardarOptionPDA(1)
        Log.d(
            TAG,
            RxApplication.applicationContext()
                .resources.getString(
                    R.string.option,
                    RxApplication.pref.obtenerOptionPDA()
                )
        )
    }


    fun getSurtidoMuebles() = liveData(Dispatchers.IO) {

        emit(Resource.Loading())

        try {
            emit(
                Resource.Success(
                    NTuple23(
                        repo.getIniciarSurtido(),
                        repo.getSurtidoMuebles(),
                        repo.getSurtidoCelulares(),
                        repo.getSurtidoMensajeria(),
                        repo.getSurtidoSuministros(),
                        repo.getSurtidoRopa(),
                        repo.getSurtidoVentasXR(),
                        repo.getSurtidoVentasMotos(),
                        repo.getConsultarKeyX(),
                        repo.getConsultarFaltantes(),
                        repo.getConsultarMasterCompensadas(),
                        repo.getCompararTotalesSurtido(),
                        repo.getVerificarPreconfirmacion(),
                        repo.getActualizartipoSurtido(),
                        repo.getInformacionExistencia(),
                        repo.getConsultarTiempoAdicionalSinMaster(),
                        repo.getConsultarTiempoConfirmar(),
                        repo.getConsultarTiendaForanea(),
                        repo.getConsultarTiempoConfirmacion(),
                        repo.getConsultarLoteoCelulares(),
                        repo.getConsultarBodegasRopa(),
                        repo.getMarcarLoteoMuebles(),
                        repo.getMarcarLoteoCelulares(),
                    )
                )
            )

        } catch (e: Exception) {
            emit(Resource.Failure(e))
        }
    }

    suspend fun guardarTiempo() {

        val horapreconfirmar =  iniciarSurtidoDao.getHoraRecepcion()
        RxApplication.pref.guardarHoraPreconfirmar(horapreconfirmar)
        Log.d(TAG, "Hora ${RxApplication.pref.obtenerHoraPreconfirmar()}")

        val minutoPreconfirmar = iniciarSurtidoDao.getMinutoRecepcion()
        RxApplication.pref.guardarMinutoPreconfirmar(minutoPreconfirmar)
        Log.d(TAG, "Minuto $minutoPreconfirmar")

        val horaConfirmar = iniciarSurtidoDao.getHoraConfirmacion()
        RxApplication.pref.guardarHoraconfirmar(horaConfirmar)
        Log.d(TAG, "Minuto $horaConfirmar")

        val minutoConfirmar =iniciarSurtidoDao.getMinutoConfirmacion()
        RxApplication.pref.guardarMinutoconfirmar(minutoConfirmar)
        Log.d(TAG, "Minuto $minutoConfirmar")

    }

    fun tiempoTotalMilliFinish():Long {
        val tiempoHorapreconfirmacion = RxApplication.pref.obtenerHoraPreconfirmar() * 3600000
        val tiempoMinutopreconfirmacion = RxApplication.pref.obtenerMinutoPreconfirmar() * 60000

        val tiempoHoraconfirmacion  = RxApplication.pref.obtenerHoraconfirmar()* 3600000
        val tiempoMinutoconfirmacion  = RxApplication.pref.obtenerMinutoconfirmar()* 60000

        val eltiempoconfirmar = tiempoHoraconfirmacion + tiempoMinutoconfirmacion
        val eltiempopreconfirmacion = tiempoHorapreconfirmacion + tiempoMinutopreconfirmacion

        val millisegundosPreconfirmar = RxApplication.pref.guardarMillifinishPreconfirmar(eltiempopreconfirmacion.toLong())
        val millisegundosConfirmar = RxApplication.pref.guardarMilliFinishconfirmar(eltiempoconfirmar.toLong())

        Log.d( TAG, "tiempo Total en MilliFinish $millisegundosPreconfirmar del WS Recepción de Surtido")
        Log.d( TAG, "tiempo Total en MilliFinish $millisegundosConfirmar del WS Surtido Muebles")

        return eltiempopreconfirmacion.toLong()
    }

}


class RecibirViewModelFactory(private val repo: RecibirRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(RecibirRepository::class.java).newInstance(repo)
    }
}

data class NTuple23<T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, T13, T14, T15, T16, T17, T18, T19, T20, T21, T22, T23>(
    val t1: T1, val t2: T2, val t3: T3, val t4: T4, val t5: T5, val t6: T6,
    val t7: T7, val t8: T8, val t9: T9, val t10: T10, val t11: T11, val t12: T12,
    val t13: T13, val t14: T14, val t15: T15, val t16: T16, val t17: T17, val t18: T18,
    val t19: T19, val t20: T20, val t21: T21, val t22: T22, val t23: T23
)