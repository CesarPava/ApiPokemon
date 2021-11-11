package co.edu.eam.mytestapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.edu.eam.mytestapp.databinding.ActivityDetalleBinding
import co.edu.eam.mytestapp.fragment.InformacionEstudianteFragment
import co.edu.eam.mytestapp.service.EstudianteData
import co.edu.eam.mytestapp.R
import co.edu.eam.mytestapp.adapter.ViewPagerAdapter
import co.edu.eam.mytestapp.fragment.DetalleFragment
import co.edu.eam.mytestapp.fragment.ListaEstudiantesFragment
import com.google.android.material.tabs.TabLayoutMediator

class DetalleActivity : AppCompatActivity(), ListaEstudiantesFragment.OnEstudianteSeleccionadoListener {

    lateinit var binding:ActivityDetalleBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetalleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val codigoEstudiante = intent.getStringExtra("item_e")

        var fragment = supportFragmentManager.findFragmentById(R.id.fragment_detalle_estudiante) as DetalleFragment
        fragment.cargarInformacion(codigoEstudiante)

        supportActionBar?.title = getString(R.string.detalle_estudiante)
        supportActionBar?.setDisplayShowTitleEnabled(true)

    }

    override fun onEstudianteSeleccionado(codigo: String) {
        var i = Intent(this, DetalleActivity::class.java)
        i.putExtra("item_e", codigo)
        startActivity(i)
    }
}