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
                    incrementarMuebles()
            sharedViewModel.obtenerdescripcion("M")

            val action = JabaFragmentDirections.actionJabaFragmentToJabaSinEtiquetaFragment(surtidomuebles)
            findNavController().navigate(action)
        }
        udi.setOnClickListener {
            val celulares = "C"+
                    incrementarCelulares()
            sharedViewModel.obtenerdescripcion("C")
            val action = JabaFragmentDirections.actionJabaFragmentToJabaSinEtiquetaFragment(celulares)
            findNavController().navigate(action)
        }
        moto.setOnClickListener {
            val suministros = "S"+
                    incrementarSuministros()
            sharedViewModel.obtenerdescripcion("S")
            val action = JabaFragmentDirections.actionJabaFragmentToJabaSinEtiquetaFragment(suministros)
            findNavController().navigate(action)
        }
        mensajeria.setOnClickListener {
            val ropa = "R"+
                    incrementarRopa()
            val action = JabaFragmentDirections.actionJabaFragmentToJabaSinEtiquetaFragment(ropa)
            findNavController().navigate(action)
        }

        return rootview
    }

    fun incrementarMuebles ():String{
        var actual : Int = RxApplication.pref.obtenerSinEtiquetaMuebles()
        actual++
        val masterMueblesGenerada :String  = actual.toString().padStart(5,'0')
        RxApplication.pref.guardarSinEtiquetaMuebles(masterMueblesGenerada.toInt())
        return  masterMueblesGenerada
    }

    fun incrementarCelulares ():String{
        var actualcelualres : Int = RxApplication.pref.obtenerSinEtiquetaCelulares()
        actualcelualres++
        val masterCelularesGenerada :String  = actualcelualres.toString().padStart(5,'0')
        RxApplication.pref.guardarSinEtiquetaCelulares(masterCelularesGenerada.toInt())
        return  masterCelularesGenerada
    }
    fun incrementarSuministros ():String{
        var actualsuministros : Int = RxApplication.pref.obtenerSinEtiquetaSuministros()
        actualsuministros++
        val masterSuministrosGenerada :String  = actualsuministros.toString().padStart(5,'0')
        RxApplication.pref.guardarSinEtiquetaSuministros(masterSuministrosGenerada.toInt())
        return  masterSuministrosGenerada
    }
    fun incrementarRopa ():String{
        var actualropa : Int = RxApplication.pref.obtenerSinEtiquetaRopa()
        actualropa++
        val masterRopaGenerada :String  = actualropa.toString().padStart(5,'0')
        RxApplication.pref.guardarSinEtiquetaRopa(masterRopaGenerada.toInt())
        return  masterRopaGenerada
    }
}