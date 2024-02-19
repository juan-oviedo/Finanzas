package com.example.finanzas

import android.util.Log
import java.lang.Exception
import java.util.Date

class Entradas (val amount: Double, val date: Date, val monthly: Boolean, val fixed: Boolean, val tags: String) {

    // ahora deberia agregar los metodos para agregar la entrada a la base de datos

}

class Tags (val name: String){
    var newTag = false
    init {
        try {
            // buscar en la base de datos si existe el tag
        }
        catch (e: Exception) {
            Log.i("tags", "no se encontro el tag $name")
            newTag = true
        }
    }

    fun addTag(){
        if (newTag){
            //agregar un tag a la base de datos
        }
        else {
            throw IllegalAccessException("the tag $name has already been added\n ")
        }
    }

    //funcion para buscar un tag
    //funcion para borrar un tag
}