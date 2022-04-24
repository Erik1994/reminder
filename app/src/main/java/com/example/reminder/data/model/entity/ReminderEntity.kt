package com.example.reminder.data.model.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.reminder.data.REMINDER_TABLE_NAME
import java.util.*

@Entity(tableName = REMINDER_TABLE_NAME)
data class ReminderEntity (
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val title: String,
    val description: String,
    val dateTime: Long,
    var workId: String,
    var isComplited: Boolean = false
)