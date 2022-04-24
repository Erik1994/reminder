package com.example.reminder.ui.di

import com.example.reminder.ui.features.addeditreminder.AddEditReminderViewModel
import com.example.reminder.ui.features.reminders.ReminderVIewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val featureModule = module {
    viewModel {
        ReminderVIewModel(
            getRemindersUseCase = get(),
            deleteReminderByIdUseCase = get(),
            addReminderUseCase = get(),
            appDispatchers = get(),
            workManager = get()
        )
    }

    viewModel {
        AddEditReminderViewModel(
            addReminderUseCase = get(),
            updateReminderUseCase = get(),
            getReminderByIdUseCase = get(),
            appDispatchers = get(),
            workManager = get()
        )
    }
}