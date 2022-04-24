package com.example.reminder.data.repository

import com.example.reminder.data.local.datasource.ReminderLocalDataSource
import com.example.reminder.data.model.entity.ReminderEntity
import kotlinx.coroutines.flow.Flow

class ReminderRepositoryImpl(private val reminderLocalDataSource: ReminderLocalDataSource): ReminderRepository {
    override fun getAllReminders(): Flow<List<ReminderEntity>> =
        reminderLocalDataSource.getAllReminders()

    override suspend fun deleteReminderById(id: String) =
        reminderLocalDataSource.deleteReminderById(id)

    override suspend fun addReminder(reminderEntity: ReminderEntity) =
        reminderLocalDataSource.addReminder(reminderEntity)

    override suspend fun updateReminders(reminder: ReminderEntity) =
        reminderLocalDataSource.updateReminders(reminder)

    override suspend fun deleteReminders(reminderEntityList: List<ReminderEntity>) =
        reminderLocalDataSource.deleteReminders(reminderEntityList)

    override suspend fun updateComplitionById(id: String) =
        reminderLocalDataSource.updateComplitionById(id)

    override suspend fun getReminderById(id: String): ReminderEntity? =
        reminderLocalDataSource.getReminderById(id)
}