package com.example.reminder.ui.extensions

import java.text.SimpleDateFormat
import java.util.*

fun emptyString() = ""

fun dateTimeFormat() = "dd.MM.yy, HH:mm"

fun Long.formatDate(pattern: String): String =
    SimpleDateFormat(pattern, Locale.getDefault()).format(this)

fun String.stringDateToMilis(pattern: String): Long =
    SimpleDateFormat(dateTimeFormat(), Locale.getDefault()).parse(this)?.time ?: System.currentTimeMillis()