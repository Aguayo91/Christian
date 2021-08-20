package com.coppel.preconfirmar.loteo

import androidx.lifecycle.*
import com.coppel.preconfirmar.entities.DetalleMasterListEntity
import com.coppel.preconfirmar.entities.SinEtiqueta
import com.coppel.preconfirmar.entities.Todo
import com.coppel.preconfirmar.preconfirmar.HomeRepositorio
import kotlinx.coroutines.launch

class LoteoViewModel(
    val repositorio: HomeRepositorio
) : ViewModel() {

    private lateinit var showIrregularidad: LiveData<Boolean>
    private lateinit var productosLoteados: MutableLiveData<List<Todo>>
    private lateinit var totalByMaster: MutableLiveData<Int>
    private lateinit var loteados: LiveData<List<SinEtiqueta>>
    private lateinit var sMasterAlotear: MutableLiveData<String>

    private lateinit var showMasterCaptured : MutableLiveData<Boolean>
    private lateinit var showMasterNoPeconfirmada : MutableLiveData<Boolean>
    private lateinit var showMasterNoSurtida : MutableLiveData<Boolean>

    private lateinit var isJabaCelular : MutableLiveData<Boolean>

    private lateinit var showIrregularidadMuebles : MutableLiveData<Boolean>
    private lateinit var showIrregularidadCelulares : MutableLiveData<Boolean>
    private lateinit var showIrregularidadSuministros : MutableLiveData<Boolean>

    fun initValues() {
        showIrregularidad = MutableLiveData()
        productosLoteados = MutableLiveData()
        sMasterAlotear = MutableLiveData("")
        totalByMaster = MutableLiveData(0)
        showMasterCaptured = MutableLiveData(false)
        showMasterNoPeconfirmada = MutableLiveData(false)
        showMasterNoSurtida = MutableLiveData(false)
        isJabaCelular = MutableLiveData(false)
        showIrregularidadMuebles = MutableLiveData(false)
        showIrregularidadCelulares = MutableLiveData(false)
        showIrregularidadSuministros = MutableLiveData(false)
    }

    fun isShowIrregularidadMuebles(): LiveData<Boolean> = showIrregularidadMuebles
    fun isShowIrregularidadCelulares(): LiveData<Boolean> = showIrregularidadCelulares
    fun isShowIrregularidadSuministros(): LiveData<Boolean> = showIrregularidadSuministros

    fun isShowIrregularidad(): LiveData<Boolean> = showIrregularidad

    fun getMasterAlotear(): LiveData<String> = sMasterAlotear

    fun getListaLoteado(): LiveData<List<Todo>> = productosLoteados

    fun getTotalesByMaster(): LiveData<Int> = totalByMaster

    fun isMasterCapturada() : LiveData<Boolean> = showMasterCaptured

    fun isMasterNoPreconfirmada() : LiveData<Boolean> = showMasterNoPeconfirmada

    fun isMasterNoSurtida() : LiveData<Boolean> = showMasterNoSurtida

    fun isJabaCelular() : LiveData<Boolean> = isJabaCelular

    fun saveTodo(s: String, master: String) {
        viewModelScope.launch {
            repositorio.saveTodo(
                Todo(
                    0,
                    s,
                    "SIN MASTER",
                    false,
                    false,
                    true,
                    false,
                    0,
                    0,
                    0,
                    master,
                    0
                )
            )

        }
    }

    /*suspend fun obtenerCapturadoLoteado():LiveData<List<SinEtiqueta>>{
        loteados = repositorio.obtenerCapoturadoLoteo()
        return loteados
    }*/

    fun buscaCodigo(codigoCapturado: String) {

        if (codigoCapturado.startsWith("M")
            || codigoCapturado.startsWith("C")
            || codigoCapturado.startsWith("S")
        ) {

            viewModelScope.launch {
                val estaEnSurtido = repositorio.buscaCodigoEnSurtido(codigoCapturado)

                if(estaEnSurtido){

                    val masterPreConfirmada  : List<Todo> = repositorio.obtieneMasterPreconfirmada(codigoCapturado)
                    if(masterPreConfirmada.isNotEmpty()){
                        if(masterPreConfirmada[0].MasterStatus == 1){
                            showMasterCaptured.postValue(true)
                        }else{
                            totalByMaster.postValue(repositorio.obtieneTotalDetalleMaster(codigoCapturado))
                            sMasterAlotear.postValue(codigoCapturado)
                            if(codigoCapturado.startsWith("C")){
                                isJabaCelular.postValue(true)
                            }else{
                                isJabaCelular.postValue(false)
                            }
                        }
                    }else{
                        showMasterNoPeconfirmada.postValue(true)
                    }
                }else{
                    showMasterNoSurtida.postValue(true)
                }
            }

        }else{
            viewModelScope.launch {
                if(sMasterAlotear.value != ""){

                    var resultadoBusqueda: List<DetalleMasterListEntity>

                    if(sMasterAlotear.value?.startsWith("C") == true){
                        resultadoBusqueda =
                            repositorio.buscaCodigoLoteadoByImei(codigoCapturado, sMasterAlotear.value!!)
                    }else{
                        resultadoBusqueda =
                            repositorio.buscaCodigoLoteado(codigoCapturado.toInt(), sMasterAlotear.value!!)
                    }

                    if (resultadoBusqueda.isNotEmpty()) {


                        repositorio.saveTodo(
                            Todo(
                                0,
                                codigo = resultadoBusqueda[0].iCodigo.toString(),
                                description = if(getTipo(sMasterAlotear.value!!) == 2){
                                    resultadoBusqueda[0].sDescripcion
                                }else{
                                    resultadoBusqueda[0].sDescripciond
                                },
                                sobrante = false,
                                exhibir = false,
                                irregularidad = false,
                                isChecked = false,
                                rubro = getTipo(sMasterAlotear.value!!),
                                ikeyx = resultadoBusqueda[0].iKeyxPda,
                                isMaster = 0,
                                Master = sMasterAlotear.value!!,
                                MasterStatus = 0,
                                imei = resultadoBusqueda[0].sImei,
                                capturado = if(resultadoBusqueda[0].iExistencia == 1 ){ 1 }else{0},
                                recibido = resultadoBusqueda[0].iExistencia
                            )
                        )
                        resultadoBusqueda[0].iEstatus = 1
                        repositorio.actualizaEstatusProducto(resultadoBusqueda[0])
                    }
                    productosLoteados.postValue(repositorio.obtenerLoteado(sMaster = sMasterAlotear.value!!))
                    totalByMaster.postValue(repositorio.obtieneTotalDetalleMaster(sMasterAlotear.value!!))
                }

            }

        }

    }

    private fun getTipo(sMaster: String): Int {

        if(sMaster.startsWith("C")){
            return 2
        }else if(sMaster.startsWith("M")){
            return 1
        }else if(sMaster.startsWith("S")){
            return 3
        }else{
            return 0
        }

    }

    fun clearValues() {
        productosLoteados.postValue(mutableListOf())
        sMasterAlotear.postValue("")
        totalByMaster.postValue(0)
    }

    fun actualizaTodo() {
        viewModelScope.launch {
            repositorio.actualizaTodo(1, sMasterAlotear.value!!)
        }
    }

    fun actualizaCantidad(todo : Todo) {
        viewModelScope.launch {
            repositorio.actualizaTodo(todo)
            productosLoteados.postValue(repositorio.obtenerLoteado(sMaster = sMasterAlotear.value!!))}
    }

    fun clickIrregularidad(todo : Todo) {

        when(todo.rubro){
            1 -> showIrregularidadMuebles.postValue(true)
            2-> showIrregularidadCelulares.postValue(true)
            3-> showIrregularidadSuministros.postValue(true)

        }

    }

}

class LoteoViewModelFactory(private val repositorio: HomeRepositorio) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return modelClass.getConstructor(HomeRepositorio::class.java).newInstance(repositorio)
    }
}