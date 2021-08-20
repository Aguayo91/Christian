package com.coppel.preconfirmar.preconfirmar

import android.app.Activity
import android.app.Dialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.CheckBox
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.coppel.preconfirmar.R
import com.coppel.preconfirmar.application.RxApplication
import com.coppel.preconfirmar.bd.room.AppDatabase
import com.coppel.preconfirmar.entities.SurtidoMueblesListEntity
import com.coppel.preconfirmar.entities.Todo
import com.coppel.preconfirmar.singleton.OnClickListenerAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TodoAdapter(
    private var mActivity: Activity,
    private var todos: MutableList<Todo>,
    private var callback: OnClickListenerAdapter
) : RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
        return TodoViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_captura, parent, false)
        )
    }

    //fun getList () : List<Todo> = todos

    fun setListTodo(lista : List<Todo>){
        val tmpLista = mutableListOf<Todo>()

        lista.forEach{ todo->
            tmpLista.add(todo)
        }
        todos = tmpLista
    }

    fun deleteItem(itemViewHolder : TodoViewHolder) {
        CoroutineScope(Dispatchers.Main).launch {
            val posicionEliminada = itemViewHolder.absoluteAdapterPosition
            val eliminado = todos[posicionEliminada]
            todos.removeAt(posicionEliminada)
            notifyItemRemoved(posicionEliminada)
            showAlert(posicionEliminada, eliminado)
            AppDatabase.getDatabase(RxApplication.applicationContext())
                .todoDao().deleteitem(eliminado._id)
        }
    }

    private fun showAlert(posicion: Int, eliminado: Todo) {
        val dialogAlert = Dialog(mActivity)
        dialogAlert.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialogAlert.setCancelable(false)
        dialogAlert.setContentView(R.layout.alertdialog_decision)
        dialogAlert.window!!.setWindowAnimations(R.style.DialogAnimation)
        val body = dialogAlert.findViewById(R.id.title_warning) as TextView
        val message = dialogAlert.findViewById(R.id.message_warning) as TextView
        body.text = "¿ Estas seguro de eliminar ?"
        message.text = "Estarías eliminando el código ${eliminado.codigo}"
        val btnToAccept = dialogAlert.findViewById(R.id.alert_btn_si) as Button
        val btnToCancelled = dialogAlert.findViewById(R.id.alert_btn_no) as Button
        btnToAccept.setText(R.string.btn_irregularidad_si)
        btnToCancelled.setText(R.string.btn_irregularidad_no)
        btnToCancelled.setOnClickListener {
            dialogAlert.dismiss()
            undo(posicion, eliminado)
        }
        btnToAccept.setOnClickListener { dialogAlert.dismiss()

        }
        dialogAlert.show()
    }


    private fun undo(posicionEliminada : Int, eliminado : Todo) {
        todos.add(
            posicionEliminada,
            eliminado
        )
        notifyItemInserted(posicionEliminada)
    }

    fun setList( lista :List<SurtidoMueblesListEntity>){
        val Listanueva: MutableList<Todo> = mutableListOf()

        lista.forEach {
            Listanueva.add(Todo
                (0,
                it.sMaster,
                it.sDescripcion,
                false,
                false,
                false,
                false,
                0,
                1,
                0,
                "",
                0)
            )
            todos = Listanueva

        }
    }

    override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
        val curTodo = todos[position]

        holder.codigo.text = curTodo.codigo
        holder.descripcion.text = curTodo.description
        holder.sobrante.isVisible = curTodo.sobrante
        holder.exhibir.isVisible = curTodo.exhibir
        holder.isChecked.isChecked = curTodo.irregularidad

        when(curTodo.rubro){
            2,6,7 -> {
                holder.irregularidad.isVisible = false
                holder.isChecked.isVisible = false
            }
            else -> {
                holder.irregularidad.isVisible = true
                holder.isChecked.isVisible = true
            }
        }

    }

    override fun getItemCount(): Int = todos.size

    inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) , View.OnClickListener{

        val codigo: TextView = itemView.findViewById(R.id.txcodigo)
        val descripcion: TextView = itemView.findViewById(R.id.txdescripcion)
        val irregularidad : TextView = itemView.findViewById(R.id.txirregular)
        val sobrante: TextView = itemView.findViewById(R.id.badgeSobrante)
        val exhibir: TextView = itemView.findViewById(R.id.badgeExhibir)
        val isChecked: CheckBox = itemView.findViewById(R.id.checkboxIrregular)
        val foreGround : RelativeLayout = itemView.findViewById(R.id.foreground)
        val background : RelativeLayout = itemView.findViewById(R.id.background)

        init {
            isChecked.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            when(view?.id){
                R.id.checkboxIrregular -> {
                    callback.irregularidadChecked(absoluteAdapterPosition, isChecked.isChecked)
                    isChecked.isChecked = false
                }
            }
        }

    }
}