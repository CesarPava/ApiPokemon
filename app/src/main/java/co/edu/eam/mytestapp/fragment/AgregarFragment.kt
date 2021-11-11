package co.edu.eam.mytestapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import co.edu.eam.mytestapp.R
import co.edu.eam.mytestapp.databinding.FragmentAgregarBinding
import co.edu.eam.mytestapp.model.Estudiante
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.*

class AgregarFragment : DialogFragment() {

    lateinit var binding:FragmentAgregarBinding
    lateinit var fechaNacimiento:Date
    lateinit var listener:OnEstudianteCreadoListener

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAgregarBinding.inflate(inflater, container, false)

        binding.fechaEst.setOnClickListener {
            val datePicker = MaterialDatePicker.Builder.datePicker()
                .setTitleText("Fecha de nacimiento")
                .setSelection(MaterialDatePicker.todayInUtcMilliseconds())
                .build()

            datePicker.show(requireActivity().supportFragmentManager, "FECHA")

            datePicker.addOnPositiveButtonClickListener {
                val sdf = SimpleDateFormat("yyyy/MM/dd")
                sdf.timeZone = TimeZone.getTimeZone("UTC-8")
                val fechaString = sdf.format(it)
                fechaNacimiento = sdf.parse(fechaString)
                binding.fechaEst.setText(fechaString)
            }

        }

        binding.agregarEstudiante.setOnClickListener{
            try {
                val estudiante = Estudiante(
                    binding.codigoEst.text.toString(),
                    fechaNacimiento,
                    binding.nombreEst.text.toString(),
                    floatArrayOf(
                        binding.nota1Est.text.toString().toFloat(),
                        binding.nota2Est.text.toString().toFloat(),
                        binding.nota3Est.text.toString().toFloat()
                    )
                )
                listener.onEstudianteCreado(estudiante)
                dismiss()
            }catch (e:Exception){
                e.printStackTrace()
            }
        }

        return binding.root
    }

    interface OnEstudianteCreadoListener{
        fun onEstudianteCreado(estudiante: Estudiante)
    }

}