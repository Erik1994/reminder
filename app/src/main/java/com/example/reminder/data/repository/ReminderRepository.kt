package com.example.reminder.data.repository

import com.example.reminder.data.model.entity.ReminderEntity
import kotlinx.coroutines.flow.Flow

interface ReminderRepository {
    fun getAllReminders(): Flow<List<ReminderEntity>>

    suspend fun deleteReminderById(id: String)

    suspend fun addReminder(reminderEntity: ReminderEntity)

    suspend fun updateReminders(reminder: ReminderEntity)

    suspend fun deleteReminders(reminderEntityList: List<ReminderEntity>)

    suspend fun updateComplitionById(id: String)

    suspend fun getReminderById(id: String): ReminderEntity?
}