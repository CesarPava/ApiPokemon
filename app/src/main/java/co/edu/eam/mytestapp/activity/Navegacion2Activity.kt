package co.edu.eam.mytestapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import co.edu.eam.mytestapp.R
import co.edu.eam.mytestapp.databinding.ActivityNavegacion3Binding
import co.edu.eam.mytestapp.fragment.DemoFragment

class Navegacion2Activity : AppCompatActivity() {

    lateinit var binding:ActivityNavegacion3Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavegacion3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        reemplazarFragmento(R.color.rojo, "1", false)

        binding.navInferior.setOnItemSelectedListener {
            when(it.itemId){
                R.id.page_1 ->  reemplazarFragmento(R.color.rojo, "1")
                R.id.page_2 ->  reemplazarFragmento(R.color.verde, "2")
                R.id.page_3 ->  reemplazarFragmento(R.color.purple_200, "3")
            }
            true
        }

    }

    fun reemplazarFragmento(color:Int, numero:String, pila:Boolean = true){
        if(pila) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frame_layout, DemoFragment.newInstance(color, numero))
                .addToBackStack(null)
                .commit()
        }else{
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.frame_layout, DemoFragment.newInstance(color, numero))
                .commit()
        }
    }


}