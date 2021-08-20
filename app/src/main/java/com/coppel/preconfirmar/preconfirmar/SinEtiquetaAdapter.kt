package com.coppel.preconfirmar.preconfirmar

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.coppel.preconfirmar.R
import com.coppel.preconfirmar.databinding.FragmentJabaSinEtiquetaBinding
import com.coppel.preconfirmar.databinding.ItemJabaloteoBinding
import com.coppel.preconfirmar.entities.SinEtiqueta
import com.coppel.preconfirmar.entities.SurtidoMueblesListEntity
import com.coppel.preconfirmar.entities.Todo

class SinEtiquetaAdapter(
    val context: Context,
    val listaSinetiqueta:MutableList<SinEtiqueta>
    ):RecyclerView.Adapter<BaseViewHolder<*>>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):BaseViewHolder<*> {
        return JabaViewHolder(LayoutInflater.from(context).inflate(R.layout.item_jabaloteo,parent,false))

    }
    lateinit var todos: MutableList<SinEtiqueta>



    override fun getItemCount(): Int = listaSinetiqueta.size

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
            when(holder){
                is JabaViewHolder -> holder.bind(listaSinetiqueta[position],position)
                else -> throw IllegalArgumentException("ViewHolder Incorrecto")
            }
    }

    inner class JabaViewHolder(itemView: View) : BaseViewHolder<SinEtiqueta>(itemView) {
            val binding = ItemJabaloteoBinding.bind(itemView)
        override fun bind(item: SinEtiqueta, position: Int) {
            binding.titleCodigo.text = item.codigo
            binding.titleDescripcion.text = item.descripcion
            binding.titleIrregularidad.isVisible = item.irregularidad
            binding.checkboxIrregular.isChecked = item.isChecked
            binding.titleCantidadNumero.text = item.cantidad.toString()
        }
    }

}