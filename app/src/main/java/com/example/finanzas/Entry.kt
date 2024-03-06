package com.example.finanzas


class Entry (private var id : Long, private var amount : Double, private var tags : List<Long>,
             private var income: Boolean, private var timeCreation : String, private var date : String) {
    fun get_id (): Long {
        return id
    }
    fun set_id (newId : Long){
        id = newId
    }

    fun get_amount (): Double {
        return amount
    }
    fun set_amount (newAmount : Double){
        amount = newAmount
    }

    fun get_tags (): List<Long> {
        return tags
    }
    fun set_tags (newTags : List<Long>){
        tags = newTags
    }

    fun get_income (): Boolean {
        return income
    }
    fun set_income (newIncome : Boolean){
        income = newIncome
    }

    fun get_timeCreation (): String{
        return timeCreation
    }
    fun set_timeCreation (newTimeCreation : String){
        timeCreation = newTimeCreation
    }

    fun get_date (): String{
        return date
    }
    fun set_date (newDate : String){
        date = newDate
    }
}

class Tag (private var id : Long, private var name : String, private var isIncome: Boolean){

    private var isClicked: Boolean = false
    fun get_id (): Long {
        return id
    }
    fun set_id (newId : Long){
        id = newId
    }

    fun get_name (): String {
        return name
    }
    fun set_name(newName : String){
        name = newName
    }

    fun get_is_income (): Boolean {
        return isIncome
    }
    fun set_is_income(newIsIncome : Boolean){
        isIncome = newIsIncome
    }

    fun change_is_clicked(){
        isClicked = !isClicked
    }

    fun get_is_clicked(): Boolean {
        return isClicked
    }
}

class TagBalance (private var id : Long, private var name : String, private var isIncome: Boolean, private var amount: Double){
    constructor():this(-1, "Default", true, 0.0)

    fun get_id (): Long {
        return id
    }
    fun set_id (newId : Long){
        id = newId
    }

    fun get_name (): String {
        return name
    }
    fun set_name(newName : String){
        name = newName
    }

    fun get_is_income (): Boolean {
        return isIncome
    }
    fun set_is_income(newIsIncome : Boolean){
        isIncome = newIsIncome
    }

    fun get_amount (): Double {
        return  amount
    }

    fun set_amount (newamount: Double){
        amount = newamount
    }
}

