package com.coppel.preconfirmar.application

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.util.Log
import com.coppel.preconfirmar.R
import com.orhanobut.logger.AndroidLogAdapter
import com.orhanobut.logger.Logger

class RxApplication: Application() {

    init {
        instance = this
    }

    companion object {
        private val TAG = RxApplication::class.qualifiedName

        private var instance: RxApplication? = null

        fun applicationContext(): Context {
            return instance!!.applicationContext
        }

        @SuppressLint("StaticFieldLeak")
        lateinit var pref: Pref
    }

    override fun onCreate() {
        super.onCreate()
        Log.d(
            TAG,
            resources.getString(
                R.string.application_inicio
            )
        )
        Logger.d(resources.getString(
            R.string.application_inicio
        ))
        Logger.addLogAdapter(AndroidLogAdapter())
        pref = Pref(applicationContext)

    }
}