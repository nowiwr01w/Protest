package com.nowiwr01p.core.extenstion

import org.joda.time.DateTime
import org.joda.time.DateTimeZone
import java.util.*

/**
 * DEFAULT
 */
fun Long.formatToDateTime() = formatToDate() + " " + formatToTime()

fun Long.formatToDate() = DateTime(this).let {
    "${it.dayOfMonth.formatWithZeros()}.${it.monthOfYear.formatWithZeros()}.${it.yearOfCentury}"
}

fun Long.formatToTime() = DateTime(this).let {
    "${it.hourOfDay.formatWithZeros()}:${it.minuteOfHour.formatWithZeros()}"
}

/**
 * DATE-TIME PICKER
 */
fun Long.getFromPicker() = DateTime(
    this,
    DateTimeZone.forTimeZone(TimeZone.getTimeZone("GMT"))
).let {
    "${it.dayOfMonth.formatWithZeros()}.${it.monthOfYear.formatWithZeros()}.${it.yearOfCentury} " +
            "${it.hourOfDay.formatWithZeros()}:${it.minuteOfHour.formatWithZeros()}"
}


fun Int.formatWithZeros() = if (this < 10) "0$this" else toString()