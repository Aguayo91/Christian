package com.coppel.preconfirmar.preconfirmar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.coppel.preconfirmar.R
import com.coppel.preconfirmar.application.RxApplication
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class JabaFragment: BottomSheetDialogFragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootview = inflater.inflate(R.layout.fragment_jaba, container, false)

        val muebles = rootview.findViewById<LinearLayout>(R.id.muebles)
        val udi = rootview.findViewById<LinearLayout>(R.id.celulares)
        val moto = rootview.findViewById<LinearLayout>(R.id.suministros)
        val mensajeria = rootview.findViewById<LinearLayout>(R.id.ropa)
        val regresar = rootview.findViewById<ImageView>(R.id.back)

        regresar.setOnClickListener {
            dismiss()
        }



        muebles.setOnClickListener {
            val surtidomuebles = "M"+
                    incrementar()
            sharedViewModel.obtenerdescripcion("M")

            val action = JabaFragmentDirections.actionJabaFragmentToJabaSinEtiquetaFragment(surtidomuebles)
            findNavController().navigate(action)
        }
        udi.setOnClickListener {
            val celulares = "C"+
                    incrementar()
            sharedViewModel.obtenerdescripcion("C")
            val action = JabaFragmentDirections.actionJabaFragmentToJabaSinEtiquetaFragment(celulares)
            findNavController().navigate(action)
        }
        moto.setOnClickListener {
            val suministros = "S"+
                    incrementar()
            sharedViewModel.obtenerdescripcion("S")
            val action = JabaFragmentDirections.actionJabaFragmentToJabaSinEtiquetaFragment(suministros)
            findNavController().navigate(action)
        }
        mensajeria.setOnClickListener {
            val ropa = "R"+
                    incrementar()
            val action = JabaFragmentDirections.actionJabaFragmentToJabaSinEtiquetaFragment(ropa)
            findNavController().navigate(action)
        }

        return rootview
    }

    fun incrementar ():String{
        var actual : Int = RxApplication.pref.getSinEtiqueta()
        actual++
        val masterGenerada :String  = actual.toString().padStart(5,'0')
        RxApplication.pref.storageSinEtiqueta(masterGenerada.toInt())
        return  masterGenerada
    }
}