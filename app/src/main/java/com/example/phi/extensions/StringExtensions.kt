package com.example.phi.extensions

import java.text.NumberFormat
import java.util.*

fun Int.convertInToMoney(): String {
    val localeBr = Locale("pt", "BR")
    val numberFormat = NumberFormat.getCurrencyInstance(localeBr)
    return numberFormat.format(this)
}