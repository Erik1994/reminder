package com.example.reminder.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.reminder.data.REMINDER_TABLE_NAME

@Entity(tableName = REMINDER_TABLE_NAME)
data class ReminderEntity (
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val title: String,
    val description: String,
    val dateTime: Long,
    var isComplited: Boolean = false
)