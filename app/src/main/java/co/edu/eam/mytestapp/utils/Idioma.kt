package co.edu.eam.mytestapp.utils

import android.content.Context
import android.content.ContextWrapper
import android.content.res.Configuration
import android.content.res.Resources
import android.os.Build
import android.os.LocaleList
import java.util.*

object Idioma {

    fun seleccionarIdioma(context: Context):String{
        val preferences = context.getSharedPreferences("preferencias_app", Context.MODE_PRIVATE)
        var idioma = preferences.getString("idioma", "es")

        idioma = if(idioma == "es"){
            "en"
        }else{
            "es"
        }

        val editor = preferences.edit()
        editor.putString("idioma", idioma)
        editor.apply()

        return idioma
    }

    fun cambiarIdioma(context: Context): ContextWrapper? {
        var context = context
        val resources: Resources = context.resources
        val configuration: Configuration = resources.configuration

        val preferences = context.getSharedPreferences("preferencias_app", Context.MODE_PRIVATE)
        var idioma = preferences.getString("idioma", "es")
        val localeToSwitchTo = Locale(idioma)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            val localeList = LocaleList(localeToSwitchTo)
            LocaleList.setDefault(localeList)
            configuration.setLocales(localeList)
        } else {
            configuration.locale = localeToSwitchTo
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N_MR1) {
            context = context.createConfigurationContext(configuration)
        } else {
            resources.updateConfiguration(configuration, resources.displayMetrics)
        }
        return ContextUtils(context)
    }

    class ContextUtils(context: Context): ContextWrapper(context) {  }

}