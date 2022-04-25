package com.example.reminder.ui.util

import android.widget.TextView
import com.example.reminder.R

fun TextView.checkValidation(isValid: (Boolean) -> Unit){
    if (text.toString().isEmpty()) {
        error = (context.getString(R.string.validation_error))
        isValid(false)
    } else isValid(true)
}