package com.example.reminder.domain.usecase

import com.example.reminder.data.model.data.ReminderViewData

interface AddReminderUseCase {
    suspend operator fun invoke(reminder: ReminderViewData)
}