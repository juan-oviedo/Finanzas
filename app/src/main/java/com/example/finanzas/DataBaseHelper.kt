package com.example.finanzas

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

const val Entry_Table = "`Entry_Table`"
const val Column_Entry_ID = "`ID`"
const val Column_Entry_AMOUNT = "`Amount`"
const val Column_Entry_TAG = "`Tag_ID`"

const val Tag_Table = "`Tag_Table`"
const val Column_Tag_ID = "`ID`"
const val Column_Tag_NAME = "`Name`"

class DataBaseHelper (context: Context) : SQLiteOpenHelper(context, "Finanzas.db", null, 1){

    override fun onCreate(db: SQLiteDatabase) {
        val createTableEntry : String = " CREATE TABLE $Entry_Table ( " +
                " $Column_Entry_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " $Column_Entry_AMOUNT DECIMAL(10,2) NOT NULL DEFAULT '0.0', " +
                " $Column_Entry_TAG INTEGER NOT NULL DEFAULT '1', " +
                " FOREIGN KEY ($Column_Entry_TAG) REFERENCES $Tag_Table($Column_Tag_ID) " +
                " ) "
        val createTableTag : String = "CREATE TABLE $Tag_Table ( " +
                " $Column_Tag_ID INTEGER PRIMARY KEY, " +
                " $Column_Tag_NAME char(35)  NOT NULL DEFAULT '' " +
                " ) "

        db.execSQL(createTableTag)
        db.execSQL(createTableEntry)

        val cv = ContentValues()
        cv.put(Column_Tag_NAME, "DEFAULT")

        val succes = db.insert(Tag_Table, null, cv)
        if (succes == -1L){
            throw SQLiteException("Default tag could not be inserted")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun add(): Long {
        val db : SQLiteDatabase = this.writableDatabase
        val cv = ContentValues()
        cv.put(Column_Entry_AMOUNT, 1234.12)
        cv.put(Column_Entry_TAG, 1)

        val id = db.insert(Entry_Table, null, cv)

        return id
    }

}