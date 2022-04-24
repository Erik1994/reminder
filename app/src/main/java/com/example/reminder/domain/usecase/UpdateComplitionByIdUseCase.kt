package com.example.reminder.domain.usecase

interface UpdateComplitionByIdUseCase {
    suspend operator fun invoke(id: String)
}