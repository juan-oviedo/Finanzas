package com.example.finanzas

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import java.time.LocalDateTime
import java.time.Month
import java.time.format.DateTimeFormatter

const val Entry_Table = "`Entry_Table`"
const val Column_Entry_ID = "`ID_Entry`"
const val Column_Entry_AMOUNT = "`Amount`"
const val Column_Entry_INCOME = "`IS_INCOME_Entry`"
const val Column_Entry_TIME_CREATION = "`TIME_CREATION_Entry`"
const val Column_Entry_DATE = "`DATE_Entry`"
const val Column_Entry_TIME_UPDATE = "`TIME_UPDATE_Entry`"
const val Column_Entry_TIME_DELETION = "`TIME_DELETION_Entry`"

const val Tag_Table = "`Tag_Table`"
const val Column_Tag_ID = "`ID_Tag`"
const val Column_Tag_NAME = "`Name`"
const val Column_Tag_INCOME = "`IS_INCOME_Tag`"
const val Column_Tag_TIME_DELETION = "`TIME_DELETION_Tag`"

const val EntryTag_Table = "`EntryTag_Table`"
const val Column_EntryTag_ID_ENTRY = "`ID_ENTRY_EntryTag`"
const val Column_EntryTag_ID_TAG = "`ID_TAG_EntryTag`"

class DataBaseHelper (context: Context) : SQLiteOpenHelper(context, "Finanzas.db", null, 1){

    override fun onCreate(db: SQLiteDatabase) {
        val createTableEntry : String = " CREATE TABLE $Entry_Table ( " +
                " $Column_Entry_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                " $Column_Entry_AMOUNT DECIMAL(10,2) NOT NULL DEFAULT '0.0', " +
                " $Column_Entry_INCOME Boolean NOT NULL DEFAULT 'TRUE' , " +
                " $Column_Entry_TIME_CREATION char(23)  NOT NULL DEFAULT '' ," +
                " $Column_Entry_DATE char(23)  NOT NULL DEFAULT '' ," +
                " $Column_Entry_TIME_UPDATE char(23)  NOT NULL DEFAULT '' ," +
                " $Column_Entry_TIME_DELETION char(23) " +
                " ) "

        val createTableTag : String = "CREATE TABLE $Tag_Table ( " +
                " $Column_Tag_ID INTEGER PRIMARY KEY, " +
                " $Column_Tag_NAME char(35)  NOT NULL DEFAULT '' , " +
                " $Column_Tag_INCOME Boolean NOT NULL DEFAULT 'TRUE' ," +
                " $Column_Tag_TIME_DELETION char(23) " +
                " ) "

        val createTableEntryTag : String = "CREATE TABLE $EntryTag_Table (" +
                " $Column_EntryTag_ID_ENTRY INTEGER NOT NULL DEFAULT '0' , " +
                " $Column_EntryTag_ID_TAG INTEGER NOT NULL DEFAULT '1' , " +
                " FOREIGN KEY ($Column_EntryTag_ID_ENTRY) REFERENCES $Entry_Table($Column_Entry_ID), " +
                " FOREIGN KEY ($Column_EntryTag_ID_TAG) REFERENCES $Tag_Table($Column_Tag_ID), " +
                " PRIMARY KEY ($Column_EntryTag_ID_ENTRY, $Column_EntryTag_ID_TAG) " +
                " ) "

        /*
        para gregar un ahorro hay que agregar una tabla que tenga
        la cantidad de pesos, el tipo de ahorro, la cantidad en la otra moneda y la fecha que se realizo el cambio.
        entonces cuando se agrega hay que buscar la cotizacion de ese dia y realizar el cambio
        luego cuando se quiera ver la cotizacion actual hay que buscar la cotizacion de hoy y realizar los cambios
        luego para realizar un cambio de un ahorro se tiene que seleccionar un ahorro y cambiar,
        lo que tendria que buscar la cotizacion del dia y agregar como ingreso la venta

        ademas deberia agregar una columna que se si es editable una entrada,
        para que al agregar un ahorro, se ponga como costo, y no se pueda editar por el usuario
        lo que se edita es el ahorro que luego llama a una funcion que edite el costo

        para borrar un ahorro se borra de la tabla y ademas se borra el costo de la tabla de entry
         */

        db.execSQL(createTableTag)
        db.execSQL(createTableEntry)
        db.execSQL(createTableEntryTag)

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
        cv.put(Column_Entry_INCOME, entry.get_income())
        cv.put(Column_Entry_TIME_CREATION, entry.get_timeCreation())
        cv.put(Column_Entry_DATE, entry.get_date())

        val id = db.insert(Entry_Table, null, cv)
        entry.set_id(id)
        if (id == -1L){
            db.close()
            throw SQLiteException("the element could not be inserted")
        }

        val entryTagAdd = add_entry_tags(id, entry.get_tags())
        if (entryTagAdd != entry.get_tags().size){
            db.close()
            throw SQLiteException("there is at least 1 entry tag that could not be inserted")
        }
        db.close()
        return id
    }

    fun add_entry_tags (entryId : Long, tagList : List<Long>): Int {
        val db : SQLiteDatabase = this.writableDatabase
        val cv = ContentValues()
        var entryTagAdd = 0
        for (tag in tagList){
            cv.put(Column_EntryTag_ID_ENTRY, entryId)
            cv.put(Column_EntryTag_ID_TAG, tag)

            val id_entryTag = db.insert(EntryTag_Table, null, cv)

            if (id_entryTag == -1L){
                db.close()
                throw SQLiteException("the element could not be inserted")
            }
            entryTagAdd += 1
        }
        db.close()
        return entryTagAdd
    }

    fun add_Tag (tag: Tag) : Long{
        val db : SQLiteDatabase = this.writableDatabase
        val cv = ContentValues()
        cv.put(Column_Tag_NAME, tag.get_name())
        cv.put(Column_Tag_INCOME, tag.get_is_income())

        val id = db.insert(Tag_Table, null, cv)
        tag.set_id(id)
        if (id == -1L){
            db.close()
            throw SQLiteException("the element could not be inserted")
        }
        db.close()
        return id
    }

    /**
     * This function get entries from the data base,
     * if startTimeString is "null" and endTimeString "null", returns all entrys
     * also (for efficiency) calculate the balance.
     * the balance is return by the object result
     */
    fun get_entrys (startTimeString: String, endTimeString : String, result : Results): MutableList<Entry> {
        val returnList: MutableList<Entry> = mutableListOf()

        val query : String
        if (startTimeString == "null" && endTimeString == "null"){
            query = "SELECT * FROM $Entry_Table WHERE $Column_Entry_TIME_DELETION IS NULL"
        }
        else{
            query = "SELECT * FROM $Entry_Table " +
                    "WHERE $Column_Entry_TIME_DELETION IS NULL AND $Column_Entry_DATE BETWEEN '$startTimeString' AND '$endTimeString'"
        }

        val db : SQLiteDatabase = this.readableDatabase
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()){
            do {
                val id = cursor.getLong(0)
                val amount = cursor.getDouble(1)
                val income = cursor.getInt(2) == 1
                val timeCreation = cursor.getString(3)
                val date = cursor.getString(4)
                val tagIds = get_entry_tags(id)

                val entry = Entry(id, amount, tagIds, income, timeCreation, date)
                returnList.add(entry)
                result.calculateResult(amount, income)
            }while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        // Sort the list by the get_date() method of each Entry
        returnList.sortBy { it.get_date() }
        return returnList
    }

    /**
     * This function get entries from the data base that have an specific tag,
     * if startTimeString is "null" and endTimeString "null", returns all entrys
     */
    fun get_entrys_by_tag (startTimeString: String, endTimeString : String, tagId: Long, result : Results): MutableList<Entry> {
        val returnList: MutableList<Entry> = mutableListOf()

        val query : String
        if (startTimeString == "null" && endTimeString == "null"){
            query = "SELECT * FROM $Entry_Table " +
                    "JOIN $EntryTag_Table ON $Entry_Table.$Column_Entry_ID = $EntryTag_Table.$Column_EntryTag_ID_ENTRY" +
                    "WHERE $Column_Entry_TIME_DELETION IS NULL AND $EntryTag_Table.$Column_EntryTag_ID_TAG = $tagId"
        }
        else{
            query = "SELECT * FROM $Entry_Table " +
                    "JOIN $EntryTag_Table ON $Entry_Table.$Column_Entry_ID = $EntryTag_Table.$Column_EntryTag_ID_ENTRY" +
                    "WHERE $Column_Entry_TIME_DELETION IS NULL AND $EntryTag_Table.$Column_EntryTag_ID_TAG = $tagId " +
                    " AND $Column_Entry_DATE BETWEEN '$startTimeString' AND '$endTimeString'"
        }

        val db : SQLiteDatabase = this.readableDatabase
        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()){
            do {
                val id = cursor.getLong(0)
                val amount = cursor.getDouble(1)
                val income = cursor.getInt(2) == 1
                val timeCreation = cursor.getString(3)
                val date = cursor.getString(4)
                val tagIds = get_entry_tags(id)

                val entry = Entry(id, amount, tagIds, income, timeCreation, date)
                returnList.add(entry)
                result.calculateResult(amount, income)
            }while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        // Sort the list by the get_date() method of each Entry
        returnList.sortBy { it.get_date() }
        return returnList
    }

    fun get_entry_tags(entryId : Long): List<Long> {
        val tagIds = mutableListOf<Long>()
        val queryTag = "SELECT * FROM $EntryTag_Table WHERE $Column_EntryTag_ID_ENTRY = $entryId"
        val db : SQLiteDatabase = this.readableDatabase
        val cursorTag = db.rawQuery(queryTag, null)
        if (cursorTag.moveToFirst()){
            do{
                val tag = cursorTag.getLong(1)
                tagIds.add(tag)
            }while (cursorTag.moveToNext())
        }
        cursorTag.close()
        return tagIds.toList()
    }

    fun get_all_tag_by_income (isIncome : Boolean) : MutableList<Tag> {
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

                val tag = Tag(id, name, income)
                returnList.add(tag)

            }while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return returnList
    }

    fun get_all_tags () : MutableList<Tag> {
        val returnList: MutableList<Tag> = mutableListOf()

        val query = "SELECT * FROM $Tag_Table " +
                " WHERE $Column_Tag_ID != 1 AND $Column_Tag_TIME_DELETION IS NULL"

        val db : SQLiteDatabase = this.readableDatabase

        val cursor = db.rawQuery(query, null)

        if (cursor.moveToFirst()){
            do {
                val id = cursor.getLong(0)
                val name = cursor.getString(1)
                val income = cursor.getInt(2) == 1

                val tag = Tag(id, name, income)
                returnList.add(tag)

            }while (cursor.moveToNext())
        }

        cursor.close()
        db.close()
        return returnList
    }

    /**
     * this function returns the balance of each tag,
     * if startTimeString == "null" and endTimeString ==  "null"
     * it returns the all time balance of each tag
     */
    fun get_tag_balance (startTimeString: String, endTimeString : String): MutableList<TagBalance>{
        val returnList: MutableList<TagBalance> = mutableListOf()

        val query: String
        if (startTimeString ==  "null" && endTimeString ==  "null"){
            query = "SELECT " +
                    " t.$Column_Tag_ID AS tag_id, " +
                    " t.$Column_Tag_NAME AS tag_name, " +
                    " t.$Column_Tag_INCOME AS is_income, " +
                    " SUM(e.$Column_Entry_AMOUNT) AS total_amount " +
                    " FROM $Entry_Table e " +
                    " JOIN $EntryTag_Table et ON e.$Column_Entry_ID = et.$Column_EntryTag_ID_ENTRY " +
                    " JOIN $Tag_Table t ON et.$Column_EntryTag_ID_TAG = t.$Column_Tag_ID " +
                    " WHERE t.$Column_Tag_ID != 1 AND e.$Column_Entry_TIME_DELETION IS NULL AND t.$Column_Tag_TIME_DELETION IS NULL" +
                    " GROUP BY t.$Column_Tag_ID "
        }
        else{
            query = "SELECT " +
                    " t.$Column_Tag_ID AS tag_id, " +
                    " t.$Column_Tag_NAME AS tag_name, " +
                    " t.$Column_Tag_INCOME AS is_income, " +
                    " SUM(e.$Column_Entry_AMOUNT) AS total_amount " +
                    " FROM $Entry_Table e " +
                    " JOIN $EntryTag_Table et ON e.$Column_Entry_ID = et.$Column_EntryTag_ID_ENTRY " +
                    " JOIN $Tag_Table t ON et.$Column_EntryTag_ID_TAG = t.$Column_Tag_ID " +
                    " WHERE t.$Column_Tag_ID != 1 " +
                    " AND e.$Column_Entry_TIME_DELETION IS NULL " +
                    " AND t.$Column_Tag_TIME_DELETION IS NULL " +
                    " AND e.$Column_Entry_DATE BETWEEN '$startTimeString' AND '$endTimeString'" +
                    " GROUP BY t.$Column_Tag_ID "

        }

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
        val query = "SELECT * FROM $Entry_Table WHERE $Column_Entry_ID = $entryId AND $Column_Entry_TIME_DELETION IS NULL"

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
        val income = cursor.getInt(2) == 1
        val timeCreation = cursor.getString(3)
        val date = cursor.getString(4)
        val tagIds = get_entry_tags(entryId)

        val entry = Entry(id, amount, tagIds, income, timeCreation, date)

        cursor.close()
        db.close()
        return entry
    }

    fun update_Entry (entry: Entry): Long {
        val db : SQLiteDatabase = this.writableDatabase
        val cv = ContentValues()
        val time = LocalDateTime.now()
        val timeUpdate = time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        val id = entry.get_id()

        cv.put(Column_Entry_AMOUNT, entry.get_amount())
        cv.put(Column_Entry_INCOME, entry.get_income())
        cv.put(Column_Entry_DATE, entry.get_date())
        cv.put(Column_Entry_TIME_UPDATE, timeUpdate)


        val affectedRows  = db.update(Entry_Table, cv, "$Column_Entry_ID == $id", null )

        if (affectedRows == 0) {
            // Handle the case where no rows were affected (entry with the given ID not found)
            db.close()
            throw IllegalStateException("No entry found with ID $id")
        }

        val entryTagAdd = add_entry_tags(id, entry.get_tags())
        if (entryTagAdd != entry.get_tags().size){
            db.close()
            throw SQLiteException("there is at least 1 entry tag that could not be inserted")
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

    /**
     * This function gets a tag from the data base,
     * if the tag has been eliminated, the return tag would have tag id = -1
     */
    fun get_Tag (tagId : Long): Tag {
        val query = "SELECT * FROM $Tag_Table WHERE $Column_Tag_ID = $tagId"

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

        if (cursor.getString(3) != null){
            return Tag(-1, "", false)
        }
        val id = cursor.getLong(0)
        val name = cursor.getString(1)
        val income = cursor.getInt(2) == 1

        val tag = Tag(id, name, income)

        cursor.close()
        db.close()
        return tag
    }

    fun update_Tag (tag: Tag): Long {
        val db : SQLiteDatabase = this.writableDatabase
        val cv = ContentValues()
        val id = tag.get_id()

        cv.put(Column_Tag_NAME, tag.get_name())
        cv.put(Column_Tag_INCOME, tag.get_is_income())

        val affectedRows  = db.update(Tag_Table, cv, "$Column_Tag_ID = $id", null )

        if (affectedRows == 0) {
            // Handle the case where no rows were affected (entry with the given ID not found)
            db.close()
            throw IllegalStateException("No entry found with ID $id")
        }

        db.close()
        return id
    }

    fun delete_Tag (tag: Tag): Long {
        val db : SQLiteDatabase = this.writableDatabase
        val cv = ContentValues()

        val time = LocalDateTime.now()
        val timeDeletion = time.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

        cv.put(Column_Tag_TIME_DELETION, timeDeletion)

        val id = tag.get_id()

        val affectedRows  = db.update(Tag_Table, cv, "$Column_Tag_ID = $id", null )

        if (affectedRows == 0) {
            // Handle the case where no rows were affected (entry with the given ID not found)
            db.close()
            throw IllegalStateException("No entry found with ID $id")
        }
        db.close()
        return id
    }

    /*
    fun time (){
        val timestampString = LocalDateTime.now()
        val string = timestampString.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)

        val timestamp = LocalDateTime.parse(string, DateTimeFormatter.ISO_LOCAL_DATE_TIME)



        val startTime: LocalDateTime = LocalDateTime.of(2024, Month.MARCH, 3, 14, 38) // Example start time
        val endTime: LocalDateTime = LocalDateTime.of(2024, Month.MARCH, 3, 14, 43) // Example end time
        // Convert LocalDateTime objects to string representations in the same format as in your database
        val startTimeString = startTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        val endTimeString = endTime.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    }
    */
}