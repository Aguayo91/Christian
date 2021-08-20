package com.coppel.preconfirmar.preconfirmar

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Dialog
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.CountDownTimer
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.View
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.cedisropa.scanner.OnScanListener
import com.coppel.preconfirmar.R
import com.coppel.preconfirmar.application.AppConstants.COUNTDOWNTIMER
import com.coppel.preconfirmar.application.RxApplication
import com.coppel.preconfirmar.databinding.FragmentHomeBinding
import com.coppel.preconfirmar.entities.Todo
import com.coppel.preconfirmar.singleton.OnClickListenerAdapter
import com.orhanobut.logger.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.NumberFormat


class HomeFragment : Fragment(R.layout.fragment_home), OnClickListenerAdapter {

    companion object {
        private val TAG = HomeFragment::class.qualifiedName
        private val todoList = mutableListOf<Todo>()
        private var capturadas = 2

        private var progr = 0
        private var contadorclick = 0
        private var isPaused = false
        private var isCancelled = false
        private var resumeFromMillis: Long = 0


        lateinit var itemTouchHelper :ItemTouchHelper

        val hora = RxApplication.pref
            .obtenerHoraPreconfirmar()

        val minuto = RxApplication.pref
            .obtenerMinutoPreconfirmar()


    }

    private lateinit var adapter: TodoAdapter
    lateinit var mediaPlayers: MediaPlayer
    lateinit var binding: FragmentHomeBinding


    private val sharedViewModel by activityViewModels<SharedViewModel> {
        HomeViewModelFactory(
            HomeRepositorio(
                RxApplication.applicationContext()
            )
        )
    }



    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentHomeBinding.bind(view)
        hideKeyboard()


        if(RxApplication.pref.obtenerFINALPRECONFIRMACION()) {
            val action = HomeFragmentDirections.actionNavHomeToListaLoteoFragment()
            findNavController().navigate(action)
            sharedViewModel.obtieneDetalleLoteo()

        }


        val millisegundostotales:Long =
            RxApplication.pref.obtenerMillifinishPreconfirmar()

        Logger.d( "Tiempo en Millisegundos Totales $millisegundostotales")
        object : CountDownTimer(15000000, COUNTDOWNTIMER) {

            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {

                val f: NumberFormat = DecimalFormat("00")
                val hour = millisUntilFinished / 3600000 % 24
                val min = millisUntilFinished / 60000 % 60
                val sec = millisUntilFinished / 1000 % 60
                binding.tvHoraPreconf.setText(
                    f.format(hour).toString() + ":" + f.format(min) + ":" + f.format(sec))
            }

            override fun onFinish() {
                binding.tvHoraPreconf.setText("00:00:00")
            }
        }.start()

        binding.miloteo.setOnClickListener {
            val action = HomeFragmentDirections.actionNavHomeToListaLoteoFragment()
                findNavController().navigate(action)
                sharedViewModel.obtieneDetalleLoteo()
        }





        bindingRecyclerview()

        setObservables()

        (activity as MainActivity).getSupportActionBar()?.setTitle(
            resources.getString(R.string.app_name))

        binding.textFolioActual.text =
            RxApplication.pref.
            obtenerFoliodelSurtido()

        binding.tvPorcentaje.text =
            resources.getString(
                R.string.progress, progr
            )

        binding.btnFinalizar.setOnClickListener {

            finalizarSurtido()
        }

        binding.btnGrabarSurtido.setOnClickListener {

            if (sharedViewModel.redInternetOnline(requireActivity())) {

                sharedViewModel.actualizaSurtidos()
                sharedViewModel.obtieneDetalleLoteo()

                binding.btnGrabarSurtido.isEnabled = false
                binding.btnGrabarSurtido.alpha = 0.3F

            } else {

                sharedViewModel.alertainformativa(
                    requireActivity(),
                    resources.getString(R.string.warning_title_internet),
                    resources.getString(R.string.warning_message_internet)
                )
            }

        }

        binding.btnCapturarScanner.setOnClickListener {
            RxApplication.pref.guardarScanner(binding.ediScannerCaptura.text.toString())
            if (
                binding.ediScannerCaptura.text.toString().isNotEmpty() &&
                RxApplication.pref.obtenerScanner().startsWith("M") ||

                binding.ediScannerCaptura.text.toString().isNotEmpty() &&
                RxApplication.pref.obtenerScanner().startsWith("C")||

                binding.ediScannerCaptura.text.toString().isNotEmpty() &&
                RxApplication.pref.obtenerScanner().startsWith("S")
            )



                {

                sharedViewModel.buscarMasterPorLetra(RxApplication.pref.obtenerScanner())

                binding.ediScannerCaptura.text?.clear()

                hideKeyboard()


            }
            if (binding.ediScannerCaptura.text.toString().isNotEmpty()
                && binding.ediScannerCaptura.text.toString().length <=18){

                sharedViewModel.buscasMasterCodigos(RxApplication.pref.obtenerScanner())
                hideKeyboard()
                binding.ediScannerCaptura.text?.clear()

            }

        }


        (activity as MainActivity).onScanListener = OnScanListener { scanner ->
            RxApplication.pref.guardarScanner(scanner)

            if (RxApplication.pref.obtenerScanner() != "" &&
                RxApplication.pref.obtenerScanner().startsWith("M")||
                RxApplication.pref.obtenerScanner().startsWith("C")||
                RxApplication.pref.obtenerScanner().startsWith("S")) {

                    binding.ediScannerCaptura.setText(scanner)

                CoroutineScope(Dispatchers.Main).launch {
                    sharedViewModel.buscasMasterCodigos(RxApplication.pref.obtenerScanner())
                }

                binding.ediScannerCaptura.text?.clear()

            }
        }
    }


    fun openRubros(homeFragment: HomeFragment, todo: Todo){
        val action = HomeFragmentDirections.actionNavHomeToRubrosFragment()
        homeFragment.findNavController().navigate(action)
    }



    private fun setObservables(){

        sharedViewModel.totalesPorFolio.observe(viewLifecycleOwner, { totales ->
            obtenerPorcentaje(totales)
        })

        sharedViewModel.getNormal().observe(viewLifecycleOwner, { normal ->
            sharedViewModel.saveTodo(normal)
        })

        sharedViewModel.invalida.observe(viewLifecycleOwner, { invalida ->
            activity?.runOnUiThread {
                Handler(Looper.getMainLooper()).postDelayed({
                    etiquetaMaster("Etiqueta Inválida", "No existe en el surtido")
                    mediaPlayers =
                        MediaPlayer.create(RxApplication.applicationContext(), R.raw.alerta)
                    mediaPlayers.start()
                }, 0)
            }
        })

        sharedViewModel.getPisoyRack().observe(viewLifecycleOwner,{piso->
            sharedViewModel.saveTodo(piso)
            activity?.runOnUiThread {
                Handler(Looper.getMainLooper()).postDelayed({

                    mediaPlayers = MediaPlayer.create(RxApplication.applicationContext(), R.raw.exhibir)
                    mediaPlayers.start()
                }, 0)
            }
        })

        sharedViewModel.mueblesbodega().observe(viewLifecycleOwner,{bodega->
            sharedViewModel.saveTodo(bodega)

        })


        sharedViewModel.getEtiquetaLeida().observe(viewLifecycleOwner, { leido ->
            if (leido.description == "leido") {
                activity?.runOnUiThread {
                    Handler(Looper.getMainLooper()).postDelayed({
                        etiquetaMaster("Etiqueta Master", "Esta etiqueta master ya fue leída")
                        mediaPlayers =
                            MediaPlayer.create(RxApplication.applicationContext(), R.raw.alerta)
                        mediaPlayers.start()
                    }, 0)
                }
            }

            else if (leido.description == "sobrante") {
                activity?.runOnUiThread {
                    Handler(Looper.getMainLooper()).postDelayed({
                        openRubros(this, leido)
                        mediaPlayers = MediaPlayer.create(RxApplication.applicationContext(), R.raw.sobrante)
                        mediaPlayers.start()
                    }, 0)
                }
            }

        })


        sharedViewModel.obtenerCapturado().observe(viewLifecycleOwner, { todos ->
            capturadas = todos.size
            adapter.setListTodo(todos)
            adapter.notifyDataSetChanged()

            sharedViewModel.obtenerTotales(RxApplication.pref.obtenerFoliodelSurtido().toInt())
        })


        sharedViewModel.getStartActivityIrregularidadMuebles().observe(viewLifecycleOwner, { todo ->
            startActivityIrregularidadesMuebles(todo)
        })

        sharedViewModel.finalizarsurtido.observe(viewLifecycleOwner,{
            RxApplication.pref.guardarFINALPRECONFIRMACION(true)

            activity?.runOnUiThread {
                Handler(Looper.getMainLooper()).postDelayed({
                    grabadoCorrecto(
                        RxApplication.applicationContext(),
                        "Grabado Correctamente", "La preconfirmación ha finalizado"
                    )

                }, 0)
            }
        })
    }

    override fun onResume() {
        val millisegundostotales:Long =
            RxApplication.pref.obtenerMillifinishPreconfirmar()
        Log.d(TAG, "Tiempo en Millisegundos Totales $millisegundostotales")
        object : CountDownTimer(RxApplication.pref.obtenerMillifinishPreconfirmar().toLong(), COUNTDOWNTIMER) {

            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {

                val f: NumberFormat = DecimalFormat("00")
                val hour = millisUntilFinished / 3600000 % 24
                val min = millisUntilFinished / 60000 % 60
                val sec = millisUntilFinished / 1000 % 60
                binding.tvHoraPreconf.setText(
                    f.format(hour).toString() + ":" + f.format(min) + ":" + f.format(sec))
            }

            override fun onFinish() {
                binding.tvHoraPreconf.setText("00:00:00")
            }
        }.start()
        super.onResume()
    }

    private fun finalizarSurtido() {
        activity?.runOnUiThread {
            Handler(Looper.getMainLooper()).postDelayed({
                showfinalizarSurtido(
                    resources.getString(R.string.titulo_finalizar),
                    resources.getString(R.string.msg_finalizar)
                )
            }, 0)
        }
    }

    private fun showfinalizarSurtido(title: String, mensaje: String) {
        val showfinalizar = Dialog(requireActivity())
        showfinalizar.requestWindowFeature(Window.FEATURE_NO_TITLE)
        showfinalizar.setCancelable(false)
        showfinalizar.setContentView(R.layout.alertdialog_decision)
        showfinalizar.window!!.setWindowAnimations(R.style.DialogAnimation)
        val body = showfinalizar.findViewById(R.id.title_warning) as TextView
        val message = showfinalizar.findViewById(R.id.message_warning) as TextView
        body.text = title
        message.text = mensaje
        val btntoAccept = showfinalizar.findViewById(R.id.alert_btn_si) as Button
        val btntoCancelled = showfinalizar.findViewById(R.id.alert_btn_no) as Button

        btntoAccept.setText(R.string.btn_irregularidad_si)
        btntoCancelled.setText(R.string.btn_irregularidad_no)

        btntoCancelled.setOnClickListener {
            showfinalizar.dismiss()
        }

        btntoAccept.setOnClickListener {
            sharedViewModel.obtenerfaltantes().observe(viewLifecycleOwner, { muebles ->

                itemTouchHelper.attachToRecyclerView(null)
                adapter.setList(muebles)
                adapter.notifyDataSetChanged()

                binding.btnGrabarSurtido.visibility = View.VISIBLE
                binding.tvMercanciaPendiente.visibility = View.VISIBLE
                binding.tvMercanciaCapturada.visibility = View.GONE
                binding.btnFinalizar.visibility = View.GONE
            })
            showfinalizar.dismiss()
        }
        showfinalizar.show()
    }


    fun grabadoCorrecto(
        context: Context,
        titlewarning: String,
        messagewarning: String
    ) {
        Log.d(TAG, "Grabado Correcto: Finaliza Preconfirmación ---> $TAG")
        val recepcionAlert = Dialog(requireActivity())
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
        btntoAccept.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {
                Handler(Looper.getMainLooper()).postDelayed({

                    Log.d(
                        TAG,
                        resources.getString(R.string.startActivity_APK2)
                    )
                    RxApplication.pref.guardarFINALPRECONFIRMACION(true)
                    val intent = Intent(Intent.ACTION_MAIN)
                    intent.component =
                        ComponentName(
                            "com.coppel.menu.actualizar",
                            "com.coppel.menu.actualizar.bienvenida.BienvenidaActivity"
                        )
                    startActivity(intent)

                }, 0)
            }

            recepcionAlert.dismiss()
        }
        recepcionAlert.show()
    }



    private fun startActivityIrregularidadesMuebles(todo : Todo){

        val intent =
            Intent(context, IrregularidadActivity::class.java).apply {
                putExtra("holder.descripcion", todo.codigo)
                Log.d("TAG", "position ")
            }
        startActivity(intent)

    }

    override fun irregularidadChecked(position : Int, isChecked : Boolean) {
        sharedViewModel.onClickedIrregularidad(position, isChecked, requireContext())
    }

    private fun Fragment.hideKeyboard() {
        view?.let { activity?.hideKeyboard(it) }
    }

    private fun Context.hideKeyboard(view: View) {
        val inputMethodManager =
            getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
    }

    private fun bindingRecyclerview() {

        adapter = TodoAdapter(requireActivity(), todoList, this)
        binding.rvTodoItems.adapter = adapter
        binding.rvTodoItems.layoutManager = LinearLayoutManager(RxApplication.applicationContext())
        (binding.rvTodoItems.layoutManager as LinearLayoutManager).stackFromEnd = true
        (binding.rvTodoItems.layoutManager as LinearLayoutManager).reverseLayout = true
        binding.tvMercanciaCapturada.visibility = View.VISIBLE
        binding.tvMercanciaPendiente.visibility = View.GONE
          itemTouchHelper = ItemTouchHelper(SwipeDeleteCallback(adapter))
        itemTouchHelper.attachToRecyclerView(binding.rvTodoItems)




    }

    private fun obtenerPorcentaje(valores: List<Int>) {
        if (valores[0] == 0) {
            val resultado = 0
            binding.progressBar.progress = resultado
        } else {
            val resultado = (capturadas - valores[1]) * 100 / valores[0]
            binding.progressBar.progress = resultado
            binding.tvPorcentaje.text = resources.getString(R.string.progress, resultado)
            RxApplication.pref.guardarResultadoProgress(resultado)
        }
    }
    private fun etiquetaMaster(title: String, mensaje: String) {
        val alertFolioError = Dialog(requireActivity())
        alertFolioError.requestWindowFeature(Window.FEATURE_NO_TITLE)
        alertFolioError.setCancelable(false)
        alertFolioError.setContentView(R.layout.alert_dialog)
        alertFolioError.window!!.setWindowAnimations(R.style.DialogAnimation)

        val body = alertFolioError.findViewById(R.id.title_warning) as TextView
        val message = alertFolioError.findViewById(R.id.message_warning) as TextView
        body.text = title
        message.text = mensaje

        val btntoAccept = alertFolioError.findViewById(R.id.btn_to_accept) as Button
        btntoAccept.setText(R.string.btn_aceptar_dialog)

        CoroutineScope(Dispatchers.Main).launch {
            btntoAccept.setOnClickListener {

                alertFolioError.dismiss()

            }
            alertFolioError.show()
        }
    }






}