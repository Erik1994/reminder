package com.example.reminder.data.local.di

import android.content.Context
import androidx.room.Room
import com.example.reminder.data.APP_DATABASE
import com.example.reminder.data.PREFS_NAME
import com.example.reminder.data.local.datasource.ReminderLocalDataSource
import com.example.reminder.data.local.datasource.ReminderLocalDataSourceImpl
import com.example.reminder.data.local.db.AppDb
import com.example.reminder.data.repository.ReminderRepository
import com.example.reminder.data.repository.ReminderRepositoryImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val localModule = module {
    single {
        androidContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    single {
        Room.databaseBuilder(
            get(),
            AppDb::class.java,
            APP_DATABASE
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideReminderDao(database: AppDb) = database.reminderDao()
    single { provideReminderDao(database = get()) }

    factory<ReminderLocalDataSource> {
        ReminderLocalDataSourceImpl(
            reminderDao = get()
        )
    }

    /*
    *
    * Inject repository here instead of creating separate module for repositories beacause
    * use single repository for whole app
    *
     */
    factory<ReminderRepository> {
        ReminderRepositoryImpl(
            reminderLocalDataSource = get()
        )
    }
}