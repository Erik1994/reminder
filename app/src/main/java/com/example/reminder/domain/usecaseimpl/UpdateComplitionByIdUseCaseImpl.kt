package com.example.reminder.domain.usecaseimpl

import com.example.reminder.data.repository.ReminderRepository
import com.example.reminder.domain.usecase.UpdateComplitionByIdUseCase

class UpdateComplitionByIdUseCaseImpl(
    private val repository: ReminderRepository
) : UpdateComplitionByIdUseCase {
    override suspend fun invoke(id: Int) =
        repository.updateComplitionById(id)
}