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
import com.orhanobut.logger.Logger
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
    private val sinetiquetaDao : SinEtiquetaDao = AppDatabase.getDatabase(context).sinetiquetaDao()


    suspend fun obtenerSobrante(): Int = withContext(Dispatchers.IO) {
        todoDao.getSobrante()
    }

    suspend fun obtenerTotales(folio: Int): Int = withContext(Dispatchers.IO) {
        dao.updateCountUI(folio)
    }

    fun obtenerCapturado(): LiveData<List<Todo>> = todoDao.getAllRowsTodo()

    //suspend fun obtenerCapoturadoLoteo():LiveData<List<SinEtiqueta>> = sinetiquetaDao.getAllTodo()

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
            val data = dataSourceRemote.getConsultarDetalleMaster(it.codigo, getArea(it.rubro))
            datelleDao.saveAllDetalle(data.data)
        }
    }

    private fun getArea(rubro: Int): String {

        return when(rubro){
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

    suspend fun buscarsMasterMaestro(escanner: String) : Todo?{

        var ismaster = 1

        val resulTodo: List<Todo> = CoroutineScope(Dispatchers.IO).async {
            todoDao.getMastercapturada(escanner)
        }.await()

        val resultMuebles: Deferred<List<SurtidoMueblesListEntity>> =
            CoroutineScope(Dispatchers.IO).async {
                Logger.d("BUSCAR MASTER MUEBLES --> Entre a buscar en muebles")

                    ismaster
                    dao.getDescripcionsMasterMuebles(escanner, 0, 1)
            }

        val resultCelulares: Deferred<List<SurtidoCelularesListEntity>> =
            CoroutineScope(Dispatchers.IO).async {
                Logger.d("BUSCAR MASTER CELULARES -->Entre a buscar en celulares")

                    ismaster
                    celularesDao.getDescripcionMasterCelulares(escanner,0)

            }
        val resultSuministros: Deferred<List<SurtidoSuministrosListEntity>> =
            CoroutineScope(Dispatchers.IO).async {
                Logger.d("BUSCAR MASTER SUMINISTROS --> Entre a buscar en suministros")

                    ismaster
                    suministrosDaoDao.getDescripcionMasterSuministros(escanner,0)
            }


        val mueblesBusqueda: List<SurtidoMueblesListEntity> = resultMuebles.await()
        val celularesBusqueda: List<SurtidoCelularesListEntity> = resultCelulares.await()
        val suministrosBusqueda: List<SurtidoSuministrosListEntity> = resultSuministros.await()
        val codigotodo: List<Todo> = resulTodo

        var rubro = 0
        var encontrado = false
        var itemcapturado : Todo? = null

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
        if (mueblesBusqueda.isNotEmpty() && !encontrado) {
            encontrado = true
            ismaster
            itemcapturado =
                Todo(
                    0,
                    RxApplication.pref.obtenerScanner(),
                    mueblesBusqueda[0].sDescripcion,
                    false,
                    true,
                    false,
                    false,
                    mueblesBusqueda[0].iRubro,
                    mueblesBusqueda[0].iKeyx,
                    0,
                    "",
                    0

                )
            dao.updateListCodigo(
                RxApplication.pref.obtenerScanner()

            )
        }

        if (celularesBusqueda.isNotEmpty() && !encontrado ) {
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
                    true,
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

        if (suministrosBusqueda.isNotEmpty() && !encontrado ) {
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
                RxApplication.pref.eliminarScanner()
        }

        if (codigotodo.isNotEmpty() && !encontrado){
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

        return itemcapturado
    }




    suspend fun buscarMasterPorCodigo(smaster: String) : Todo?{

        val resultMuebles: Deferred<List<SurtidoMueblesListEntity>> =
            CoroutineScope(Dispatchers.IO).async {
                Logger.d("BUSCAR MASTER MUEBLES --> Entre a buscar en muebles")

                    dao.getDescripcionCodigo(smaster)
            }

        val resulTodo: List<Todo> = CoroutineScope(Dispatchers.IO).async {
            todoDao.getMastercapturada(smaster)
        }.await()

        val resultRopa: Deferred<List<SurtidoRopaListEntity>> =
            CoroutineScope(Dispatchers.IO).async {
                Logger.d("BUSCAR CODIGO --> Entre a buscar en ropa")
                ropaDao.getDescripcionMasterRopa(smaster,0)
            }
        val resultMensajeria: Deferred<List<SurtidoMensajeriaListEntity>> =
            CoroutineScope(Dispatchers.IO).async {
                Logger.d("BUSCAR CODIGO ---> Entre a buscar en mensajeria")
                mensajeriaDao.getDescripcionMasterMensajeria(smaster,0)
            }
        val resultMotos: Deferred<List<SurtidoMotosListEntity>> =
            CoroutineScope(Dispatchers.IO).async {
                Log.i("BUSCA_CODIGO", "Entre a buscar en motos")
                motosDao.getDescripcionMotosCodigo(smaster)
            }

        val mueblesBusqueda: List<SurtidoMueblesListEntity> = resultMuebles.await()
        val ropaBusqueda: List<SurtidoRopaListEntity> = resultRopa.await()
        val mensajeriaBusqueda: List<SurtidoMensajeriaListEntity> = resultMensajeria.await()
        val motosBusqueda: List<SurtidoMotosListEntity> = resultMotos.await()
        val codigotodo: List<Todo> = resulTodo

        val ismaster = 1
        var rubro = 5
        var encontrado = false

        var itemcapturado : Todo? = null

        if (codigotodo.isNotEmpty() && mueblesBusqueda[0].iRubro==5||
            codigotodo.isNotEmpty() && mueblesBusqueda[0].iRubro==6){
            encontrado = true
            itemcapturado =
                Todo(
                    0,
                    RxApplication.pref.obtenerScanner(),
                    "sobrante",
                    true,
                    false,
                    false,
                    false,
                    10,
                    0,
                    3 ,
                    "",
                    0
                )
        }

        if (mueblesBusqueda.isNotEmpty() && !encontrado && mueblesBusqueda[0].iExistencia == 0) {
            encontrado = true
            rubro = 6

            itemcapturado =
                Todo(
                    0,
                    RxApplication.pref.obtenerScanner(),
                    mueblesBusqueda[0].sDescripcion,
                    false,
                    true,
                    false,
                    false,
                    rubro,
                    mueblesBusqueda[0].iKeyx,
                    0,
                    "",
                    0
                )

            dao.updatListCodigo(
                RxApplication.pref.obtenerScanner(), mueblesBusqueda[0].iKeyx
            )

        }
        if (mueblesBusqueda.isNotEmpty() && !encontrado ){
            encontrado = true


            itemcapturado =
                Todo(
                    0,
                    RxApplication.pref.obtenerScanner(),
                    mueblesBusqueda[0].sDescripcion,
                    false,
                    false,
                    false,
                    false,
                    mueblesBusqueda[0].iRubro,
                    mueblesBusqueda[0].iKeyx,
                    0,
                    "",
                    0
                )

            dao.updatListCodigo(
                RxApplication.pref.obtenerScanner(), mueblesBusqueda[0].iKeyx
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
                    mensajeriaBusqueda[0].sMaster,
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

        if (motosBusqueda.isNotEmpty() && !encontrado) {
            encontrado = true
            rubro = 9
            itemcapturado =
                Todo(
                    0,
                    motosBusqueda[0].sMaster,
                    motosBusqueda[0].sDescripcion,
                    false,
                    true,
                    false,
                    false,
                    rubro,
                    motosBusqueda[0].iKeyx,
                    0,
                    "",
                    1
                )

            motosDao.updateListMotosCodigo(
                RxApplication.pref.obtenerScanner(),mensajeriaBusqueda[0].iKeyx
            )
        }

        if (codigotodo.isNotEmpty() && ropaBusqueda.isNotEmpty()||
            codigotodo.isNotEmpty()&&mensajeriaBusqueda.isNotEmpty()){
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
                    0 ,
                    "",
                    0
                )
        }

        return itemcapturado
    }


        /* Loteo
        *
        *
        *
        *
        * */
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