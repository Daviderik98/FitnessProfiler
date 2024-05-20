package com.example.myapplication.domain

class Calculator(
    height: Double,
    weight: Double,
    age: Int,
    restPulse: Int
) {
    val height = height
    val weight = weight
    val age = age
    val restingPulse = restPulse

    var bmi: Int = 0
    var maxPulse: Int = 0
    var hrr: Int = 0

    fun calculateBMI(height: Double, weight: Double) {
        val calculatedBmi = weight / (height * height)
        bmi =  calculatedBmi.toInt()
    }

    fun calculateMaxPulse(age: Int) {
        val max: Double = (210 - (0.5*age))
        maxPulse = max.toInt()
    }

    fun calculateHRR(maxPulse: Int, restPulse: Int) {
        hrr = maxPulse - restPulse
    }
}