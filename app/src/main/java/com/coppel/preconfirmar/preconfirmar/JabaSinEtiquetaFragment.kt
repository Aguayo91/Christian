package com.coppel.preconfirmar.preconfirmar

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.coppel.preconfirmar.R

class JabaSinEtiquetaFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        (activity as MainActivity).getSupportActionBar()?.setTitle("Jaba sin etiqueta")
        val rootview = inflater.inflate(R.layout.fragment_jaba_sin_etiqueta, container, false)

        return rootview
    }

}