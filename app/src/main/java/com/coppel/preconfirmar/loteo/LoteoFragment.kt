package com.coppel.preconfirmar.loteo

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import android.os.CountDownTimer
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.cedisropa.scanner.OnScanListener
import com.coppel.framework.ui.view.textviews.TextviewLabel
import com.coppel.preconfirmar.R
import com.coppel.preconfirmar.application.AppConstants
import com.coppel.preconfirmar.application.AppConstants.COUNTDOWNTIMER
import com.coppel.preconfirmar.application.RxApplication
import com.coppel.preconfirmar.databinding.FragmentLoteoBinding
import com.coppel.preconfirmar.entities.Todo
import com.coppel.preconfirmar.preconfirmar.HomeRepositorio
import com.coppel.preconfirmar.preconfirmar.MainActivity
import com.coppel.preconfirmar.singleton.OnClickCheckboxListenerAdapter
import com.coppel.preconfirmar.singleton.OnSolicitaCantidad
import java.text.DecimalFormat
import java.text.NumberFormat

class LoteoFragment : Fragment(), OnSolicitaCantidad, OnClickCheckboxListenerAdapter {

    private var _binding: FragmentLoteoBinding? = null
    private val binding get() = _binding!!
    private lateinit var adapter: LoteoFragmentAdapter


    private val loteoViewModel by activityViewModels<LoteoViewModel> {
        LoteoViewModelFactory(
            HomeRepositorio(
                RxApplication.applicationContext()
            )
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoteoBinding.inflate(inflater, container, false)


        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val args: LoteoFragmentArgs by navArgs()
        val folio = args.foliogenerado.toString()
        binding.txmifolio.text = folio
        binding.txarea.text = "Muebles"

        (activity as MainActivity).getSupportActionBar()?.setTitle(
            resources.getString(R.string.app_loteo))

        //(activity as MainActivity).miDrawerLoteo()


        val millisegundostotales:Long =
            RxApplication.pref.obtenerMilliFinishconfirmar()

        Log.d("Loteo Muebles", "Tiempo en Millisegundos Totales $millisegundostotales")
        object : CountDownTimer(RxApplication.pref.obtenerMillifinishPreconfirmar().toLong(),
            COUNTDOWNTIMER
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

        loteoViewModel.initValues()
        initRecycleView()
        initEvents()
        setObservers()
        getActivity()?.setTitle("Loteo Muebles");

    }

    private fun initEvents() {
        (activity as MainActivity).onScanListener = OnScanListener { scanner ->
            RxApplication.pref.guardarScanner(scanner)

            if (RxApplication.pref.obtenerScanner() != "") {
                binding.editextReference.setText(scanner)
                val codigoCapturado = binding.editextReference.text.toString()
                loteoViewModel.buscaCodigo(codigoCapturado)
                binding.editextReference.text?.clear()
            }
        }

        binding.capturar.setOnClickListener {
            val codigoCapturado = binding.editextReference.text.toString()
            loteoViewModel.buscaCodigo(codigoCapturado)
        }

        binding.btnToEnd.setOnClickListener {
            loteoViewModel.clearValues()
            loteoViewModel.actualizaTodo()
            RxApplication.pref.guardarFINALPRECONFIRMACION(true)
        }
    }

    private fun initRecycleView() {

        adapter = LoteoFragmentAdapter(mutableListOf(), this, this)
        binding.rvTodoItemsLoteo.adapter = adapter
        binding.rvTodoItemsLoteo.layoutManager = LinearLayoutManager(requireContext())
        (binding.rvTodoItemsLoteo.layoutManager as LinearLayoutManager).stackFromEnd = true
        (binding.rvTodoItemsLoteo.layoutManager as LinearLayoutManager).reverseLayout = true
        binding.textMercancia.visibility = View.VISIBLE
        binding.imgMercancia.visibility = View.GONE

    }

    private fun setObservers() {

        loteoViewModel.isJabaCelular().observe(viewLifecycleOwner, { isJabaCelular ->
            if (isJabaCelular) {
                binding.tvCodigo.text = "IMEI"
                binding.editextReference.hint = "Ingresa IMEI"
            } else {
                binding.tvCodigo.text = "Codigo"
                binding.editextReference.hint = "Ingresa codigo"
            }
        })

        loteoViewModel.isMasterNoSurtida().observe(viewLifecycleOwner, { showNosurtida ->
            if (showNosurtida) {
                showMessageMasterNoSurtida()
                binding.editextReference.setText("")
                binding.master.text = "Master Actual: "
            }

        })

        loteoViewModel.isMasterNoPreconfirmada()
            .observe(viewLifecycleOwner, { showNoPreconfirmada ->
                if (showNoPreconfirmada) {
                    showMessageNoPreconfirmada()
                    binding.editextReference.setText("")
                    binding.master.text = "Master Actual: "
                }

            })

        loteoViewModel.isMasterCapturada().observe(viewLifecycleOwner, { showCapturada ->
            if (showCapturada) {
                showMessageMasterConfirmada()
                binding.editextReference.setText("")
                binding.master.text = "Master Actual: "
            }

        })

        loteoViewModel.getListaLoteado().observe(viewLifecycleOwner, { loteado ->
            binding.editextReference.setText("")
            adapter.setLoteado(loteado)
            adapter.notifyDataSetChanged()
        })

        loteoViewModel.isShowIrregularidad().observe(viewLifecycleOwner, {


        })

        loteoViewModel.getMasterAlotear().observe(viewLifecycleOwner, { sMaster ->
            if (!sMaster.isNullOrEmpty()) {
                binding.master.text = "Master Actual: " + sMaster
                binding.editextReference.setText("")
                adapter.setLoteado(mutableListOf())
            } else {
                binding.master.text = "Master Actual: "
            }
        })

        loteoViewModel.getTotalesByMaster().observe(viewLifecycleOwner, {
            binding.txPercent.text = "${adapter.itemCount}/${it}"
            if (it > 0) {
                binding.progressBar.progress = ((adapter.itemCount * 100) / it)
            } else {
                binding.progressBar.progress = 0
            }

        })

        loteoViewModel.isShowIrregularidadCelulares().observe(viewLifecycleOwner, { showCelulares ->
            if (showCelulares) {
                Toast.makeText(
                    context,
                    "Se levanto irregularidad para celulares",
                    Toast.LENGTH_LONG
                ).show()
            }
        })

        loteoViewModel.isShowIrregularidadMuebles().observe(viewLifecycleOwner, { showMuebles ->
            if (showMuebles) {
                Toast.makeText(context, "Se levanto irregularidad para muebles", Toast.LENGTH_LONG)
                    .show()
            }
        })

        loteoViewModel.isShowIrregularidadSuministros()
            .observe(viewLifecycleOwner, { showSuministros ->
                if (showSuministros) {
                    Toast.makeText(
                        context,
                        "Se levanto irregularidad para suministros",
                        Toast.LENGTH_LONG
                    ).show()
                }

            })

    }

    private fun showMessageMasterNoSurtida() {
        Toast.makeText(context, "Jaba no pertence al surtido", Toast.LENGTH_LONG).show()
    }

    private fun showMessageMasterConfirmada() {
        Toast.makeText(context, "Ya se loteo esta Jaba", Toast.LENGTH_LONG).show()
    }

    private fun showMessageNoPreconfirmada() {
        Toast.makeText(context, "Jaba no preconfirmada", Toast.LENGTH_LONG).show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun solicitaCantidad(todo: Todo) {
        val showCantidad = Dialog(requireActivity())
        showCantidad.requestWindowFeature(Window.FEATURE_NO_TITLE)
        showCantidad.setCancelable(false)
        showCantidad.setContentView(R.layout.alertdialog_cantidade)

        val titulo = showCantidad.findViewById<TextviewLabel>(R.id.title_cantidad)
        val btnContinuar = showCantidad.findViewById<Button>(R.id.btn_acepta_cantidad)
        val editTextCantidad = showCantidad.findViewById<EditText>(R.id.et_cantidad)

        titulo.setText(getString(R.string.ingresa_cantidad_recibida))
        btnContinuar.setOnClickListener {

            try {
                val captura =   editTextCantidad.text.toString()
                todo.capturado = captura.toInt()
                loteoViewModel.actualizaCantidad(todo)
                showCantidad.dismiss()
            } catch (numberException: NumberFormatException) {
                Toast.makeText(context, "Ingresa valor numerico", Toast.LENGTH_LONG)
            }
        }
        showCantidad.show()
    }

    override fun irregularidadChecked(position: Int) {

        loteoViewModel.clickIrregularidad(adapter.getTodo(position))

    }
}