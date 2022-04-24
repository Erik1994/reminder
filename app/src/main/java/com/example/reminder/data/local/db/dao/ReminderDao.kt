package com.example.reminder.data.local.db.dao

import androidx.room.*
import com.example.reminder.data.REMINDER_TABLE_NAME
import com.example.reminder.data.model.entity.ReminderEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ReminderDao {

    @Query("SELECT * FROM $REMINDER_TABLE_NAME ORDER BY dateTime ASC")
    fun getAllReminders(): Flow<List<ReminderEntity>>

    @Query("DELETE FROM $REMINDER_TABLE_NAME WHERE id = :id")
    suspend fun deleteReminderById(id: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addReminder(reminderEntity: ReminderEntity)

    @Query("UPDATE $REMINDER_TABLE_NAME SET isComplited = 1 WHERE id = :id")
    suspend fun updateComplitionById(id: String)

    @Query("SELECT * FROM $REMINDER_TABLE_NAME WHERE id = :id")
    suspend fun getReminderById(id: String): ReminderEntity?

    @Update
    suspend fun updateReminders(vararg reminderEntity: ReminderEntity)

    @Delete
    suspend fun deleteReminders(vararg reminderEntity: ReminderEntity)
}