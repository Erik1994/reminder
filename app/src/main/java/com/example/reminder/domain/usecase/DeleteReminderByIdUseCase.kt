package com.example.reminder.domain.usecase

interface DeleteReminderByIdUseCase {
    suspend operator fun invoke(id: Int)
}