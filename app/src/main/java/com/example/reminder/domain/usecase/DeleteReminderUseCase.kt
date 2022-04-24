package com.example.reminder.domain.usecase

import com.example.reminder.data.model.data.ReminderViewData

interface DeleteReminderUseCase {
    suspend operator fun invoke(reminders: List<ReminderViewData>)
}