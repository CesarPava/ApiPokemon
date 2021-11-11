package co.edu.eam.mytestapp.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import co.edu.eam.mytestapp.R
import co.edu.eam.mytestapp.databinding.FragmentDemoBinding

class DemoFragment : Fragment() {

    lateinit var binding: FragmentDemoBinding
    var color: Int = 0
    lateinit var numero: String

    companion object{
        fun newInstance(color: Int, numero:String):DemoFragment{
            val args = Bundle()
            args.putInt("color", color)
            args.putString("numero", numero)
            val f = DemoFragment()
            f.arguments = args
            return f
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments!=null){
            this.color = requireArguments().getInt("color")
            this.numero = requireArguments().getString("numero", "0")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDemoBinding.inflate(inflater, container, false)

        binding.pantalla.setBackgroundColor(ContextCompat.getColor(requireContext(), color))
        binding.numeroGr.text = numero

        return binding.root
    }

}