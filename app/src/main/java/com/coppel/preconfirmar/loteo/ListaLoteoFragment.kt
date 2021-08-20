package com.coppel.preconfirmar.loteo

import android.app.Dialog
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.navigation.fragment.findNavController
import com.coppel.preconfirmar.R
import com.coppel.preconfirmar.application.RxApplication
import com.coppel.preconfirmar.databinding.FragmentListaLoteoBinding
import com.coppel.preconfirmar.preconfirmar.HomeFragment
import com.coppel.preconfirmar.preconfirmar.HomeFragmentDirections
import com.orhanobut.logger.Logger


class ListaLoteoFragment : Fragment(R.layout.fragment_lista_loteo) {

    companion object {
        private val TAG = ListaLoteoFragment::class.qualifiedName
    }

    lateinit var binding: FragmentListaLoteoBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentListaLoteoBinding.bind(view)

        binding.mifolio.text = RxApplication.pref
            .obtenerFoliodelSurtido()

        binding.miseleccion.text = RxApplication.pref.
        obtenerTipoSeleccionado().toString()

         binding.milayoutSeleccion.setOnClickListener {
             finalizarSurtido()

             Logger.d("el Tipo Seleccionado es " +
                     RxApplication.pref.obtenerTipoSeleccionado().toString()
             )

             Logger.d("el Folio  es " +
                     RxApplication.pref.obtenerFoliodelSurtido()
             )
         }

    }

    private fun finalizarSurtido() {
        activity?.runOnUiThread {
            Handler(Looper.getMainLooper()).postDelayed({
                showIniciarLoteo(
                    resources.getString(R.string.titulo_iniciarFolio),
                    resources.getString(R.string.msg_iniciarFolio,RxApplication.pref
                        .obtenerFoliodelSurtido())
                )
            }, 0)
        }
    }

    private fun showIniciarLoteo(title: String, mensaje: String) {
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
            val action = ListaLoteoFragmentDirections.actionListaLoteoFragmentToLoteoFragment()
            findNavController().navigate(action)
            showfinalizar.dismiss()
        }
        showfinalizar.show()
    }


}