package co.edu.eam.mytestapp.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import co.edu.eam.mytestapp.R
import co.edu.eam.mytestapp.activity.DetalleActivity
import co.edu.eam.mytestapp.model.Estudiante
import java.lang.Exception

class EstudianteAdapter(var lista:ArrayList<Estudiante>): RecyclerView.Adapter<EstudianteAdapter.ViewHolder>(), Filterable {

    var copia:ArrayList<Estudiante> = lista
    lateinit var listener:OnClickEstudianteAdapter

    constructor(lista:ArrayList<Estudiante>, fragment: Fragment):this(lista){
        if(fragment is OnClickEstudianteAdapter){
            this.listener = fragment
        }else{
            throw Exception("El fragmento no ha implementado el interface OnClickEstudianteAdapter")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_estudiante, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindEstudiante( copia[position] )
    }

    override fun getItemCount(): Int {
        return copia.size
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val valor = constraint.toString()
                if (valor.isEmpty()) {
                    copia = lista
                } else {
                    copia = ArrayList()
                    val resultados = lista.filter { estudiante ->
                        estudiante.nombre!!.lowercase().contains(valor.lowercase())
                    }
                    copia.addAll(resultados)
                }
                return FilterResults().also {
                    it.values = copia
                }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                copia = results?.values as ArrayList<Estudiante>
                notifyDataSetChanged()
            }
        }
    }

    inner class ViewHolder(itemView:View):RecyclerView.ViewHolder(itemView), View.OnClickListener{

        var nombre:TextView = itemView.findViewById(R.id.nombre_estudiante)
        var codigo:TextView = itemView.findViewById(R.id.codigo_estudiante)
        var fecha:TextView = itemView.findViewById(R.id.fecha_estudiante)
        var codigoEstudiante:String? = null

        init{
            itemView.setOnClickListener(this)
        }

        fun bindEstudiante(estudiante: Estudiante){
            this.codigoEstudiante = estudiante.codigo
            nombre.text = estudiante.nombre
            codigo.text = estudiante.codigo
            fecha.text = estudiante.fechaNacimiento.toString()
        }

        override fun onClick(v: View?) {
            listener.onClickEstudiante(codigoEstudiante!!)
        }

    }

    interface OnClickEstudianteAdapter{
        fun onClickEstudiante(codigo:String)
    }

}