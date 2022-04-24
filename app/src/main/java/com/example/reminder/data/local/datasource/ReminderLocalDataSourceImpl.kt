package com.example.reminder.data.local.datasource

import com.example.reminder.data.local.db.dao.ReminderDao
import com.example.reminder.data.model.entity.ReminderEntity
import kotlinx.coroutines.flow.Flow

class ReminderLocalDataSourceImpl(
    private val reminderDao: ReminderDao
) : ReminderLocalDataSource {
    override fun getAllReminders(): Flow<List<ReminderEntity>> =
        reminderDao.getAllReminders()

    override suspend fun deleteReminderById(id: Int) =
        reminderDao.deleteReminderById(id)

    override suspend fun addReminder(reminderEntity: ReminderEntity) =
        reminderDao.addReminder(reminderEntity)

    override suspend fun updateReminders(reminder: ReminderEntity) =
        reminderDao.updateReminders(reminder)

    override suspend fun deleteReminders(reminderEntityList: List<ReminderEntity>) =
        reminderDao.updateReminders(*reminderEntityList.toTypedArray())

    override suspend fun updateComplitionById(id: Int)  =
        reminderDao.updateComplitionById(id)

    override suspend fun getReminderById(id: Int): ReminderEntity? =
        reminderDao.getReminderById(id)
}