package com.example.reminder.ui.features.reminders

import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import com.example.reminder.domain.dispatchers.AppDispatchers
import com.example.reminder.data.model.data.ReminderViewData
import com.example.reminder.domain.usecase.AddReminderUseCase
import com.example.reminder.domain.usecase.DeleteReminderByIdUseCase
import com.example.reminder.domain.usecase.GetRemindersUseCase
import com.example.reminder.ui.common.BaseViewModel
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ReminderVIewModel(
    private val getRemindersUseCase: GetRemindersUseCase,
    private val deleteReminderByIdUseCase: DeleteReminderByIdUseCase,
    private val addReminderUseCase: AddReminderUseCase,
    private val appDispatchers: AppDispatchers,
    workManager: WorkManager
) : BaseViewModel(workManager) {
    private val _getremindersSharedFlow = MutableSharedFlow<List<ReminderViewData>>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val getRemindersSharedFlow: SharedFlow<List<ReminderViewData>> =
        _getremindersSharedFlow.asSharedFlow()

    private suspend fun getAllLocalRemindersFlow(): Flow<List<ReminderViewData>> = withContext(appDispatchers.ioDispatcher) {
        return@withContext getRemindersUseCase()
    }

    fun deleteReminderById(id: String, workerID: String) {
        viewModelScope.launch(appDispatchers.ioDispatcher) {
            cancelEnquedWorkByID(workerID)
            deleteReminderByIdUseCase(id)
        }
    }

    fun getAllLocalReminders() {
        viewModelScope.launch {
            _getremindersSharedFlow.emitAll(getAllLocalRemindersFlow())
        }
    }

    fun addReminder(reminderViewData: ReminderViewData) {
        viewModelScope.launch(appDispatchers.ioDispatcher) {
            val workId = enqueWorkAndReturnId(reminderId = reminderViewData.id, reminderTime = reminderViewData.dateTime)
            reminderViewData.workId = workId
            addReminderUseCase(reminderViewData)
        }
    }
}