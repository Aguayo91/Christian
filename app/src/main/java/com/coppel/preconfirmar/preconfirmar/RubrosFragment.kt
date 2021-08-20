package com.coppel.preconfirmar.preconfirmar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.fragment.app.activityViewModels
import com.coppel.preconfirmar.R
import com.coppel.preconfirmar.application.RxApplication
import com.coppel.preconfirmar.entities.Todo
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class RubrosFragment : BottomSheetDialogFragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootview = inflater.inflate(R.layout.fragment_rubros, container, false)
        val codmuebles = rootview.findViewById<LinearLayout>(R.id.layout_muebles)
        val codudi = rootview.findViewById<LinearLayout>(R.id.layout_udi)
        val codmoto = rootview.findViewById<LinearLayout>(R.id.layout_moto)
        val regresar = rootview.findViewById<ImageView>(R.id.back)

        regresar.setOnClickListener {
            dismiss()
        }

        codmuebles.setOnClickListener {
            val itemcapturado = Todo(
                0,
                RxApplication.pref.obtenerScanner(),
                "Código Muebles",
                true,
                false,
                false,
                false,
                5,
                1,
                0,
                "",
                0

            )
            sharedViewModel.saveTodo(itemcapturado)
            //findNavController().navigate(R.id.action_nav_rubros_to_nav_home)
            dismiss()
        }
        codudi.setOnClickListener {
            val itemcapturado = Todo(
                0,
                RxApplication.pref.obtenerScanner(),
                "Udi Muebles ",
                true,
                false,
                false,
                false,
                4,
                1,
                0,
                "",
                0

            )
            sharedViewModel.saveTodo(itemcapturado)
            //findNavController().navigate(R.id.action_nav_rubros_to_nav_home)
            dismiss()
        }
        codmoto.setOnClickListener {
            val itemcapturado = Todo(
                0,
                RxApplication.pref.obtenerScanner(),
                "Código Motocicleta",
                true,
                false,
                false,
                false,
                6,
                1,
                0,
                "",
                0
            )
            sharedViewModel.saveTodo(itemcapturado)
            //findNavController().navigate(R.id.action_nav_rubros_to_nav_home)
            dismiss()
        }


        return rootview
    }
}