package com.example.finanzas

import android.content.ContentValues

class Entry (var id : Int?, var amount : Int?, var tag : Int?) {
    constructor():this(null, null, null)

    // crear funciones para modificar cada uno de los elementos
}

class Tags (){

    //los tag tienen que tener un maximo de 30 caracteres
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