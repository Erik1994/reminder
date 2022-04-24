package com.example.reminder.data.model.data

import com.example.reminder.ui.extensions.emptyString
import java.util.*

data class ReminderViewData (
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val dateTime: Long,
    var workId: String = emptyString(),
    var isComplited: Boolean = false
)