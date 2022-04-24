package com.example.reminder.domain.usecaseimpl

import com.example.reminder.data.repository.ReminderRepository
import com.example.reminder.domain.usecase.DeleteReminderByIdUseCase

class DeleteReminderByIdUseCaseImpl(
    private val repository: ReminderRepository
) : DeleteReminderByIdUseCase {
    override suspend fun invoke(id: String) =
        repository.deleteReminderById(id)
}