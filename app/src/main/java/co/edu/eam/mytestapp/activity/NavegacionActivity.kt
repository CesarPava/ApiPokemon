package co.edu.eam.mytestapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.core.view.GravityCompat
import co.edu.eam.mytestapp.R
import co.edu.eam.mytestapp.databinding.ActivityNavegacionBinding
import co.edu.eam.mytestapp.fragment.DemoFragment
import com.google.android.material.navigation.NavigationView
import androidx.annotation.NonNull

class NavegacionActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var binding:ActivityNavegacionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNavegacionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.contenido.miToolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setHomeButtonEnabled(true)

        val toggle = ActionBarDrawerToggle(this, binding.drawerLayout, R.string.abrir, R.string.cerrar)
        binding.drawerLayout.addDrawerListener(toggle)
        toggle.syncState()

        binding.navView.setNavigationItemSelectedListener(this)

        reemplazarFragmento(R.color.rojo, "1", false)
        binding.navView.menu.getItem(0).isChecked = true;
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {

        limpiarMenu(binding.navView.menu)
        item.isChecked = true

        when(item.itemId){
            R.id.menu_seccion_1 -> reemplazarFragmento(R.color.rojo, "1")
            R.id.menu_seccion_2 -> reemplazarFragmento(R.color.verde, "2")
            R.id.menu_opcion1 -> startActivity(Intent(this, ListaActivity::class.java))
            R.id.menu_opcion2 -> startActivity(Intent(this, Navegacion2Activity::class.java))
        }

        binding.drawerLayout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun limpiarMenu(menu: Menu) {
        val size: Int = menu.size()
        for (i in 0 until size) {
            val item: MenuItem = menu.getItem(i)
            if (item.hasSubMenu()) {
                limpiarMenu(item.subMenu)
            } else {
                item.isChecked = false
            }
        }
    }

    fun reemplazarFragmento(color:Int, numero:String, pila:Boolean = true){
        if(pila) {
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.content_frame, DemoFragment.newInstance(color, numero))
                .addToBackStack(null)
                .commit()
        }else{
            supportFragmentManager
                .beginTransaction()
                .replace(R.id.content_frame, DemoFragment.newInstance(color, numero))
                .commit()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home -> binding.drawerLayout.openDrawer( GravityCompat.START )
        }
        return super.onOptionsItemSelected(item)
    }

}