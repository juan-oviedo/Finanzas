package com.example.finanzas

class Results{
    private var income : Double = 0.0
    private var cost : Double = 0.0
    private var result : Double = 0.0

    fun calculateResult (value: Double, isIncome: Boolean){
        if (isIncome){
            income += value
            result += value
        }
        else{
            cost += value
            result -= value
        }
    }

    fun getIncome(): Double {
        return income
    }
    fun getCost(): Double {
        return cost
    }
    fun getResult(): Double {
        return result
    }
}