package com.coppel.preconfirmar.recibir

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.Window
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.coppel.preconfirmar.R
import com.coppel.preconfirmar.application.AppConstants.ONBACK_PRESSED
import com.coppel.preconfirmar.application.RxApplication
import com.coppel.preconfirmar.bd.room.AppDatabase
import com.coppel.preconfirmar.databinding.ActivityRecibirBinding
import com.coppel.preconfirmar.preconfirmar.MainActivity
import com.coppel.preconfirmar.service.Resource
import com.coppel.preconfirmar.service.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RecibirActivity : AppCompatActivity() {

    companion object {
        private val TAG =
            RecibirActivity::class.qualifiedName
        var progr = 0
    }
    private lateinit var binding: ActivityRecibirBinding
    private val recibirViewModel by viewModels<RecibirViewModel> {

        RecibirViewModelFactory(
            RecibirRepositoryImpl(
                RemoteRecibirDataSource(RetrofitClient.WebService),
                LocalRecibirDataSource(
                    AppDatabase.getDatabase(this).mueblesDao(),
                    AppDatabase.getDatabase(this).celularesDao(),
                    AppDatabase.getDatabase(this).mensajeriaDao(),
                    AppDatabase.getDatabase(this).ropaDao(),
                    AppDatabase.getDatabase(this).ventasxrDao(),
                    AppDatabase.getDatabase(this).suministrosDao(),
                    AppDatabase.getDatabase(this).motosDao(),
                    AppDatabase.getDatabase(this).iniciarSurtidoDao(),
                    AppDatabase.getDatabase(this).consultarkeyXDao(),

                    )
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityRecibirBinding.inflate(layoutInflater)
        setContentView(binding.root)

        obtenerDatosActualizarSurtido()
        animationLottie()

        if (redInternetOnline(this)) {
            if ((isPackageExist("com.coppel.menu.actualizar"))) {
                recibirViewModel.iniciarValores()

            } else {
                runOnUiThread { errorAPK1() }
            }
            Log.d(TAG, "Internet= ${redInternetOnline(this)}")

        } else {
            binding.imgKey.visibility = View.GONE
            binding.progressBarCoppel.visibility = View.GONE
            binding.animationView.visibility = View.GONE
            runOnUiThread { errorAPK1() }

            Toast.makeText(this, "No hay INTERNET", Toast.LENGTH_LONG).show()
            Log.d(TAG, "No hay Internet= ${redInternetOnline(this)}")
        }
    }

    @SuppressLint("QueryPermissionsNeeded")
    fun isPackageExist(target: String): Boolean {
        return this@RecibirActivity
            .packageManager
            .getInstalledApplications(0)
            .find { info -> info.packageName == target } != null
    }


    private fun updateProgressBarEntity() {
        binding.progressBarCoppel.progress = progr
    }

    private fun animationLottie() {
        CoroutineScope(Dispatchers.Main).launch {
            Handler(Looper.getMainLooper()).postDelayed({
                binding.animationView.setAnimation("coppel.json")
                binding.animationView.playAnimation()
            }, 100)
        }
    }

    private fun animationLottieCancel() {
        Handler(Looper.getMainLooper()).postDelayed({
            binding.animationView.cancelAnimation()
        }, 0)
    }

    private fun obtenerDatosActualizarSurtido() {
        if (isPackageExist("com.coppel.menu.actualizar")) {

            try {
                val storage = createPackageContext(
                    "com.coppel.menu.actualizar",
                    0
                ).getSharedPreferences("Preferences", 0)

                val foliodelSurtido = storage.getString("FOLIO_DEL_SURTIDO", "")
                RxApplication.pref.guardarFoliodeSurtido(foliodelSurtido.toString())
                Log.d(
                    TAG, resources.getString(
                        R.string.getFoliodeSurtido, foliodelSurtido
                    )
                )
                val tienda = storage.getString("TIENDA_COPPEL", "")
                RxApplication.pref.guardarTiendaCoppel(tienda.toString())
                Log.d(
                    TAG, resources.getString(
                        R.string.getTiendaCoppel, tienda
                    )
                )
                val tipoSeleccionado = storage.getInt("TIPO_SELECCIONADO", -1)
                RxApplication.pref.guardarSeleccion(tipoSeleccionado)
                Log.d(
                    TAG, resources.getString(R.string.getTipoSeleccionado, tipoSeleccionado)
                )
                val empleado = storage.getString("EMPLEADO", "")
                RxApplication.pref.guardarEmpleado(empleado.toString())
                Log.d(
                    TAG, resources.getString(
                        R.string.getNumberEmpleado, empleado
                    )
                )
                val chofer = storage.getString("CHOFER", "")
                RxApplication.pref.guardarChofer(chofer.toString())
                Log.d(
                    TAG, resources.getString(
                        R.string.getNumberChofer, chofer
                    )
                )

                val macpda = storage.getString("MACPAD", "")
                RxApplication.pref.guardarMacPda(macpda!!)
                Log.d(
                    TAG, resources.getString(
                        R.string.getmacpda, macpda
                    )
                )
                val sipWS = storage.getString("IP_WSERVICES", "")
                RxApplication.pref.guardarPuertoWservices(sipWS.toString())
                Log.d(
                    TAG, resources.getString(
                        R.string.getIPWS, sipWS
                    )
                )
                val iPuertoWS = storage.getString("PUERTO_WSERVICES", "")
                RxApplication.pref.guardarIPwebservices(iPuertoWS.toString())
                Log.d(
                    TAG, resources.getString(
                        R.string.getPuerto, iPuertoWS
                    )
                )
                Handler(Looper.getMainLooper()).postDelayed({

                    descargarSurtido()

                }, 2000)

            } catch (e: Exception) {
                animationLottieCancel()
                runOnUiThread { errorSharedManagerPreferences() }
                Log.e(TAG, resources.getString(R.string.error_IO_Actualizar) + e.message)

                Log.e(TAG, resources.getString(R.string.error_IO_Actualizar) + e.message)
            }

        }
    }

    private fun descargarSurtido() {

        if (RxApplication.pref.obtenerHoraPreconfirmar() == 0  && RxApplication.pref.obtenerMillifinishPreconfirmar() == -1L &&
            recibirViewModel.redInternetOnline(this@RecibirActivity) && RxApplication.pref.obtenerResultadoProgress()==-1
        ) {

            progr += 75
            updateProgressBarEntity()
                recibirViewModel.getSurtidoMuebles().observe(this@RecibirActivity) { result ->
                    when (result) {

                        is Resource.Success -> {
                            if (result.data.t1.type == 2) {

                                runOnUiThread {
                                    errorReiniciarSurtido(
                                        resources.getString(R.string.title_error_assortment_folio),
                                        result.data.t1.message
                                    )
                                }

                                Log.d(
                                    TAG,
                                    "Surtido Muebles:  Response${result.data.t1.data}"
                                )
                            } else {
                                progr += 100
                                updateProgressBarEntity()
                                remoteIniciarCaptura()
                            }

                        }
                        is Resource.Loading<*> -> {
                            progr += 20
                            updateProgressBarEntity()
                            Log.d(
                                TAG,
                                resources.getString(R.string.loading)
                            )
                        }

                        is Resource.Failure -> {
                            runOnUiThread { errorAPK1() }

                            Log.d(
                                TAG,
                                resources.getString(
                                    R.string.errorWs_reStartFun
                                ) + result.exception
                            )
                        }

                        else -> {
                            Log.d(
                                TAG,
                                resources.getString(
                                    R.string.errorWs_reStartFun
                                )
                            )
                        }
                    }
                }




        } else {
            animationLottieCancel()
            progr = 100
            updateProgressBarEntity()
            localIniciarCaptura()

        }
    }

    private fun remoteIniciarCaptura() {
        CoroutineScope(Dispatchers.Main).launch {
            recibirViewModel.guardarTiempo()
            recibirViewModel.tiempoTotalMilliFinish()
        }
        startActivity(Intent(this@RecibirActivity, MainActivity::class.java))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

    }

    private fun localIniciarCaptura() {
        startActivity(Intent(this@RecibirActivity, MainActivity::class.java))
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)

    }

    private fun errorReiniciarSurtido(title: String, message: String) {
        val errorApk = Dialog(
            this@RecibirActivity
        )
        errorApk.requestWindowFeature(
            Window.FEATURE_NO_TITLE
        )
        errorApk.setCancelable(
            false
        )
        errorApk.setContentView(
            R.layout.alert_dialog
        )
        val titulo = errorApk.findViewById(R.id.title_warning) as TextView
        val mensaje = errorApk.findViewById(R.id.message_warning) as TextView
        val btnAceptar = errorApk.findViewById(R.id.btn_to_accept) as Button

        titulo.text = title
        mensaje.text = message
        btnAceptar.setText(R.string.btn_aceptar_dialog)

        btnAceptar.setOnClickListener {
            errorApk.dismiss()

        }
        errorApk.show()

    }

    private fun errorAPK1() {
        val errorApk = Dialog(
            this@RecibirActivity
        )
        errorApk.requestWindowFeature(
            Window.FEATURE_NO_TITLE
        )
        errorApk.setCancelable(
            false
        )
        errorApk.setContentView(
            R.layout.alert_dialog
        )
        val titulo = errorApk.findViewById(R.id.title_warning) as TextView
        val mensaje = errorApk.findViewById(R.id.message_warning) as TextView
        val btnAceptar = errorApk.findViewById(R.id.btn_to_accept) as Button

        titulo.text = resources.getString(R.string.title_APK1)
        mensaje.text = resources.getString(R.string.message_APK1)
        btnAceptar.setText(R.string.btn_aceptar_dialog)

        btnAceptar.setOnClickListener {
            errorApk.dismiss()

            if (isPackageExist("com.coppel.menu.actualizar")) {
                startActualizarSurtidoPDA()
            } else {
                onBackPressed()
            }
        }
        errorApk.show()

    }
    private fun errorSharedManagerPreferences() {
        val errorApk = Dialog(
            this@RecibirActivity
        )
        errorApk.requestWindowFeature(
            Window.FEATURE_NO_TITLE
        )
        errorApk.setCancelable(
            false
        )
        errorApk.setContentView(
            R.layout.alert_dialog
        )
        val titulo = errorApk.findViewById(R.id.title_warning) as TextView
        val mensaje = errorApk.findViewById(R.id.message_warning) as TextView
        val btnAceptar = errorApk.findViewById(R.id.btn_to_accept) as Button

        titulo.text = resources.getString(R.string.title_sharedpreferences)
        mensaje.text = resources.getString(R.string.message_sharedpreferences)
        btnAceptar.setText(R.string.btn_aceptar_dialog)

        btnAceptar.setOnClickListener {
            errorApk.dismiss()

            if (isPackageExist("com.coppel.menu.actualizar")) {
                startActualizarSurtidoPDA()
            } else {
                onBackPressed()
            }
        }
        errorApk.show()

    }
    private fun startActualizarSurtidoPDA() {
        Log.d(TAG, resources.getString(R.string.startActivity_APK1))
        val intent = Intent(Intent.ACTION_MAIN)
        intent.component = ComponentName(
            "com.coppel.menu.actualizar",
            "com.coppel.menu.actualizar.bienvenida.BienvenidaActivity"
        )
        startActivity(intent)
    }
    fun redInternetOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(
                Context.CONNECTIVITY_SERVICE
            ) as ConnectivityManager

        val capabilities = connectivityManager.getNetworkCapabilities(
            connectivityManager.activeNetwork
        )
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> {
                    Log.d(
                        TAG,
                        RxApplication.applicationContext().resources.getString(R.string.TransporCelular)
                    )
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Log.d(
                        TAG,
                        RxApplication.applicationContext().resources.getString(R.string.TransporWIFI)
                    )
                    return true
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    Log.d(
                        TAG,
                        RxApplication.applicationContext().resources.getString(R.string.TransporEthernet)
                    )
                    return true
                }
            }
        }
        //true PointMobile
        return true
        //false NormalApp
    }

    override fun onBackPressed() {
        super.onBackPressed()
        Log.d(TAG, ONBACK_PRESSED)
    }

}