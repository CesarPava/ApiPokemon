package co.edu.eam.mytestapp.activity

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import co.edu.eam.mytestapp.R
import co.edu.eam.mytestapp.databinding.ActivityListaBinding
import co.edu.eam.mytestapp.fragment.DetalleFragment
import co.edu.eam.mytestapp.fragment.ListaEstudiantesFragment
import co.edu.eam.mytestapp.service.EstudianteData
import co.edu.eam.mytestapp.utils.Idioma
import com.google.android.material.snackbar.Snackbar

class ListaActivity : AppCompatActivity(), ListaEstudiantesFragment.OnEstudianteSeleccionadoListener {

    lateinit var binding:ActivityListaBinding
    var detalleEstudiante:Fragment? = null

    override fun attachBaseContext(newBase: Context?) {
        val nuevoContexto = Idioma.cambiarIdioma(newBase!!)
        super.attachBaseContext(nuevoContexto)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        EstudianteData.newInstance(this)

        binding = ActivityListaBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar!!.title = getString(R.string.app_name)

        supportFragmentManager.beginTransaction()
            .replace(R.id.lista_estu_fragment, ListaEstudiantesFragment.newInstance("-1"))
            .commit()

        detalleEstudiante = supportFragmentManager.findFragmentById(R.id.detalle_estu_fragment)

    }

    override fun onEstudianteSeleccionado(codigo: String) {
        if(detalleEstudiante!=null){
            (detalleEstudiante as DetalleFragment).cargarInformacion(codigo)
        }else{
            var i = Intent(this, DetalleActivity::class.java)
            i.putExtra("item_e", codigo)
            startActivity(i)
        }
    }


}