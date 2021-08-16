package com.coppel.preconfirmar.loteo

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.LinearLayout
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.coppel.preconfirmar.R
import com.coppel.preconfirmar.entities.Todo
import com.coppel.preconfirmar.singleton.OnClickCheckboxListenerAdapter
import com.coppel.preconfirmar.singleton.OnSolicitaCantidad

class LoteoFragmentAdapter (private var  loteado: List<Todo>,
                            private val onSolicitaCantidad: OnSolicitaCantidad,
                            private val onCLickIrregularidad : OnClickCheckboxListenerAdapter
) : RecyclerView.Adapter<LoteoFragmentAdapter.LoteoViewHolder>() {

    inner class LoteoViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){

        val codigo: TextView = itemView.findViewById(R.id.txcodigo)
        val descripcion: TextView = itemView.findViewById(R.id.txdescripcion)
        val recibiSurtido : TextView = itemView.findViewById(R.id.tv_recibi_surtido)
        val sobrante: TextView = itemView.findViewById(R.id.badgeSobrante)
        val exhibir: TextView = itemView.findViewById(R.id.badgeExhibir)
        val checkBoxIrrehularidad: CheckBox = itemView.findViewById(R.id.checkboxIrregularLoteo)
        val foreGround : RelativeLayout = itemView.findViewById(R.id.foreground)
        val background : RelativeLayout = itemView.findViewById(R.id.background)
        val linearImei : LinearLayout = itemView.findViewById(R.id.ll_ime)
        val imei : TextView = itemView.findViewById(R.id.tvValorImei)
        val linearTercero : LinearLayout = itemView.findViewById(R.id.ll_tercer_linear)

        fun bind(todo : Todo){

            codigo.text = todo.codigo
            descripcion.text = todo.description
            sobrante.isVisible = todo.sobrante
            exhibir.isVisible = todo.exhibir
            recibiSurtido.text = itemView.context.resources.getString(R.string.recibido_surtido,todo.capturado,todo.recibido)
            checkBoxIrrehularidad.isChecked = todo.isChecked

            if (todo.imei != ""){
                imei.text = todo.imei
                linearImei.visibility = View.VISIBLE
                linearTercero.visibility = View.GONE
            }else{
                imei.text = ""
                linearImei.visibility = View.GONE
                linearTercero.visibility = View.VISIBLE
            }

            if(todo.recibido > 1 && todo.capturado  == 0 && !todo.Master.startsWith("C") ){
                onSolicitaCantidad.solicitaCantidad(todo)
            }

            checkBoxIrrehularidad.setOnClickListener{
                checkBoxIrrehularidad.isChecked = false
                onCLickIrregularidad.irregularidadChecked(absoluteAdapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): LoteoViewHolder {
        return  LoteoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_captura_loteo, parent, false)
        )
    }

    fun setLoteado(loteado : List<Todo>){
        this.loteado = loteado
    }

    override fun getItemCount(): Int {
        return loteado.size
    }

    override fun onBindViewHolder(holder: LoteoFragmentAdapter.LoteoViewHolder, position: Int) {
        holder.bind(loteado[position])
    }


    fun getTodo(position : Int) : Todo = loteado[0]
}