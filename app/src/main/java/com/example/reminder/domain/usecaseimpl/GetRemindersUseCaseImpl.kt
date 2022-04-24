package com.example.reminder.domain.usecaseimpl

import com.example.reminder.data.model.data.ReminderViewData
import com.example.reminder.data.model.mapper.REMINDER_ENTITY_TO_VIEW_DATA_MAPPER
import com.example.reminder.data.repository.ReminderRepository
import com.example.reminder.domain.usecase.GetRemindersUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetRemindersUseCaseImpl(
    private val repository: ReminderRepository
) : GetRemindersUseCase {
    override fun invoke(): Flow<List<ReminderViewData>> =
        repository.getAllReminders().map {
            REMINDER_ENTITY_TO_VIEW_DATA_MAPPER.map(it)
        }
}