package com.example.reminder.data.model.data

data class ReminderViewData (
    val id: Int = 0,
    val title: String,
    val description: String,
    val dateTime: Long,
    var isComplited: Boolean = false
)