package com.coppel.preconfirmar.preconfirmar

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.coppel.preconfirmar.databinding.ActivityIrregularidadJabaBinding

class IrregularidadJabaActivity : AppCompatActivity() {

    companion object {
        private val TAG =
            IrregularidadJabaActivity::class.qualifiedName
    }
    private lateinit var binding:ActivityIrregularidadJabaBinding



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityIrregularidadJabaBinding.inflate(layoutInflater)
        setContentView(binding.root)


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
            binding.tituloRadiojaba.visibility = View.VISIBLE

        }

        binding.botonAceptarJaba.setOnClickListener {

            finish()
        }


    }
}