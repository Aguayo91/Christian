package com.coppel.preconfirmar.preconfirmar

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import com.coppel.preconfirmar.application.RxApplication
import com.coppel.preconfirmar.bd.dao.*
import com.coppel.preconfirmar.bd.room.AppDatabase
import com.coppel.preconfirmar.entities.*
import com.coppel.preconfirmar.recibir.RemoteRecibirDataSource
import com.coppel.preconfirmar.service.RetrofitClient
import kotlinx.coroutines.*

class HomeRepositorio (context: Context){

    private var dataSourceRemote: RemoteRecibirDataSource = RemoteRecibirDataSource(RetrofitClient.WebService)
    private val dao: MueblesDao = AppDatabase.getDatabase(context).mueblesDao()
    private val celularesDao: CelularesDao = AppDatabase.getDatabase(context).celularesDao()
    private val suministrosDaoDao: SuministrosDao = AppDatabase.getDatabase(context).suministrosDao()
    private val todoDao: TodoDao = AppDatabase.getDatabase(context).todoDao()
    private val mensajeriaDao: MensajeriaDao = AppDatabase.getDatabase(context).mensajeriaDao()
    private val ropaDao: RopaDao = AppDatabase.getDatabase(context).ropaDao()
    private val motosDao: MotosDao = AppDatabase.getDatabase(context).motosDao()
    private val ventasR: VentasxrDao = AppDatabase.getDatabase(context).ventasxrDao()
    private val datelleDao: DetalleMasterDao = AppDatabase.getDatabase(context).consultarDetalleMasterDao()


    suspend fun obtenerSobrante(): Int = withContext(Dispatchers.IO) {
        todoDao.getSobrante()
    }

    suspend fun obtenerTotales(folio: Int): Int = withContext(Dispatchers.IO) {
        dao.updateCountUI(folio)
    }

    fun obtenerCapturado(): LiveData<List<Todo>> = todoDao.getAllRowsTodo()

    suspend fun actualizaSurtidos() {

        val listaMuebles = dao.getAllMuebles()
        val listaCelulares = celularesDao.getAllCelulares()
        val listaSuministros = suministrosDaoDao.getAllSuministros()
        val listaRopa = ropaDao.getAllRopa()
        val listaVentaR = ventasR.getAllVentasR()
        val listaMensajeria = mensajeriaDao.getAllMensajeria()

        dataSourceRemote.actualizaSurtidoMuebles(listaMuebles)
        dataSourceRemote.actualizaSurtidoCelulares(listaCelulares)
        dataSourceRemote.actualizaSurtidoSuminsitross(listaSuministros)
        dataSourceRemote.actualizaSurtidoRopa(listaRopa)
        dataSourceRemote.actualizaSurtidoVentasR(listaVentaR)
        dataSourceRemote.actualizaSurtidoMensajeria(listaMensajeria)
        dataSourceRemote.grabarTiempo()

        Log.i("RESPUESTA_SERVICE", "Termino de ejecutarse todos")

    }

    suspend fun obtenerLoteo() {
        val listalotear = todoDao.getPendinetesLotear()
        listalotear.forEach {
            val data = dataSourceRemote.getConsultarDetalleMaster(it.codigo, getArea(it.tipo))
            datelleDao.saveAllDetalle(data.data)
        }
    }

    private fun getArea(tipo: Int): String {

        return when(tipo){
            1 ->  "Muebles"
            2 ->  "Celulares"
            4 ->  "Suministros"
            else -> ""
        }

    }

    suspend fun actualizaTodo(estatus : Int , sMaster : String ) {
        todoDao.updateMaster(estatus,sMaster)
    }

    suspend fun actualizaTodo(todo : Todo ) {
        todoDao.updateTodo(todo)
    }

    fun obtenerFaltantes(): LiveData<List<SurtidoMueblesListEntity>> = dao.getAllFaltantes()

    suspend fun actualizaIrregularidad(todo: Todo) = withContext(Dispatchers.IO) {
        todoDao.updateTodo(todo)
    }

    suspend fun obtieneMasterPreconfirmada(smaster : String) = todoDao.getMastercapturada(smaster)

    suspend fun obtieneTotalDetalleMaster(sMaster : String) = datelleDao.obtieneTotaleByMaster(sMaster)
    suspend fun buscaCodigoLoteado(codigo : Int, sMaster: String ) : List<DetalleMasterListEntity> =  datelleDao.buscarcodigo(icodigo = codigo, smaster = sMaster)

    suspend fun buscaCodigoLoteadoByImei(imei : String, sMaster: String ) : List<DetalleMasterListEntity> =  datelleDao.buscarcodigoByImei(icodigo = imei, smaster = sMaster)

    suspend fun actualizaEstatusProducto(producto  : DetalleMasterListEntity){
        datelleDao.updateDetalleMaster(producto)
    }

    suspend fun obtenerLoteado (sMaster : String ) : List<Todo> = todoDao.getLoteado(smaster = sMaster)

    suspend fun buscasMaster(codigo: String) : Todo?{

        var ismaster = 1

        val resulTodo: List<Todo> = CoroutineScope(Dispatchers.IO).async {
            todoDao.getMastercapturada(codigo)
        }.await()

        val resultMuebles: Deferred<List<SurtidoMueblesListEntity>> =
            CoroutineScope(Dispatchers.IO).async {
                Log.i("BUSCAR_MASTER", "Entre a buscar en muebles")
                ismaster
                dao.getDescripcionsMasterMuebles(codigo,0,1)
            }

        val resultCelulares: Deferred<List<SurtidoCelularesListEntity>> =
            CoroutineScope(Dispatchers.IO).async {
                Log.i("BUSCA_CODIGO", "Entre a buscar en celulares")
                if (RxApplication.pref.obtenerScanner().startsWith("C")) {
                    ismaster
                    celularesDao.getDescripcionMasterCelulares(codigo,0)
                } else {
                    celularesDao.getDescripcionCelularesCodigo(codigo)
                }
            }
        val resultSuministros: Deferred<List<SurtidoSuministrosListEntity>> =
            CoroutineScope(Dispatchers.IO).async {
                Log.i("BUSCA_CODIGO", "Entre a buscar en suministros")
                if (RxApplication.pref.obtenerScanner().startsWith("S")) {
                    ismaster
                    suministrosDaoDao.getDescripcionMasterSuministros(codigo,0)
                } else {
                    suministrosDaoDao.getDescripcionCodigo(codigo)
                }
            }

        val mueblesBusqueda: List<SurtidoMueblesListEntity> = resultMuebles.await()
        val celularesBusqueda: List<SurtidoCelularesListEntity> = resultCelulares.await()
        val suministrosBusqueda: List<SurtidoSuministrosListEntity> = resultSuministros.await()
        val codigotodo: List<Todo> = resulTodo

        var rubro = 0
        var encontrado = false
        var itemcapturado : Todo? = null

        if (codigotodo.isNotEmpty() && encontrado==false){
            encontrado = true
            rubro = 1
            ismaster
            itemcapturado =
                Todo(
                    0,
                    RxApplication.pref.obtenerScanner(),
                    "leido",
                    false,
                    false,
                    false,
                    false,
                    rubro,
                    codigotodo[0].ikeyx,
                    ismaster ,
                    "",
                    0
                )
        }

        if (mueblesBusqueda.isNotEmpty() && !encontrado) {
            rubro = 1
            encontrado = true
            ismaster
            itemcapturado =
            Todo(
                0,
                RxApplication.pref.obtenerScanner(),
                mueblesBusqueda[0].sDescripcion,
                false,
                false,
                false,
                false,
                rubro,
                mueblesBusqueda[0].iKeyx,
                ismaster,
                "",
                0

            )
            dao.updatListMaster(
                RxApplication.pref.obtenerScanner(),
                mueblesBusqueda[0].iKeyx
            )
        }

        if (celularesBusqueda.isNotEmpty() && encontrado==false) {
            rubro = 2
            encontrado = true
            ismaster
            itemcapturado =
                Todo(
                    0,
                    RxApplication.pref.obtenerScanner(),
                    celularesBusqueda[0].sDescripcion,
                    false,
                    false,
                    false,
                    false,
                    rubro,
                    celularesBusqueda[0].iKeyx,
                    ismaster,
                    "",
                    0
                )

            celularesDao.updatListMasterCelulares(
                RxApplication.pref.obtenerScanner(),
                celularesBusqueda[0].iKeyx
            )

        }

        if (suministrosBusqueda.isNotEmpty() && encontrado==false) {
            rubro = 4
            encontrado = true
            ismaster
            itemcapturado =
                Todo(
                    0,
                    RxApplication.pref.obtenerScanner(),
                    suministrosBusqueda[0].sDescripcion,
                    false,
                    false,
                    false,
                    false,
                    rubro,
                    suministrosBusqueda[0].iKeyx,
                    ismaster,
                    "",
                    0
                )

            suministrosDaoDao.updatListMasterSuministros(
                RxApplication.pref.obtenerScanner(),
                suministrosBusqueda[0].iKeyx
            )
        }


        return itemcapturado
    }

    suspend fun buscarsMasterCodigo(smaster: String) : Todo?{


        val resulTodo: List<Todo> = CoroutineScope(Dispatchers.IO).async {
            todoDao.getMastercapturada(smaster)
        }.await()

        val resultRopa: Deferred<List<SurtidoRopaListEntity>> =
            CoroutineScope(Dispatchers.IO).async {
                Log.i("BUSCA_CODIGO", "Entre a buscar en ropa")
                ropaDao.getDescripcionMasterRopa(smaster,0)
            }
        val resultMensajeria: Deferred<List<SurtidoMensajeriaListEntity>> =
            CoroutineScope(Dispatchers.IO).async {
                Log.i("BUSCA_CODIGO", "Entre a buscar en mensajeria")
                mensajeriaDao.getDescripcionMasterMensajeria(smaster,0)
            }


        val codigotodo: List<Todo> = resulTodo
        val ropaBusqueda: List<SurtidoRopaListEntity> = resultRopa.await()
        val mensajeriaBusqueda: List<SurtidoMensajeriaListEntity> = resultMensajeria.await()
        var ismaster = 1
        var rubro = 0
        var encontrado = false

        var itemcapturado : Todo? = null

        if (codigotodo.isNotEmpty() && encontrado==false){
            encontrado = true
            rubro = 1

            itemcapturado =
                Todo(
                    0,
                    RxApplication.pref.obtenerScanner(),
                    "leido",
                    false,
                    false,
                    false,
                    false,
                    rubro,
                    codigotodo[0].ikeyx,
                    ismaster,
                    "",
                    1
                )
        }

        if (ropaBusqueda.isNotEmpty() && !encontrado) {
            encontrado = true
            rubro = 3
            itemcapturado = Todo(
                0,
                RxApplication.pref.obtenerScanner(),
                ropaBusqueda[0].sDescripcion,
                false,
                false,
                false,
                false,
                rubro,
                ropaBusqueda[0].iKeyx,
                ismaster,
                "",
                1

            )

            ropaDao.updatListRopa(RxApplication.pref.obtenerScanner(),ropaBusqueda[0].iKeyx)
        }
        if (mensajeriaBusqueda.isNotEmpty() && !encontrado) {
            encontrado = true
            rubro = 7
            itemcapturado =
                Todo(
                    0,
                    RxApplication.pref.obtenerScanner(),
                    mensajeriaBusqueda[0].sDescripcion,
                    false,
                    false,
                    false,
                    false,
                    rubro,
                    mensajeriaBusqueda[0].iKeyx,
                    1,
                    "",
                    1
                )

            mensajeriaDao.updatListMasterMensajeria(
                RxApplication.pref.obtenerScanner(),mensajeriaBusqueda[0].iKeyx
            )
        }

        return itemcapturado
    }

    suspend fun buscaCodigoEnSurtido(codigo : String): Boolean{

        var ismaster = false
        val resulTodo: List<Todo> =
            withContext(CoroutineScope(Dispatchers.IO).coroutineContext) {
                todoDao.getMastercapturada(codigo)
            }

        val resultMuebles: Deferred<List<SurtidoMueblesListEntity>> =
            CoroutineScope(Dispatchers.IO).async {
                Log.i("BUSCA_CODIGO", "Entre a buscar en muebles")

                if (codigo.startsWith("M")) {
                    ismaster = true
                    dao.getDescripcionsMasterMuebles(codigo,1,1)
                } else {
                    dao.getDescripcionCodigo(codigo)
                }
            }
        val resultCelulares: Deferred<List<SurtidoCelularesListEntity>> =
            CoroutineScope(Dispatchers.IO).async {
                Log.i("BUSCA_CODIGO", "Entre a buscar en celulares")
                if (codigo.startsWith("C")) {
                    ismaster = true
                    celularesDao.getDescripcionMasterCelulares(codigo,1)
                } else {
                    celularesDao.getDescripcionCelularesCodigo(codigo)
                }
            }
        val resultSuministros: Deferred<List<SurtidoSuministrosListEntity>> =
            CoroutineScope(Dispatchers.IO).async {
                Log.i("BUSCA_CODIGO", "Entre a buscar en suministros")
                if (codigo.startsWith("S")) {
                    ismaster = true
                    suministrosDaoDao.getDescripcionMasterSuministros(codigo,1)
                } else {
                    suministrosDaoDao.getDescripcionCodigo(codigo)
                }
            }
        val resultRopa: Deferred<List<SurtidoRopaListEntity>> =
            CoroutineScope(Dispatchers.IO).async {
                Log.i("BUSCA_CODIGO", "Entre a buscar en ropa")
                ropaDao.getDescripcionMasterRopa(codigo,1)
            }
        val resultVentasxr: Deferred<List<SurtidoVentasXRListEntity>> =
            CoroutineScope(Dispatchers.IO).async {
                Log.i("BUSCA_CODIGO", "Entre a buscar en ventasxr")
                ventasR.getDescripcionVentasxR(codigo)
            }
        val resultMensajeria: Deferred<List<SurtidoMensajeriaListEntity>> =
            CoroutineScope(Dispatchers.IO).async {
                Log.i("BUSCA_CODIGO", "Entre a buscar en mensajeria")
                mensajeriaDao.getDescripcionMasterMensajeria(codigo,1)
            }

        val resultMotos: Deferred<List<SurtidoMotosListEntity>> =
            CoroutineScope(Dispatchers.IO).async {
                Log.i("BUSCA_CODIGO", "Entre a buscar en motos")
                motosDao.getDescripcionMotosCodigo(codigo)
            }
        val codigotodo: List<Todo> = resulTodo
        val mueblesBusqueda: List<SurtidoMueblesListEntity> = resultMuebles.await()
        val celularesBusqueda: List<SurtidoCelularesListEntity> = resultCelulares.await()
        val suministrosBusqueda: List<SurtidoSuministrosListEntity> = resultSuministros.await()
        val ropaBusqueda: List<SurtidoRopaListEntity> = resultRopa.await()
        val ventasBusqueda: List<SurtidoVentasXRListEntity> = resultVentasxr.await()
        val mensajeriaBusqueda: List<SurtidoMensajeriaListEntity> = resultMensajeria.await()
        val motosBusqueda: List<SurtidoMotosListEntity> = resultMotos.await()

        return (mueblesBusqueda.isNotEmpty() || celularesBusqueda.isNotEmpty()
                || suministrosBusqueda.isNotEmpty() || ropaBusqueda.isNotEmpty()
                || ventasBusqueda.isNotEmpty() || mensajeriaBusqueda.isNotEmpty()
                ||motosBusqueda.isNotEmpty()) ||codigotodo.isNotEmpty()

    }

    suspend fun saveTodo(todo : Todo){
        todoDao.saveTodo(todo)
    }
}