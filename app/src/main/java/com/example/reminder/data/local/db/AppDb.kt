package com.example.reminder.data.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.reminder.data.local.db.dao.ReminderDao
import com.example.reminder.data.model.entity.ReminderEntity

@Database(entities = [ReminderEntity::class], version = 1)
abstract class AppDb : RoomDatabase() {
    abstract fun reminderDao(): ReminderDao
}