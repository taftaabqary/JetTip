package com.althaaf.jettip.utils

fun calculateTotalTip(billValue: Double, tipPercentage: Float): Double {
    val percentage = (tipPercentage * 100).toInt()
    val totalTip = billValue * percentage / 100
    return totalTip
}

fun calculateTotalPerPerson(billValue: Double, tipPercentage: Float, splitBy: Int): Double {
    val totalTip = calculateTotalTip(billValue, tipPercentage) + billValue
    return  totalTip / splitBy
}
