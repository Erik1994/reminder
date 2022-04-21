package com.example.reminder.ui.extensions

import java.text.SimpleDateFormat
import java.util.*

fun emptyString() = ""

fun Long.formatDate(pattern: String): String =
    SimpleDateFormat(pattern, Locale.getDefault()).format(this)