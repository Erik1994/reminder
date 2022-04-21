package com.example.reminder.data.local.db.dao

import androidx.room.*
import com.example.reminder.data.REMINDER_TABLE_NAME
import com.example.reminder.data.model.entity.ReminderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {

    @Query("SELECT * FROM $REMINDER_TABLE_NAME ORDER BY dateTime DESC")
    fun getAllReminders(): Flow<List<ReminderEntity>>

    @Query("DELETE FROM $REMINDER_TABLE_NAME WHERE id = :id")
    suspend fun deleteReminderById(id: Int)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addReminder(reminderEntity: ReminderEntity)

    @Update
    suspend fun updateReminders(vararg reminderEntity: ReminderEntity)

    @Delete
    suspend fun deleteReminders(vararg reminderEntity: ReminderEntity)
}