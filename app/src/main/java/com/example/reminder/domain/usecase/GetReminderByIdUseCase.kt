package com.example.reminder.domain.usecase

import com.example.reminder.data.model.data.ReminderViewData

interface GetReminderByIdUseCase {
    suspend operator fun invoke(id: Int): ReminderViewData?
}