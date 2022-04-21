package com.example.reminder.data.local.datasource

import android.content.SharedPreferences
import com.example.reminder.data.local.db.dao.ReminderDao
import com.example.reminder.data.model.entity.ReminderEntity
import kotlinx.coroutines.flow.Flow

class ReminderLocalDataSourceImpl(
    private val reminderDao: ReminderDao,
    private val sharedPreferences: SharedPreferences
) : ReminderLocalDataSource {
    override fun getAllReminders(): Flow<List<ReminderEntity>> =
        reminderDao.getAllReminders()

    override suspend fun deleteReminderById(id: Int) =
        reminderDao.deleteReminderById(id)

    override suspend fun addReminder(reminderEntity: ReminderEntity) =
        reminderDao.addReminder(reminderEntity)

    override suspend fun updateReminders(reminderEntityList: List<ReminderEntity>) =
        reminderDao.updateReminders(*reminderEntityList.toTypedArray())

    override suspend fun deleteReminders(reminderEntityList: List<ReminderEntity>) =
        reminderDao.updateReminders(*reminderEntityList.toTypedArray())

}