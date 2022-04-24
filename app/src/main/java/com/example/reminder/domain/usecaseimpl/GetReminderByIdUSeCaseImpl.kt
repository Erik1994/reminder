package com.example.reminder.domain.usecaseimpl

import com.example.reminder.data.model.data.ReminderViewData
import com.example.reminder.data.model.mapper.REMINDER_ENTITY_TO_VIEW_DATA_MAPPER
import com.example.reminder.data.repository.ReminderRepository
import com.example.reminder.domain.usecase.GetReminderByIdUseCase

class GetReminderByIdUSeCaseImpl(
    private val repository: ReminderRepository
) : GetReminderByIdUseCase {
    override suspend fun invoke(id: Int): ReminderViewData? =
        REMINDER_ENTITY_TO_VIEW_DATA_MAPPER.mapIfNotNull(repository.getReminderById(id))

}