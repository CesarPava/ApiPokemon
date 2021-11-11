package co.edu.eam.mytestapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import co.edu.eam.mytestapp.databinding.ActivityPantalla2Binding
import co.edu.eam.mytestapp.utils.Constantes

class Pantalla2Activity : AppCompatActivity(), View.OnClickListener {
    lateinit var binding:ActivityPantalla2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPantalla2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.titulo.text = "Texto modificado desde el activity"

        binding.btnNormal.setOnClickListener(this)
        binding.btnToggle.setOnClickListener(this)
        binding.btnImage.setOnClickListener(this)
        binding.btnSwitch.setOnClickListener(this)
        binding.btnFloating.setOnClickListener(this)
    }

    override fun onBackPressed() {
        val intent = intent
        intent.putExtra("RESPUESTA", "Mensaje enviado desde la pantalla 2")
        setResult(14, intent)
        finish()
        super.onBackPressed()
    }

    override fun onClick(v: View?) {
        when( v?.id ){

            binding.btnNormal.id -> Constantes.mostrarMensaje(this, "Click desde un botón normal")
            binding.btnToggle.id -> {
                if( binding.btnToggle.isChecked ){
                    Constantes.mostrarMensaje(this,"Botón encendido")
                }else{
                    Constantes.mostrarMensaje(this,"Botón apagado")
                }
            }
            binding.btnImage.id -> Constantes.mostrarMensaje(this,"Click desde un ImageButton")
            binding.btnSwitch.id -> {
                if( binding.btnSwitch.isChecked ){
                    Constantes.mostrarMensaje(this,"Botón encendido")
                }else{
                    Constantes.mostrarMensaje(this,"Botón apagado")
                }
            }
            binding.btnFloating.id -> Constantes.mostrarMensaje(this,"Evento desde el botón flotante")

        }
    }
}