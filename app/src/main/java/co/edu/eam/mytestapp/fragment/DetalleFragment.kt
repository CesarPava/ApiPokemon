package co.edu.eam.mytestapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import co.edu.eam.mytestapp.R
import co.edu.eam.mytestapp.adapter.ViewPagerAdapter
import co.edu.eam.mytestapp.databinding.FragmentDetalleBinding
import com.google.android.material.tabs.TabLayoutMediator


class DetalleFragment : Fragment() {

    lateinit var binding:FragmentDetalleBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDetalleBinding.inflate(inflater, container, false)
        return binding.root
    }

    fun cargarInformacion(codigoEstudiante:String?){
        if(codigoEstudiante!=null){
            binding.viewPager.adapter = ViewPagerAdapter(requireActivity(), codigoEstudiante)
            TabLayoutMediator(binding.tabs, binding.viewPager){tab, pos ->
                when(pos){
                    0 -> tab.text = getString(R.string.informacion)
                    1 -> tab.text = getString(R.string.lista_amigos)
                }
            }.attach()
        }
    }

}