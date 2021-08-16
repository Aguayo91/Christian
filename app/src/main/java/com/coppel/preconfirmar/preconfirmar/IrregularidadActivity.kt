package com.coppel.preconfirmar.preconfirmar

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.coppel.preconfirmar.R
import com.coppel.preconfirmar.bd.room.AppDatabase
import com.coppel.preconfirmar.databinding.ActivityIrregularidadBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IrregularidadActivity  : AppCompatActivity() {


    companion object {
        private val TAG =
            IrregularidadActivity::class.qualifiedName
    }


    private lateinit var binding: ActivityIrregularidadBinding
    private val irregularidadViewModel: IrregularidadViewModel by viewModels()

    lateinit var folioacta: String
    lateinit var nounidad: String
    lateinit var noempleado: String
    lateinit var nogerente: String
    lateinit var noauditor: String


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIrregularidadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        checkboxChecked()
        val message = intent.getStringExtra("holder.descripcion")
        binding.titleSobrante.text = resources.getString(R.string.title_irregularidad, message)

        CoroutineScope(Dispatchers.Main).launch {
            val foranea = AppDatabase.getDatabase(this@IrregularidadActivity)
                .iniciarSurtidoDao().getTiendaForanea()

            if (foranea == 1) {
                binding.radiobuttonActaForanea.visibility = View.GONE
            } else {
                binding.radiobuttonActaForanea.visibility = View.VISIBLE
            }

            binding.startIrregularidad.visibility = View.VISIBLE



            binding.myToolbar.setOnClickListener {
                finish()
            }

            binding.btnNo.setOnClickListener {

                val intent : Intent = Intent().apply {
                    putExtra("irregularidad",false)
                }

                setResult(RESULT_OK,intent)
                finish()
            }


            binding.btnSi.setOnClickListener {

                binding.startIrregularidad.visibility = View.GONE

                binding.tituloRadioGrupoCargo.visibility = View.VISIBLE

            }

            binding.btnAceptarCargo.setOnClickListener {

                finish()
            }


        }

        binding.radioGroupIrregularidad.setOnCheckedChangeListener { _, checkedId ->

            if (checkedId == R.id.radiobutton_cargo) {

                binding.subtituloRadioGrupoCargo.visibility = View.VISIBLE
                binding.buttonMaster.visibility = View.VISIBLE

                binding.actaIrregularidad.visibility = View.GONE
                binding.scrollViewDataActa.visibility = View.GONE

            } else if (checkedId == R.id.radiobutton_acta_foranea) {

                binding.actaIrregularidad.visibility = View.VISIBLE
                binding.scrollViewDataActa.visibility = View.VISIBLE

                binding.subtituloRadioGrupoCargo.visibility = View.GONE
                binding.buttonMaster.visibility = View.GONE


                binding.btnContinue.setOnClickListener {
                    validarCamposDatos()
                }

                binding.btnSalir.setOnClickListener {
                    finish()
                }
            }
        }


    }

    private fun validarCamposDatos() {
        if (validarEmpleadoChofer()) {
            return

        }
        if (validarEmpleadoGerente()){
            return

        }
        if (validarEmpleadoAuditor()){
            return

        }
        if (unidadActa()){
            return
        }
        if (foliotitulodelActa()){
            return
        }
        else {

            finish()
        }
    }

    private fun validarEmpleadoChofer():Boolean{
        val empleadoChofer = binding.editextChoferEmpleado.text.toString()
        if (irregularidadViewModel.validarChofer(empleadoChofer)){
            return false
        }else{
            irregularidadViewModel.alertInformativa(
                this@IrregularidadActivity,
                resources.getString(R.string.title_error_driver_employee),
                resources.getString(R.string.message_error_driver_employee)
            )
        }
        return true
    }

    private fun validarEmpleadoGerente(): Boolean {
        val empleadoGerente = binding.editextChoferEmpleado.text.toString()

        if (irregularidadViewModel.validarChofer(empleadoGerente)||
            binding.editextFolio.text.toString().isEmpty()) {
            return false

        } else {

            irregularidadViewModel.alertInformativa(
                this@IrregularidadActivity,
                resources.getString(R.string.title_empty_gte),
                resources.getString(R.string.message_empty_gte)
            )

            binding.editextFolio.requestFocus()
        }
        return true
    }

    private fun validarEmpleadoAuditor():Boolean{
        val empleadoAuditor = binding.editextAuditor.text.toString()
        if (irregularidadViewModel.validarChofer(empleadoAuditor)){
            return false
        }else{
            irregularidadViewModel.alertInformativa(
                this@IrregularidadActivity,
                resources.getString(R.string.title_empty_auditor),
                resources.getString(R.string.message_empty_auditor)
            )
        }
        return true
    }

    private fun unidadActa():Boolean{
        val numeroUnidad = binding.editextUnidad.text.toString()
        if (irregularidadViewModel.validarUnidadActa(numeroUnidad)){
            return false
        }else{
            irregularidadViewModel.alertInformativa(
                this@IrregularidadActivity,
                resources.getString(R.string.title_empty_unidad),
                resources.getString(R.string.message_empty_unidad)
            )
        }
        return true
    }

    private fun foliotitulodelActa():Boolean{
        val foliodelActa = binding.editextFolio.text.toString()
        if(irregularidadViewModel.validarUnidadActa(foliodelActa)){
            return false
        }else{
            irregularidadViewModel.alertInformativa(
                this@IrregularidadActivity,
                resources.getString(R.string.title_empty_folioacta),
                resources.getString(R.string.message_empty_folioacta)
            )
        }
        return true
    }

    fun checkboxChecked() {
        binding.selloviolado.setOnCheckedChangeListener { _, isChecked ->

            val sello = "Sello"
            Log.d(TAG,"elegido es:$sello")
        }

        binding.cajaReten.setOnCheckedChangeListener { _, isChecked ->
            val caja = "Caja"
            Log.d(TAG,"elegido es:$caja")
        }

        binding.BitacoraSinfirmas.setOnCheckedChangeListener { _, isChecked ->
            val Bitacora = "Bitacora"
            Log.d(TAG,"elegido es:$Bitacora")
        }

        binding.laminasSueltas.setOnCheckedChangeListener { _, isChecked ->
            val Laminas = "Laminas"
            Log.d(TAG,"elegido es:$Laminas")
        }

    }
}