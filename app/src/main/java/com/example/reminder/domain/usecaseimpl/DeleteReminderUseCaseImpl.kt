package com.example.reminder.domain.usecaseimpl

import com.example.reminder.data.model.data.ReminderViewData
import com.example.reminder.data.model.mapper.VIEW_DATA_MAPPER_TO_REMINDER_ENTITY
import com.example.reminder.data.repository.ReminderRepository
import com.example.reminder.domain.usecase.DeleteReminderUseCase

class DeleteReminderUseCaseImpl(
    private val repository: ReminderRepository
) : DeleteReminderUseCase {
    override suspend fun invoke(reminders: List<ReminderViewData>) =
        repository.deleteReminders(VIEW_DATA_MAPPER_TO_REMINDER_ENTITY.map(reminders))
}