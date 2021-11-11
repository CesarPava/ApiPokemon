package co.edu.eam.mytestapp.service

import android.content.Context
import co.edu.eam.mytestapp.db.EstudianteDbHelper
import co.edu.eam.mytestapp.model.Estudiante
import java.text.SimpleDateFormat

object EstudianteData {

    var listaEstudiantes:ArrayList<Estudiante> = ArrayList()
    lateinit var db: EstudianteDbHelper

    fun newInstance(context: Context):EstudianteData{
        db = EstudianteDbHelper(context)
        return this
    }

    fun agregar(estudiante:Estudiante){
        db.guardarEstudiante(estudiante)
        listaEstudiantes.add(0, estudiante)
    }

    fun agregarAmigo(codigoEstudiante: String, codigoAmigo:String){
        db.guardarAmigo(codigoEstudiante, codigoAmigo)
    }

    fun obtenerAmigos(codigo: String):ArrayList<Estudiante>{
        return db.obtenerAmigos(codigo)
    }

    fun eliminar(codigo:String){
        db.eliminarEstudiante(codigo)
        for ( e in listaEstudiantes ){
            if(e.codigo == codigo){
                listaEstudiantes.remove(e)
                break
            }
        }
    }

    fun obtener(codigo:String):Estudiante?{
        return db.obtenerEstudiante(codigo)
    }

    fun modificar(estudiante: Estudiante){
        db.actualizarEstudiante(estudiante)
        for ( (i,e) in listaEstudiantes.withIndex() ){
            if(e.codigo == estudiante.codigo){
                listaEstudiantes[i] = estudiante
                break
            }
        }
    }

    fun listar():ArrayList<Estudiante>{
        listaEstudiantes = db.obtenerLista()
        return listaEstudiantes
    }

}