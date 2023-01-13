package com.nowiwr01p.core.extenstion

import org.joda.time.DateTime
import java.time.LocalDate
import java.time.LocalTime

fun Long.formatToDate() = DateTime(this).let {
    "${it.dayOfMonth.formatWithZeros()}.${it.monthOfYear.formatWithZeros()}.${it.yearOfCentury}"
}

fun Long.formatToTime() = DateTime(this).let {
    "${it.hourOfDay.formatWithZeros()}:${it.minuteOfHour.formatWithZeros()}"
}

fun LocalDate.formatToDate() = "${dayOfMonth.formatWithZeros()}.${monthValue.formatWithZeros()}.${year}"

fun LocalTime.formatToTime() = "${hour.formatWithZeros()}:${minute.formatWithZeros()}"

fun Long.formatToDateTime() = formatToDate() + " " + formatToTime()

fun Int.formatWithZeros() = if (this < 10) "0$this" else toString()