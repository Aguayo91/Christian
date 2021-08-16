package com.coppel.preconfirmar.preconfirmar

import android.app.Dialog
import android.content.Context
import android.view.Window
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModel
import com.coppel.preconfirmar.R
import com.coppel.preconfirmar.application.AppConstants.KT8
import com.coppel.preconfirmar.application.AppConstants.KT_H
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class IrregularidadViewModel : ViewModel() {



    fun validarChofer(valor: String?): Boolean {

        try {
            if (valor!!.length== KT_H
                && valor != KT8) {
                return true
            }
        } catch (e: Exception) {
            return false
        }
        return false
    }



    fun validarUnidadActa(valor: String?):Boolean{

        try {
            if (valor!!.isNotEmpty()) {
                return true
            }
        } catch (e: Exception) {
            return false
        }
        return false
    }

    fun alertInformativa(context: Context, title: String, mensaje: String) {
        val alertFolioError = Dialog(context)
        alertFolioError.requestWindowFeature(Window.FEATURE_NO_TITLE)
        alertFolioError.setCancelable(false)
        alertFolioError.setContentView(R.layout.alert_dialog)
        alertFolioError.window!!.setWindowAnimations(R.style.DialogAnimation)
        val body = alertFolioError.findViewById(R.id.title_warning) as TextView
        val message = alertFolioError.findViewById(R.id.message_warning) as TextView
        body.text = title
        message.text = mensaje

        val btntoAccept = alertFolioError.findViewById(R.id.btn_to_accept) as Button
        btntoAccept.setText(R.string.btn_aceptar_dialog)

        CoroutineScope(Dispatchers.Main).launch {
            btntoAccept.setOnClickListener {

                alertFolioError.dismiss()

            }
            alertFolioError.show()
        }
    }
}