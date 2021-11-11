package co.edu.eam.mytestapp.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import co.edu.eam.mytestapp.R
import co.edu.eam.mytestapp.databinding.ActivityPantalla3Binding
import co.edu.eam.mytestapp.model.Estudiante
import co.edu.eam.mytestapp.utils.Constantes

class Pantalla3Activity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding:ActivityPantalla3Binding
    var estado:Boolean = true
    var pokemonSeleccionado:String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPantalla3Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val estudiante:Estudiante? = intent.getParcelableExtra(Constantes.KEY_ESTUDIANTE)
        if(estudiante!=null){
            Log.e("ACTIVIDAD3", estudiante.toString())
        }

        binding.imagen.setOnClickListener(this)
        binding.leerDatos.setOnClickListener(this)

        binding.txtNombre.isErrorEnabled = true
        binding.txtEdad.isErrorEnabled = true

        val adapter = ArrayAdapter.createFromResource(this, R.array.pokemons, android.R.layout.simple_spinner_item)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinner.adapter = adapter
        binding.spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                pokemonSeleccionado = parent?.getItemAtPosition(position).toString()
                Constantes.mostrarMensaje(baseContext, "La opción seleccionada es : ${pokemonSeleccionado}")
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

        }

    }

    override fun onBackPressed() {
        val intent = intent
        intent.putExtra("RESPUESTA", "Mensaje enviado desde la pantalla 3")
        setResult(12, intent)
        finish()
        super.onBackPressed()
    }

    override fun onClick(v: View?) {
        when(v?.id){
            binding.imagen.id -> {
                if(estado){
                    estado = false
                    binding.imagen.setImageResource(R.drawable.img2)
                }else{
                    estado = true
                    binding.imagen.setImageResource(R.drawable.img1)
                }
            }
            binding.leerDatos.id -> {
                val nombre = binding.nombre.text
                val edad = binding.edad.text

                if(nombre.length > 10 ){
                    binding.txtNombre.error = "Máximo 10 caracteres"
                }else{
                    binding.txtNombre.error = null
                }

            }
        }
    }

    /*
    val TAG = MainActivity::class.java.simpleName
    lateinit private var resultLauncher: ActivityResultLauncher<Intent>
    lateinit var imagen: ImageView
    var currentPhotoPath = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        imagen = findViewById(R.id.imagen)
        resultLauncher = registerForActivityResult( ActivityResultContracts.StartActivityForResult() ) {
            onActivityResult(it.resultCode, it)
        }
    }

    fun irPantalla2(v: View){
        Log.v(TAG, "Pasar a la pantalla 2")
        val intent = Intent(this, Pantalla2Activity::class.java)
        resultLauncher.launch(intent)
    }

    fun irPantalla3(v: View){
        Intent(MediaStore.ACTION_IMAGE_CAPTURE).also { takePictureIntent ->
            takePictureIntent.resolveActivity(packageManager)?.also {
                // Create the File where the photo should go
                val photoFile: File? = try {
                    createImageFile()
                } catch (ex: IOException) {
                    null
                }
                // Continue only if the File was successfully created
                photoFile?.also {
                    val photoURI: Uri = FileProvider.getUriForFile(
                        this,
                        "co.edu.eam.mytestapp.provider",
                        it
                    )
                    takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI)
                    takePictureIntent.putExtra("ID", "234")
                    resultLauncher.launch(takePictureIntent)
                }
            }
        }
    }

    private fun onActivityResult(resultCode:Int, result: ActivityResult){
        val data = result.data?.extras
        if( resultCode == 12 ){
            Toast.makeText(this, "Mensaje: ${data?.getString("RESPUESTA")}", Toast.LENGTH_LONG).show()
        }else if( resultCode == 14 ){
            Toast.makeText(this, "Mensaje: ${data?.getString("RESPUESTA")}", Toast.LENGTH_LONG).show()
        }else{
            Log.v("Carlos", result.data?.getStringExtra("ID").toString() )
            val bm = BitmapFactory.decodeFile(currentPhotoPath)
            imagen.setImageBitmap(bm)
        }
    }

    @Throws(IOException::class)
    private fun createImageFile(): File {
        // Create an image file name
        val timeStamp: String = SimpleDateFormat("yyyyMMdd_HHmmss").format(Date())
        val storageDir: File? = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return File.createTempFile(
            "JPEG_${timeStamp}_", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            // Save a file: path for use with ACTION_VIEW intents
            currentPhotoPath = absolutePath
        }
    }
     */

}