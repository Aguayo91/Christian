package com.coppel.preconfirmar.preconfirmar

import android.app.Dialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.*
import com.coppel.preconfirmar.R
import com.coppel.preconfirmar.application.RxApplication
import com.coppel.preconfirmar.entities.Todo
import kotlinx.coroutines.launch

class SharedViewModel(
    private val repositorio: HomeRepositorio
): ViewModel() {

    val savemuebles = MutableLiveData<String>()

    var totalesPorFolio: MutableLiveData<List<Int>>

    var finalizarsurtido:MutableLiveData<Boolean>

    private var normal: MutableLiveData<Todo>
    private var pisorack: MutableLiveData<Todo>
    private var showRubros : MutableLiveData<Boolean>
    private var etiquetaleida: MutableLiveData<Todo>
    private var mueblesbodega: MutableLiveData<Todo>
    var invalida: MutableLiveData<Boolean>


    private lateinit var capturados: LiveData<List<Todo>>

    private var startIrregularidadMuebles: MutableLiveData<Todo>
    private var startIrregularidadCedis: MutableLiveData<Todo>
    private var startIrregularidadJaba: MutableLiveData<Todo>
    private var startIrregularidadRopa: MutableLiveData<Todo>


    init {

        totalesPorFolio = MutableLiveData()

        finalizarsurtido = MutableLiveData<Boolean>()

        normal = MutableLiveData<Todo>()

        showRubros = MutableLiveData<Boolean>()

        etiquetaleida = MutableLiveData<Todo>()

        pisorack = MutableLiveData<Todo>()

        invalida = MutableLiveData<Boolean>()

        mueblesbodega = MutableLiveData<Todo>()

        startIrregularidadMuebles = MutableLiveData<Todo>()
        startIrregularidadCedis = MutableLiveData<Todo>()
        startIrregularidadJaba = MutableLiveData<Todo>()
        startIrregularidadRopa = MutableLiveData<Todo>()
    }

    fun getStartActivityIrregularidadMuebles() = startIrregularidadMuebles

    fun getNormal() = normal
    fun getEtiquetaLeida()= etiquetaleida
    fun getSHowRubros() = showRubros
    fun getPisoyRack()= pisorack
    fun mueblesbodega ()= mueblesbodega



    fun saveTodo(todo : Todo){
        viewModelScope.launch {
            repositorio.saveTodo(todo)
            showRubros.postValue(false)
        }
    }

    fun actualizaSurtidos() {
        viewModelScope.launch {
            repositorio.actualizaSurtidos()
            repositorio.obtenerLoteo()
            finalizarsurtido.postValue(true)

            Log.i("RESPUESTA_SERVICE","Se termino con exito ")

        }

    }


    fun obtenerCapturado(): LiveData<List<Todo>> {
        capturados = repositorio.obtenerCapturado()
        return capturados
    }



    fun onClickedIrregularidad(position : Int, isChecked : Boolean, context : Context) {

        var todo : Todo? = capturados.value?.get(position)

        if(todo != null){

            todo.irregularidad = isChecked
            viewModelScope.launch{
                repositorio.actualizaIrregularidad(todo)
                if(isChecked){
                    when(todo.rubro){
                        1-> //IrregularidadActivity
                            startIrregularidadMuebles.postValue(todo)
                        3-> //IrregularidadCedisActivity
                            startIrregularidadCedis.postValue(todo)
                        8-> //IrregualridadRopaActivity
                            startIrregularidadRopa.postValue(todo)
                    }
                }
            }
        }


    }

    fun buscarMasterPorLetra(escanner : String){
        viewModelScope.launch {
            //Metodo del repos
            val todo : Todo? = repositorio.buscarsMasterMaestro(escanner)

            if(todo?.description=="leido"){
                etiquetaleida.postValue(todo)
            }
            else if (todo?.isMaster==1){
                normal.postValue(todo)
            }

        }
    }

    fun buscasMasterCodigos(smaster: String) {
        viewModelScope.launch {
            //Metodo del repos
            val todo: Todo? = repositorio.buscarMasterPorCodigo(smaster)

            if (todo==null){
                 invalida.postValue(true)
        }
            else if (todo?.description == "sobrante") {
                etiquetaleida.postValue(todo)
            }

            else if (todo?.description == "leido") {
                etiquetaleida.postValue(todo)
            }
            else if    (todo?.isMaster == 0 && todo.rubro==5) {
                mueblesbodega.postValue(todo)
            }

            else if    (todo?.isMaster == 0) {
                pisorack.postValue(todo)

            } else if (todo?.isMaster == 1) {
                normal.postValue(todo)
                }

        }
    }


    fun obtenerdescripcion(s: String) {
        savemuebles.postValue(s)
    }

    fun obtieneDetalleLoteo(){
        viewModelScope.launch {
            repositorio.obtenerLoteo()
        }
    }

    fun obtenerfaltantes() = repositorio.obtenerFaltantes()

    fun obtenerTotales(folio : Int)  {
        viewModelScope.launch {
            val total = repositorio.obtenerTotales(folio)
            val sobrantes = repositorio.obtenerSobrante()

            totalesPorFolio.postValue(arrayListOf(total,sobrantes))

        }
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
                        "SharedViewModel", RxApplication.applicationContext()
                            .resources.getString(R.string.TransporCelular))
                    return true}
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    Log.d(
                        "SharedViewModel", RxApplication.applicationContext()
                            .resources.getString(R.string.TransporWIFI))
                    return true}
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    Log.d(
                        "SharedViewModel", RxApplication.applicationContext()
                            .resources.getString(R.string.TransporEthernet))
                    return true }
            }
        }
        //true PointMobile
        return true
        //false NormalApp
    }
    fun alertainformativa(
        context: Context,
        titlewarning: String,
        messagewarning: String
    ) {
        Log.d("SharedViewModel", "ADVERTENCIA INFORMATIVA UI --->SharedViewModel")
        val recepcionAlert = Dialog(context)
        recepcionAlert.requestWindowFeature(Window.FEATURE_NO_TITLE)
        recepcionAlert.setCancelable(false)
        recepcionAlert.setContentView(R.layout.alert_dialog)
        recepcionAlert.window!!.setWindowAnimations(R.style.DialogAnimation)
        val body = recepcionAlert.findViewById(R.id.title_warning) as TextView
        val message = recepcionAlert.findViewById(R.id.message_warning) as TextView
        val btntoAccept = recepcionAlert.findViewById(R.id.btn_to_accept) as Button
        btntoAccept.setText(R.string.btn_aceptar_dialog)
        body.text = titlewarning
        message.text = messagewarning
        btntoAccept.setOnClickListener {recepcionAlert.dismiss()}
        recepcionAlert.show()
    }


}


class HomeViewModelFactory(private val repositorio: HomeRepositorio) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(HomeRepositorio::class.java).newInstance(repositorio)
    }
}