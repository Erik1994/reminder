package com.example.reminder.domain.usecaseimpl

import com.example.reminder.data.model.data.ReminderViewData
import com.example.reminder.data.model.mapper.VIEW_DATA_MAPPER_TO_REMINDER_ENTITY
import com.example.reminder.data.repository.ReminderRepository
import com.example.reminder.domain.usecase.AddReminderUseCase

class AddReminderUseCaseImpl(
    private val repository: ReminderRepository
) : AddReminderUseCase {
    override suspend fun invoke(reminder: ReminderViewData) =
        repository.addReminder(VIEW_DATA_MAPPER_TO_REMINDER_ENTITY.map(reminder))
}