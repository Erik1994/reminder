package com.example.reminder.data.repository

import com.example.reminder.data.local.datasource.ReminderLocalDataSource
import com.example.reminder.data.model.entity.ReminderEntity
import kotlinx.coroutines.flow.Flow

class ReminderRepositoryImpl(private val reminderLocalDataSource: ReminderLocalDataSource): ReminderRepository {
    override fun getAllReminders(): Flow<List<ReminderEntity>> =
        reminderLocalDataSource.getAllReminders()

    override suspend fun deleteReminderById(id: Int) =
        reminderLocalDataSource.deleteReminderById(id)

    override suspend fun addReminder(reminderEntity: ReminderEntity) =
        reminderLocalDataSource.addReminder(reminderEntity)

    override suspend fun updateReminders(reminderEntityList: List<ReminderEntity>) =
        reminderLocalDataSource.updateReminders(reminderEntityList)

    override suspend fun deleteReminders(reminderEntityList: List<ReminderEntity>) =
        reminderLocalDataSource.deleteReminders(reminderEntityList)
}