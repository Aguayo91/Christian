package com.coppel.preconfirmar.preconfirmar

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.lifecycle.lifecycleScope
import com.coppel.preconfirmar.R
import com.coppel.preconfirmar.bd.room.AppDatabase
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.orhanobut.logger.Logger
import kotlinx.coroutines.launch

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
            borrarBD()
            dismiss()
        }

        btncancelar.setOnClickListener {
            dismiss()
        }

        return rootview
    }

    fun borrarBD(){

        lifecycleScope.launch {
            AppDatabase.getDatabase(requireActivity()).mueblesDao().deleteAll()
            AppDatabase.getDatabase(requireActivity()).mensajeriaDao().deleteAll()
            AppDatabase.getDatabase(requireActivity()).ropaDao().deleteAll()
            AppDatabase.getDatabase(requireActivity()).ventasxrDao().deleteAll()
            AppDatabase.getDatabase(requireActivity()).suministrosDao().deleteAll()
            AppDatabase.getDatabase(requireActivity()).iniciarSurtidoDao().deleteAll()
            AppDatabase.getDatabase(requireActivity()).consultarkeyXDao().deleteAll()
            AppDatabase.getDatabase(requireActivity()).consultarDetalleMasterDao().deleteAll()
            AppDatabase.getDatabase(requireActivity()).faltantesDao().deleteAll()
        }
            Logger.d(
                    resources.getString(R.string.startActivity_APK1)
                )

                val intent = Intent(Intent.ACTION_MAIN)
                intent.component =
                    ComponentName(
                        "com.coppel.menu.actualizar",
                        "com.coppel.menu.actualizar.bienvenida.BienvenidaActivity"
                    )
                startActivity(intent)


    }
}