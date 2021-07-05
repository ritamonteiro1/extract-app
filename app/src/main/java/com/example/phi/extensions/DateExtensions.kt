package com.example.phi.extensions

import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun Date.convertDateToString(): String{
    val localeBr = Locale("pt", "BR")
    val dateFormatDayAndMonth = "dd/MM"
    val dateFormat: DateFormat = SimpleDateFormat(dateFormatDayAndMonth, localeBr)
    return dateFormat.format(this)
}