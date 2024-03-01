package com.example.finanzas

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

const val Entry_Table = "`Entry_Table`"
const val Column_Entry_ID = "`ID_Entry`"
const val Column_Entry_AMOUNT = "`Amount`"
const val Column_Entry_TAG = "`Tag_ID`"
const val Column_Entry_INCOME = "`IS_INCOME_Entry`"
const val Column_Entry_TIME_CREATION = "`TIME_CREATION_Entry`"
const val Column_Entry_TIME_UPDATE = "`TIME_UPDATE_Entry`"
const val Column_Entry_TIME_DELETION = "`TIME_DELETION_Entry`"

const val Tag_Table = "`Tag_Table`"
const val Column_Tag_ID = "`ID_Tag`"
const val Column_Tag_NAME = "`Name`"
const val Column_Tag_INCOME = "`IS_INCOME_Tag`"
const val Column_Tag_TIME_CREATION = "`TIME_CREATION_Tag`"
const val Column_Tag_TIME_UPDATE = "`TIME_UPDATE_tag`"
const val Column_Tag_TIME_DELETION = "`TIME_DELETION_Tag`"

class DataBaseHelper (context: Context) : SQLiteOpenHelper(context, "Finanzas.db", null, 1){

    override fun onCreate(db: SQLiteDatabase) {
        val createTableEntry : String = " CREATE TABLE $Entry_Table ( " +
                " $Column_Entry_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " $Column_Entry_AMOUNT DECIMAL(10,2) NOT NULL DEFAULT '0.0', " +
                " $Column_Entry_TAG INTEGER NOT NULL DEFAULT '1', " +
                " $Column_Entry_INCOME Boolean NOT NULL DEFAULT 'TRUE' , " +
                " $Column_Entry_TIME_CREATION char(23)  NOT NULL DEFAULT '' ," +
                " $Column_Entry_TIME_UPDATE char(23)  NOT NULL DEFAULT '' ," +
                " $Column_Entry_TIME_DELETION char(23) ," +
                " FOREIGN KEY ($Column_Entry_TAG) REFERENCES $Tag_Table($Column_Tag_ID) " +
                " ) "

        val createTableTag : String = "CREATE TABLE $Tag_Table ( " +
                " $Column_Tag_ID INTEGER PRIMARY KEY, " +
                " $Column_Tag_NAME char(35)  NOT NULL DEFAULT '' , " +
                " $Column_Tag_INCOME Boolean NOT NULL DEFAULT 'TRUE' ," +
                " $Column_Tag_TIME_CREATION char(23)  NOT NULL DEFAULT '' , " +
                " $Column_Tag_TIME_UPDATE char(23)  NOT NULL DEFAULT ''  ," +
                " $Column_Tag_TIME_DELETION char(23) " +
                " ) "

        db.execSQL(createTableTag)
        db.execSQL(createTableEntry)

        val cv = ContentValues()
        cv.put(Column_Tag_NAME, "DEFAULT")
        cv.put(Column_Tag_INCOME, true)

        val success = db.insert(Tag_Table, null, cv)
        if (success == -1L){
            throw SQLiteException("Default tag could not be inserted")
        }
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        TODO("Not yet implemented")
    }

    fun add_Entry(entry: Entry): Long {
        val db : SQLiteDatabase = this.writableDatabase
        val cv = ContentValues()
        cv.put(Column_Entry_AMOUNT, entry.get_amount())
        if (entry.get_tag() != null) {
            cv.put(Column_Entry_TAG, entry.get_tag())
        }
        else{
            cv.put(Column_Entry_TAG, 1)
        }
        cv.put(Column_Entry_INCOME, entry.get_income())

        cv.put(Column_Entry_TIME_CREATION, entry.get_timeCreation())

        val id = db.insert(Entry_Table, null, cv)
        entry.set_id(id)
        if (id == -1L){
            throw SQLiteException("the element could not be inserted")
        }
        return id
    }

    fun add_Tag (tag: Tag) : Long{
        val db : SQLiteDatabase = this.writableDatabase
        val cv = ContentValues()
        cv.put(Column_Tag_NAME, tag.get_name())
        cv.put(Column_Tag_INCOME, tag.get_is_income())
        cv.put(Column_Tag_TIME_CREATION, tag.get_timeCreation())

        val id = db.insert(Tag_Table, null, cv)
        tag.set_id(id)
        if (id == -1L){
            throw SQLiteException("the element could not be inserted")
        }
        return id
    }

    /**
     * This function get all entries from the data base,
     * also (for efficiency) calculate the balance.
     * the balance is return by the object result
     */
    fun get_all_Entry (result : Results): MutableList<Entry> {
        val returnList: MutableList<Entry> = mutableListOf()

        val query = "SELECT * FROM $Entry_Table WHERE $Column_Entry_TIME_DELETION IS NULL"

        val db : SQLiteDatabase = this.readableDatabase

        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()){
            do {
                val id = cursor.getLong(0)
                val amount = cursor.getDouble(1)
                val tagId = cursor.getLong(2)
                val income = cursor.getInt(3) == 1
                val timeCreation = cursor.getString(4)

                val entry = Entry(id, amount, tagId, income, timeCreation)
                returnList.add(entry)
                result.calculateResult(amount, income)
            }while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return returnList
    }

    fun get_all_tag (isIncome : Boolean) : MutableList<Tag> {
        val returnList: MutableList<Tag> = mutableListOf()

        val query = "SELECT * FROM $Tag_Table " +
                    " WHERE $Column_Tag_INCOME = $isIncome AND $Column_Tag_ID != 1 AND $Column_Tag_TIME_DELETION IS NULL"

        val db : SQLiteDatabase = this.readableDatabase

        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()){
            do {
                val id = cursor.getLong(0)
                val name = cursor.getString(1)
                val income = cursor.getInt(2) == 1
                val timeCreation = cursor.getString(3)

                val tag = Tag(id, name, income, timeCreation)
                returnList.add(tag)

            }while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return returnList
    }

    fun get_tag_balance (): MutableList<TagBalance>{
        val returnList: MutableList<TagBalance> = mutableListOf()

        val query = "SELECT " +
                    " t.$Column_Tag_ID AS tag_id, " +
                    " t.$Column_Tag_NAME AS tag_name, " +
                    " t.$Column_Tag_INCOME AS is_income, " +
                    " SUM(e.$Column_Entry_AMOUNT) AS total_amount " +
                    " FROM $Entry_Table e " +
                    " JOIN $Tag_Table t ON e.$Column_Entry_TAG = t.$Column_Tag_ID " +
                    " WHERE $Column_Tag_ID != 1 AND $Column_Entry_TIME_DELETION IS NULL AND $Column_Tag_TIME_DELETION IS NULL" +
                    " GROUP BY e.$Column_Entry_TAG "

        val db : SQLiteDatabase = this.readableDatabase

        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()){
            do {
                val id = cursor.getLong(0)
                val name = cursor.getString(1)
                val income = cursor.getInt(2) == 1
                val amount = cursor.getDouble(3)

                val tagBalance = TagBalance(id, name, income, amount)
                returnList.add(tagBalance)
            }while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return returnList
    }

    fun get_entry (entryId : Long): Entry {
        val query = "SELECT * FROM $Entry_Table WHERE $Column_Entry_ID == $entryId AND $Column_Entry_TIME_DELETION IS NULL"

        val db : SQLiteDatabase = this.readableDatabase

        val cursor = db.rawQuery(query, null)

        if(cursor.count > 1){
            cursor.close()
            db.close()
            throw IllegalStateException("Database is inconsistent: Multiple data found for id $entryId")
        }else if (cursor.count == 0){
            cursor.close()
            db.close()
            throw IllegalStateException("Database is inconsistent: No data found for id $entryId")
        }

        cursor.moveToFirst()
        
        val id = cursor.getLong(0)
        val amount = cursor.getDouble(1)
        val tag_id = cursor.getLong(2)
        val income = cursor.getInt(3) == 1
        val timeCreation = cursor.getString(4)

        val entry = Entry(id, amount, tag_id, income, timeCreation)

        cursor.close()
        db.close()
        return entry
    }

    fun update_Entry (entry: Entry): Long {
        val db : SQLiteDatabase = this.writableDatabase
        val cv = ContentValues()
        cv.put(Column_Entry_AMOUNT, entry.get_amount())
        if (entry.get_tag() != null) {
            cv.put(Column_Entry_TAG, entry.get_tag())
        }
        else{
            cv.put(Column_Entry_TAG, 1)
        }
        cv.put(Column_Entry_INCOME, entry.get_income())

        val time = LocalDateTime.now()
        val timeUpdate = time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

        cv.put(Column_Entry_TIME_UPDATE, timeUpdate)

        val id = entry.get_id()

        val affectedRows  = db.update(Entry_Table, cv, "$Column_Entry_ID == $id", null )

        if (affectedRows == 0) {
            // Handle the case where no rows were affected (entry with the given ID not found)
            db.close()
            throw IllegalStateException("No entry found with ID $id")
        }
        db.close()
        return id
    }

    fun delete_Entry (entry: Entry): Long {
        val db : SQLiteDatabase = this.writableDatabase
        val cv = ContentValues()

        val time = LocalDateTime.now()
        val timeDeletion = time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

        cv.put(Column_Entry_TIME_DELETION, timeDeletion)

        val id = entry.get_id()

        val affectedRows  = db.update(Entry_Table, cv, "$Column_Entry_ID == $id", null )

        if (affectedRows == 0) {
            // Handle the case where no rows were affected (entry with the given ID not found)
            db.close()
            throw IllegalStateException("No entry found with ID $id")
        }
        db.close()
        return id
    }

    fun get_Tag (tagId : Long): Tag {
        val query = "SELECT * FROM $Tag_Table WHERE $Column_Tag_ID == $tagId AND $Column_Tag_TIME_DELETION IS NULL"

        val db : SQLiteDatabase = this.readableDatabase

        val cursor = db.rawQuery(query, null)

        if(cursor.count > 1){
            cursor.close()
            db.close()
            throw IllegalStateException("Database is inconsistent: Multiple data found for id $tagId")
        }else if (cursor.count == 0){
            cursor.close()
            db.close()
            throw IllegalStateException("Database is inconsistent: No data found for id $tagId")
        }

        cursor.moveToFirst()

        val id = cursor.getLong(0)
        val name = cursor.getString(1)
        val income = cursor.getInt(2) == 1
        val timeCreation = cursor.getString(3)

        val tag = Tag(id, name, income, timeCreation)

        cursor.close()
        db.close()
        return tag
    }

    fun time (){
        val timestampString = LocalDateTime.now()
        val string = timestampString.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

        val timestamp = LocalDateTime.parse(string, DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }
}