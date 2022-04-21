package com.example.reminder.data.repository

import com.example.reminder.data.model.entity.ReminderEntity
import kotlinx.coroutines.flow.Flow

interface ReminderRepository {
    fun getAllReminders(): Flow<List<ReminderEntity>>

    suspend fun deleteReminderById(id: Int)

    suspend fun addReminder(reminderEntity: ReminderEntity)

    suspend fun updateReminders(reminderEntityList: List<ReminderEntity>)

    suspend fun deleteReminders(reminderEntityList: List<ReminderEntity>)
}