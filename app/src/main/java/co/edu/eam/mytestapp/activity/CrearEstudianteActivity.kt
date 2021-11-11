package co.edu.eam.mytestapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.core.view.children
import co.edu.eam.mytestapp.R
import co.edu.eam.mytestapp.databinding.ActivityCrearEstudianteBinding
import co.edu.eam.mytestapp.model.Estudiante
import co.edu.eam.mytestapp.service.EstudianteData
import java.util.*

class CrearEstudianteActivity : AppCompatActivity() {

    var estudiante:Estudiante? = null
    var posicion:Int = -1
    lateinit var binding:ActivityCrearEstudianteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCrearEstudianteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        posicion = intent.getIntExtra("posicion", -1)

        if(posicion!=-1){
            estudiante = intent.extras!!.getParcelable("estudiante")
            binding.btnFormulario.text = "Modificar estudiante"
        }

    }

    fun agregarCajonTexto(v:View){
        val editText:EditText = EditText(this)
        editText.width = 200
        editText.height = 40
        binding.listaNotas.addView( editText )
    }

    fun crearEstudiante(v:View){
        if(estudiante==null) {

            /*for( i in 0 .. binding.listaNotas.childCount-1){
                Log.e("VALOR", (binding.listaNotas.getChildAt(i) as EditText).text.toString())
            }*/

            EstudianteData.agregar(Estudiante("232", Date(), "Luz", floatArrayOf(3.4F, 3.2F, 4.1F)))
            intent.putExtra("RESPUESTA", "El estudiante se creó")
            setResult(14, intent)
        }else{
            estudiante!!.nombre = "Manuelita"
            EstudianteData.modificar( estudiante!! )
            intent.putExtra("RESPUESTA", "El estudiante se modificó")
            intent.putExtra("pos", posicion)
            setResult(15, intent)
        }
        finish()
    }

    override fun onBackPressed() {
        if(estudiante!=null) {
            intent.putExtra("pos", posicion)
            setResult(15, intent)
        }
        finish()
    }
}