package co.edu.eam.mytestapp.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import co.edu.eam.mytestapp.fragment.InformacionEstudianteFragment
import co.edu.eam.mytestapp.fragment.ListaEstudiantesFragment

class ViewPagerAdapter(fragmentActivity: FragmentActivity, var codigo:String): FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return if(position == 0){
            InformacionEstudianteFragment.newInstance(codigo)
        }else{
            ListaEstudiantesFragment.newInstance(codigo)
        }
    }
}

