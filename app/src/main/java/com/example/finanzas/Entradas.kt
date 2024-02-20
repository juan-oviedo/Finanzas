package com.example.finanzas

import android.util.Log
import java.lang.Exception
import java.util.Date

class Entradas () {
// se puede dividir en 2 entradas, una de simple y otra mensual
    fun addEntry (amount: Double, date: Date, tags: List<String>, monthly: Boolean, name: String, fixed: Boolean){
        if (monthly){
            complexEntry(amount, date, tags, name, fixed)
        }
        else{
            simpleEntry(amount, date, tags)
        }
    }

    //funcion para agregar una entrada simple (que no es mensual)
    fun simpleEntry(amount: Double, date: Date,tags: List<String>){
        //agregar la entrada a la base de datos
    }

    //funcion para agregar una entrada compleja
    fun complexEntry (amount: Double, date: Date,tags: List<String>, name: String, fixed: Boolean){
        //agregar la entrada simple:
        simpleEntry(amount, date, tags)

        //agregar a la tabla de entradas mensuales

    }

    //funcion para mostrar todas las entradas simples

    //funcion para mostrar todas las entradas mensuales

    //funcion para eliminar una entrada simple

    //funcion para eliminar una entrada mensual

    //funcion para editar una entrada simple

    //funcion para editar una entrada compleja
}

class Tags (){
    private fun searchTag(name: String): Boolean {
        var isIn: Boolean = false
        //buscar un tag en la base de datos
        return isIn
    }
    fun addTag(name: String){
        if (!searchTag(name)){
            //agregar un tag a la base de datos
        }
        else {
            throw IllegalAccessException("the tag $name has already been added\n ")
        }
    }

    fun listTags(){
        //buscar en la base de datos todos los tags y devolver una lista
    }

    fun deleteTag(name: String){
        if (!searchTag(name)){
            //borrar de la base de datos
        }
        else{
            throw IllegalAccessException("the tag $name is not in the data base\n ")
        }
    }

    //funcion para editar un tag
}