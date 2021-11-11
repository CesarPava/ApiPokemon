package co.edu.eam.mytestapp.fragment

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import co.edu.eam.mytestapp.R
import co.edu.eam.mytestapp.activity.CrearEstudianteActivity
import co.edu.eam.mytestapp.adapter.EstudianteAdapter
import co.edu.eam.mytestapp.databinding.FragmentListaEstudiantesBinding
import co.edu.eam.mytestapp.model.Estudiante
import co.edu.eam.mytestapp.service.EstudianteData
import co.edu.eam.mytestapp.utils.Idioma
import com.google.android.material.snackbar.Snackbar
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator

class ListaEstudiantesFragment : Fragment(), EstudianteAdapter.OnClickEstudianteAdapter, AgregarFragment.OnEstudianteCreadoListener {

    lateinit var binding: FragmentListaEstudiantesBinding
    lateinit var codigo:String
    lateinit var lista:ArrayList<Estudiante>
    lateinit var adapter:EstudianteAdapter
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>
    lateinit var listener:OnEstudianteSeleccionadoListener

    companion object{
        fun newInstance(codigo:String):ListaEstudiantesFragment{
            val args = Bundle()
            args.putString("codigo", codigo)
            val f = ListaEstudiantesFragment()
            f.arguments = args
            return f
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if(context is Activity){
            try{
                listener = context as OnEstudianteSeleccionadoListener
            }catch (e:Exception){
                e.printStackTrace()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if(arguments!=null){
            this.codigo = requireArguments().getString("codigo", "-1")
        }
        resultLauncher = registerForActivityResult( ActivityResultContracts.StartActivityForResult() ) {
            onActivityResult(it.resultCode, it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentListaEstudiantesBinding.inflate(inflater, container, false)

        if(codigo == "-1"){
            lista = EstudianteData.listar()
            setHasOptionsMenu(true)
        }else{
            lista = EstudianteData.obtenerAmigos(codigo)
        }

        cargarLista(lista)
        return binding.root
    }

    private fun cargarLista( lista:ArrayList<Estudiante> ){
        adapter = EstudianteAdapter(lista, this)
        binding.listaEstud.adapter = adapter
        binding.listaEstud.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        ItemTouchHelper( CustomSimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) ).attachToRecyclerView( binding.listaEstud )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            R.id.menu_agregar -> {
                //resultLauncher.launch( Intent(requireContext(), CrearEstudianteActivity::class.java) )
                val dialogo = AgregarFragment()
                dialogo.listener = this
                dialogo.setStyle(DialogFragment.STYLE_NORMAL, R.style.DialogoTitulo)
                dialogo.show(requireActivity().supportFragmentManager, "AGREGAR")
            }
            R.id.menu_modificar -> {
                val aux = lista[1]
                lista[1] = lista[2]
                lista[2] = aux
                adapter.notifyItemMoved(1,2)
            }
            R.id.cambiar_idioma -> {
                Idioma.seleccionarIdioma(requireContext())
                val intent = activity?.intent
                intent!!.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK
                activity?.finish()
                activity?.startActivity(intent)
            }
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_estudiantes, menu)

        val itemBusqueda = menu.findItem(R.id.menu_buscar)
        val searchView: SearchView = itemBusqueda.actionView as SearchView

        searchView.queryHint = "Nombre del estudiante..."

        searchView.setOnQueryTextListener( object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if(newText!=null){
                    adapter.filter.filter(newText)
                }
                return false
            }
        } )

    }

    private fun onActivityResult(resultCode:Int, result: ActivityResult) {
        val data = result.data?.extras
        if(resultCode == 14){
            adapter.notifyItemInserted(0)
            binding.listaEstud.scrollToPosition(0)
        }else if(resultCode == 15){
            val pos = data!!.getInt("pos")
            adapter.notifyItemChanged(pos)
        }

    }

    override fun onClickEstudiante(codigo: String) {
        listener.onEstudianteSeleccionado(codigo)
    }

    inner class CustomSimpleCallback(drag:Int, swipe:Int): ItemTouchHelper.SimpleCallback(drag, swipe){

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean {
            return false
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val pos = viewHolder.adapterPosition
            val estudiante = EstudianteData.listaEstudiantes[pos]
            when(direction){
                ItemTouchHelper.LEFT -> {
                    EstudianteData.eliminar(estudiante.codigo!!)
                    adapter.notifyItemRemoved(pos)
                    Snackbar.make(binding.listaEstud, "EL estudiante se llama ${estudiante.nombre}", Snackbar.LENGTH_LONG)
                        .setAction("Deshacer", View.OnClickListener {
                            EstudianteData.listaEstudiantes.add(pos, estudiante)
                            adapter.notifyItemInserted(pos)
                        }).show()
                }
                ItemTouchHelper.RIGHT -> {
                    val i = Intent(requireContext(), CrearEstudianteActivity::class.java)
                    i.putExtra("estudiante", estudiante)
                    i.putExtra("posicion", pos)
                    resultLauncher.launch(i)
                }
            }
        }

        override fun onChildDraw(
            c: Canvas,
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            dX: Float,
            dY: Float,
            actionState: Int,
            isCurrentlyActive: Boolean
        ) {

            RecyclerViewSwipeDecorator.Builder(c,recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                .addSwipeLeftBackgroundColor(ContextCompat.getColor(requireContext(), R.color.rojo))
                .addSwipeRightBackgroundColor(ContextCompat.getColor(requireContext(), R.color.verde))
                .addSwipeLeftActionIcon(android.R.drawable.ic_menu_delete)
                .addSwipeRightActionIcon(android.R.drawable.ic_menu_edit)
                .create()
                .decorate()

            super.onChildDraw(
                c,
                recyclerView,
                viewHolder,
                dX,
                dY,
                actionState,
                isCurrentlyActive
            )
        }

    }

    interface OnEstudianteSeleccionadoListener{
        fun onEstudianteSeleccionado(codigo:String)
    }

    override fun onEstudianteCreado(estudiante: Estudiante) {
        EstudianteData.agregar(estudiante)
        adapter.notifyItemInserted(0)
    }

}