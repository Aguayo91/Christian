package com.coppel.preconfirmar.singleton

import com.coppel.preconfirmar.entities.Todo

interface OnSolicitaCantidad {

    fun solicitaCantidad(todo : Todo)
}