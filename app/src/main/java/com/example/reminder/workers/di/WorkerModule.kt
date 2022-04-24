package com.example.reminder.workers.di

import androidx.work.WorkManager
import org.koin.dsl.module

val wokerModule = module {
    single {
        WorkManager.getInstance(get())
    }
}