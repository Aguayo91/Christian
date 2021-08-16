package com.coppel.preconfirmar.preconfirmar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.coppel.preconfirmar.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class SalirFragment : BottomSheetDialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootview =  inflater.inflate(R.layout.fragment_salir, container, false)

        val btnsalir = rootview.findViewById<Button>(R.id.btn_sisalir)
        val btncancelar = rootview.findViewById<Button>(R.id.btn_nosalir)

        btnsalir.setOnClickListener {
            dismiss()

        }
        btncancelar.setOnClickListener {
            dismiss()
        }

        return rootview
    }
}