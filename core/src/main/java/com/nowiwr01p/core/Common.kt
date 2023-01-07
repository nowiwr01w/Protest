package com.nowiwr01p.core

import org.joda.time.DateTime

fun Long.formatToDate(): String {
    val date = DateTime(this)
    val day = if (date.dayOfMonth < 10) "0${date.dayOfMonth}" else "${date.dayOfMonth}"
    val month = if (date.monthOfYear < 10) "0${date.monthOfYear}" else "${date.monthOfYear}"
    return "$day.$month.${date.year} ${date.hourOfDay}:${date.minuteOfHour}"
}