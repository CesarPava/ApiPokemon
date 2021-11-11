package co.edu.eam.mytestapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.edu.eam.mytestapp.R
import co.edu.eam.mytestapp.databinding.FragmentInformacionEstudiante2Binding
import co.edu.eam.mytestapp.model.Estudiante
import co.edu.eam.mytestapp.service.EstudianteData
import java.text.SimpleDateFormat

class InformacionEstudianteFragment : Fragment() {

    lateinit var binding:FragmentInformacionEstudiante2Binding
    lateinit var codigoEstudiante: String

    companion object{
        fun newInstance(codigoEstudiante: String):InformacionEstudianteFragment{
            val args = Bundle()
            args.putString("estudiante", codigoEstudiante)
            val fragment = InformacionEstudianteFragment()
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if( arguments != null ){
            this.codigoEstudiante = requireArguments().getString("estudiante", "-1")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentInformacionEstudiante2Binding.inflate(inflater, container, false)
        cargarInformacion()
        return binding.root
    }

    private fun cargarInformacion(){
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        val estudiante = EstudianteData.obtener(codigoEstudiante)
        if(estudiante!=null){
            binding.nombreDetalle.text = estudiante.nombre
            binding.fechaDetalle.text = sdf.format(estudiante.fechaNacimiento)
            binding.notasDetalle.text = estudiante.notas.contentToString()
        }
    }

}