package co.edu.eam.mytestapp.model

import android.content.ContentValues
import android.os.Parcel
import android.os.Parcelable
import co.edu.eam.mytestapp.db.EstudianteContrato
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class Estudiante (var codigo:String?, var fechaNacimiento:Date, var nombre:String?, var notas:FloatArray?) : Parcelable{

    var amigos:ArrayList<Estudiante> = ArrayList()

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readSerializable() as Date,
        parcel.readString(),
        parcel.createFloatArray()
    ) {
        amigos = parcel.createTypedArrayList(CREATOR) as ArrayList<Estudiante>
    }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeString(codigo)
        dest?.writeSerializable(fechaNacimiento)
        dest?.writeString(nombre)
        dest?.writeFloatArray(notas)
        dest?.writeTypedList(amigos)
    }

    override fun toString(): String {
        return "Estudiante(codigo=$codigo, fechaNacimiento=$fechaNacimiento, nombre=$nombre, notas=${notas?.contentToString()}, amigos=$amigos)"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Estudiante

        if (codigo != other.codigo) return false

        return true
    }

    override fun hashCode(): Int {
        return codigo?.hashCode() ?: 0
    }


    companion object CREATOR : Parcelable.Creator<Estudiante> {
        override fun createFromParcel(parcel: Parcel): Estudiante {
            return Estudiante(parcel)
        }

        override fun newArray(size: Int): Array<Estudiante?> {
            return arrayOfNulls(size)
        }
    }

    fun toContentValues():ContentValues{
        val values = ContentValues()
        val sdf = SimpleDateFormat("dd/MM/yyyy")
        values.put(EstudianteContrato.CODIGO, this.codigo)
        values.put(EstudianteContrato.NOMBRE, this.nombre)
        values.put(EstudianteContrato.FECHA_NACIMIENTO, sdf.format(this.fechaNacimiento))
        values.put(EstudianteContrato.NOTA_1, notas!![0])
        values.put(EstudianteContrato.NOTA_2, notas!![1])
        values.put(EstudianteContrato.NOTA_3, notas!![2])

        return values
    }

}