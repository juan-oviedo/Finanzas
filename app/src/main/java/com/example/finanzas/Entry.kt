package com.example.finanzas


class Entry (private var id : Long, private var amount : Double, private var tag : Long) {
    constructor():this(-1, 0.0, 1)

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

    fun get_tag (): Long {
        return tag
    }
    fun set_tag (newtag : Long){
        tag = newtag
    }
}

class Tag (private var id : Long, private var name : String){
    constructor():this(-1, "Default")


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
}