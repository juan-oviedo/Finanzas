package com.example.finanzas


class Entry (private var id : Long, private var amount : Double, private var tag : Long?, private var income: Boolean) {
    constructor():this(-1, 0.0, 1, true)

    fun get_id (): Long {
        return id
    }
    fun set_id (newid : Long){
        id = newid
    }

    fun get_amount (): Double {
        return amount
    }
    fun set_amount (newamount : Double){
        amount = newamount
    }

    fun get_tag (): Long? {
        return tag
    }
    fun set_tag (newtag : Long){
        tag = newtag
    }

    fun get_income (): Boolean {
        return income
    }
    fun set_income (newincome : Boolean){
        income = newincome
    }
}

class Tag (private var id : Long, private var name : String, private var isIncome: Boolean){
    constructor():this(-1, "Default", true)

    private var isClicked: Boolean = false
    fun get_id (): Long {
        return id
    }
    fun set_id (newid : Long){
        id = newid
    }

    fun get_name (): String {
        return name
    }
    fun set_name(newname : String){
        name = newname
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