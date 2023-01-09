package com.nowiwr01p.core.extenstion

import org.joda.time.DateTime

fun Long.formatToDate(): String {
    val date = DateTime(this)
    return "${date.dayOfMonth.formatWithZeros()}.${date.monthOfYear.formatWithZeros()}.${date.yearOfCentury}" +
            " ${date.hourOfDay.formatWithZeros()}:${date.minuteOfHour.formatWithZeros()}"
}

fun Int.formatWithZeros() = if (this < 10) "0$this" else toString()