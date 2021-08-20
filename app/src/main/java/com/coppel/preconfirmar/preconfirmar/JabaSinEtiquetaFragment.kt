package com.coppel.preconfirmar.preconfirmar

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.cedisropa.scanner.OnScanListener
import com.coppel.preconfirmar.R
import com.coppel.preconfirmar.application.AppConstants
import com.coppel.preconfirmar.application.RxApplication
import com.coppel.preconfirmar.databinding.FragmentJabaSinEtiquetaBinding
import com.coppel.preconfirmar.entities.SinEtiqueta
import com.coppel.preconfirmar.loteo.LoteoViewModel
import com.coppel.preconfirmar.loteo.LoteoViewModelFactory
import com.orhanobut.logger.Logger
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.DecimalFormat
import java.text.NumberFormat

class JabaSinEtiquetaFragment : Fragment(R.layout.fragment_jaba_sin_etiqueta) {

    private lateinit var adapter: SinEtiquetaAdapter
    private val jabalist = mutableListOf<SinEtiqueta>()
    lateinit var binding:FragmentJabaSinEtiquetaBinding
    private var capturadoloteo = 2
    val args: JabaSinEtiquetaFragmentArgs by navArgs()

    private val LoteoViewModel by activityViewModels<LoteoViewModel> {
        LoteoViewModelFactory(
            HomeRepositorio(
                RxApplication.applicationContext()
            )
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentJabaSinEtiquetaBinding.bind(view)

        val amount = args.mastergenerada
        binding.masterGenerada.text = amount
        Logger.d( "El resultado obtenido es: $amount")
        val s = binding.editextLoteo.text.toString()

        val millisegundostotales:Long =
            RxApplication.pref.obtenerMilliFinishconfirmar()

        Log.d("Loteo Muebles", "Tiempo en Millisegundos Totales $millisegundostotales")
        object : CountDownTimer(
            RxApplication.pref.obtenerMillifinishPreconfirmar().toLong(),
            AppConstants.COUNTDOWNTIMER
        ) {

            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {

                val f: NumberFormat = DecimalFormat("00")
                val hour = millisUntilFinished / 3600000 % 24
                val min = millisUntilFinished / 60000 % 60
                val sec = millisUntilFinished / 1000 % 60
                binding.tiempoRecepcion.setText(
                    f.format(hour).toString() + ":" + f.format(min) + ":" + f.format(sec))
            }

            override fun onFinish() {
                binding.tiempoRecepcion.setText("00:00:00")
            }
        }.start()


        adapter = SinEtiquetaAdapter(requireActivity(), jabalist)
        binding.rvJabaItems.adapter = adapter

        binding.rvJabaItems.layoutManager = LinearLayoutManager(RxApplication.applicationContext())
        (binding.rvJabaItems.layoutManager as LinearLayoutManager).stackFromEnd = true
        (binding.rvJabaItems.layoutManager as LinearLayoutManager).reverseLayout = true
        (activity as MainActivity).getSupportActionBar()?.setTitle(
            resources.getString(R.string.jaba_etiqueta))


        (activity as MainActivity).onScanListener = OnScanListener { s  ->
            RxApplication.pref.guardarScanner(s)

        }

        binding.btnSalirLoteo.setOnClickListener {
            val action = JabaSinEtiquetaFragmentDirections.actionJabaSinEtiquetaFragmentToNavHome()
            findNavController().navigate(action)
        }

        binding.capturarLoteo.setOnClickListener {

            LoteoViewModel.saveTodo(s,amount)
            adapter.notifyDataSetChanged()
        }



        binding.btnGrabarLoteo.setOnClickListener {
            val action = JabaSinEtiquetaFragmentDirections.actionJabaSinEtiquetaFragmentToNavHome()
            findNavController().navigate(action)
            CoroutineScope(Dispatchers.Main).launch {
                LoteoViewModel.saveTodo(RxApplication.pref.obtenerScanner(),amount)
            }
        }


        binding.folio.text = RxApplication.pref.obtenerFoliodelSurtido()

    }

}