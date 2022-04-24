package com.example.reminder.domain.usecase

import com.example.reminder.data.model.data.ReminderViewData
import kotlinx.coroutines.flow.Flow

interface GetRemindersUseCase {
    operator fun invoke(): Flow<List<ReminderViewData>>
}