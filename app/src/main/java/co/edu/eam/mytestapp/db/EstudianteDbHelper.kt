package co.edu.eam.mytestapp.db

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import co.edu.eam.mytestapp.model.Estudiante
import java.text.SimpleDateFormat

const val DATABASE_NAME = "estudiantes.db"
const val VERSION = 2

class EstudianteDbHelper(context:Context):SQLiteOpenHelper(context, DATABASE_NAME, null, VERSION ) {

    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL("create table ${EstudianteContrato.TABLE_NAME} (" +
                "${EstudianteContrato.CODIGO} varchar not null primary key, "+
                "${EstudianteContrato.NOMBRE} varchar not null, "+
                "${EstudianteContrato.FECHA_NACIMIENTO} varchar not null, "+
                "${EstudianteContrato.NOTA_1} float not null, "+
                "${EstudianteContrato.NOTA_2} float not null, "+
                "${EstudianteContrato.NOTA_3} float not null "+
                ")")

        p0?.execSQL("create table ${AmigoContrato.TABLE_NAME} (" +
                "${AmigoContrato.ESTUDIANTE_ID} varchar not null, " +
                "${AmigoContrato.AMIGO_ID} varchar not null, " +
                "primary key(${AmigoContrato.ESTUDIANTE_ID}, ${AmigoContrato.AMIGO_ID}), "+
                "foreign key(${AmigoContrato.ESTUDIANTE_ID}) references  ${EstudianteContrato.TABLE_NAME} (${EstudianteContrato.CODIGO}), "+
                "foreign key(${AmigoContrato.AMIGO_ID}) references  ${EstudianteContrato.TABLE_NAME} (${EstudianteContrato.CODIGO}) "+
                ")")

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("drop table if exists ${EstudianteContrato.TABLE_NAME}")
        p0?.execSQL("drop table if exists ${AmigoContrato.TABLE_NAME}")
        onCreate(p0)
    }

    override fun onOpen(db: SQLiteDatabase?) {
        super.onOpen(db)
        if(! db!!.isReadOnly ){
            db.setForeignKeyConstraintsEnabled(true)
        }
    }

    fun guardarEstudiante(estudiante: Estudiante){
        writableDatabase.insert(
            EstudianteContrato.TABLE_NAME,
            null,
            estudiante.toContentValues()
        )
    }

    fun eliminarEstudiante(codigo:String){
        writableDatabase.delete(
            EstudianteContrato.TABLE_NAME,
            "${EstudianteContrato.CODIGO} = ?",
            arrayOf(codigo)
        )
    }

    fun actualizarEstudiante(estudiante: Estudiante){
        writableDatabase.update(
            EstudianteContrato.TABLE_NAME,
            estudiante.toContentValues(),
            "${EstudianteContrato.CODIGO} = ?",
            arrayOf(estudiante.codigo)
        )
    }

    fun obtenerLista():ArrayList<Estudiante>{
        var lista = ArrayList<Estudiante>()

        val cursor = readableDatabase.query(
            EstudianteContrato.TABLE_NAME,
            arrayOf(EstudianteContrato.CODIGO, EstudianteContrato.NOMBRE, EstudianteContrato.FECHA_NACIMIENTO, EstudianteContrato.NOTA_1, EstudianteContrato.NOTA_2, EstudianteContrato.NOTA_3),
            null,
            null,
            null,
            null,
            null
        )

        val sdf = SimpleDateFormat("dd/MM/yyyy")

        if(cursor.moveToFirst()){
            do{

                lista.add(
                    Estudiante(
                        cursor.getString(0),
                        sdf.parse(cursor.getString(2)),
                        cursor.getString(1),
                        floatArrayOf( cursor.getFloat(3), cursor.getFloat(4), cursor.getFloat(5) )
                    )
                )

            }while (cursor.moveToNext())
        }

        return lista
    }

    fun obtenerEstudiante(codigo:String):Estudiante?{
        var estudiante:Estudiante? = null

        val cursor = readableDatabase.query(
            EstudianteContrato.TABLE_NAME,
            arrayOf(EstudianteContrato.CODIGO, EstudianteContrato.NOMBRE, EstudianteContrato.FECHA_NACIMIENTO, EstudianteContrato.NOTA_1, EstudianteContrato.NOTA_2, EstudianteContrato.NOTA_3),
            "${EstudianteContrato.CODIGO} = ?",
            arrayOf(codigo),
            null,
            null,
            null
        )

        val sdf = SimpleDateFormat("dd/MM/yyyy")

        if(cursor.moveToFirst()){
            estudiante = Estudiante(
                cursor.getString(0),
                sdf.parse(cursor.getString(2)),
                cursor.getString(1),
                floatArrayOf( cursor.getFloat(3), cursor.getFloat(4), cursor.getFloat(5) ) )
        }

        return estudiante
    }

    fun guardarAmigo(codigoEstudiante: String, codigoAmigo:String){

        val values = ContentValues()
        values.put(AmigoContrato.ESTUDIANTE_ID, codigoEstudiante)
        values.put(AmigoContrato.AMIGO_ID, codigoAmigo)

        writableDatabase.insert(
            AmigoContrato.TABLE_NAME,
            null,
            values
        )
    }

    fun obtenerAmigos(codigo:String):ArrayList<Estudiante>{
        val lista = ArrayList<Estudiante>()
        val c = readableDatabase.query(
            "${AmigoContrato.TABLE_NAME} inner join ${EstudianteContrato.TABLE_NAME} on ${AmigoContrato.AMIGO_ID} = ${EstudianteContrato.CODIGO}",
            arrayOf(EstudianteContrato.CODIGO, EstudianteContrato.NOMBRE, EstudianteContrato.FECHA_NACIMIENTO, EstudianteContrato.NOTA_1, EstudianteContrato.NOTA_2, EstudianteContrato.NOTA_3),
            "${AmigoContrato.ESTUDIANTE_ID} = ?",
            arrayOf(codigo),
            null,
            null,
            null
        )

        val sdf = SimpleDateFormat("dd/MM/yyyy")

        if(c.moveToFirst()){
            do{
                lista.add( Estudiante( c.getString(0), sdf.parse( c.getString(2) ), c.getString(1), floatArrayOf( c.getFloat(3), c.getFloat(4), c.getFloat(5) ) ) )
            }while (c.moveToNext());
        }

        return lista
    }


}