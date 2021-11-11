package co.edu.eam.mytestapp.activity

import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import co.edu.eam.mytestapp.R
import co.edu.eam.mytestapp.databinding.ActivityMainBinding
import co.edu.eam.mytestapp.model.Estudiante
import co.edu.eam.mytestapp.utils.Constantes
import java.util.*

class MainActivity : AppCompatActivity() {

    val TAG = "ACTIVIDAD1"
    private lateinit var resultLauncher:ActivityResultLauncher<Intent>
    lateinit var binding:ActivityMainBinding
    lateinit var valorTexto:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        valorTexto = if( savedInstanceState != null ){
            savedInstanceState?.getString(Constantes.KEY_NOMBRE, "Mensaje inicial")
        }else{
            "Mensaje inicial"
        }

        binding.textoPrincipal.text = valorTexto

        resultLauncher = registerForActivityResult( ActivityResultContracts.StartActivityForResult() ) {
            onActivityResult(it.resultCode, it)
        }
    }

    fun tomarFoto(v:View){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                resultLauncher.launch(takePictureIntent)
            }
        }
    }

    fun irPantalla2(v:View){
        startActivity(Intent(this, Pantalla2Activity::class.java))
    }

    fun irPantalla3(v:View){
        Log.v(TAG, "Pasar a la pantalla 3")
        val intent = Intent(this, Pantalla3Activity::class.java)

        val estudiante1 = Estudiante("12", Date(), "Pepito", floatArrayOf(3.6F, 4.6F, 2.4F) )

        val estudiante2 = Estudiante( "14", Date(), "Juanita", floatArrayOf(3.6F, 3.6F, 3.4F) )
        estudiante2.amigos.add(estudiante1)

        intent.putExtra(Constantes.KEY_ESTUDIANTE, estudiante2)
        resultLauncher.launch(intent)
    }

    fun irListaEstudiantes(v:View){
        startActivity( Intent(this, ListaActivity::class.java) )
    }

    fun cambiarTexto(v:View){
        valorTexto = "Nuevo texto"
        binding.textoPrincipal.text = valorTexto
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(Constantes.KEY_NOMBRE, valorTexto)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        binding.textoPrincipal.text = savedInstanceState.getString(Constantes.KEY_NOMBRE)
    }

    private fun onActivityResult(resultCode:Int, result:ActivityResult){
        val data = result.data?.extras
        if( resultCode == 12 ){
            Constantes.mostrarMensaje(this, "Mensaje 1: ${data?.getString("RESPUESTA")}")
        }else if( resultCode == 14 ){
            Constantes.mostrarMensaje(this, "Mensaje 2: ${data?.getString("RESPUESTA")}")
        }else{
            if( data?.get("data") is Bitmap ){
                val imageBitmap = data?.get("data") as Bitmap
                binding.imagenMiniatura.setImageBitmap(imageBitmap)
            }
        }
    }

}