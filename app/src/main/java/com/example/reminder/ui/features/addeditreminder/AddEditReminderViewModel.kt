package com.example.reminder.ui.features.addeditreminder

import androidx.lifecycle.viewModelScope
import androidx.work.WorkManager
import com.example.reminder.data.model.data.ReminderViewData
import com.example.reminder.domain.dispatchers.AppDispatchers
import com.example.reminder.domain.usecase.AddReminderUseCase
import com.example.reminder.domain.usecase.GetReminderByIdUseCase
import com.example.reminder.domain.usecase.UpdateReminderUseCase
import com.example.reminder.ui.common.BaseViewModel
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AddEditReminderViewModel(
    private val getReminderByIdUseCase: GetReminderByIdUseCase,
    private val addReminderUseCase: AddReminderUseCase,
    private val updateReminderUseCase: UpdateReminderUseCase,
    private val appDispatchers: AppDispatchers,
    workManager: WorkManager
): BaseViewModel(workManager) {
    private val _reminderSharedFlow = MutableSharedFlow<ReminderViewData?>(
        replay = 1,
        onBufferOverflow = BufferOverflow.DROP_OLDEST
    )
    val reminderSharedFlow: SharedFlow<ReminderViewData?> = _reminderSharedFlow

    private suspend fun getReminderViewDataById(id: String) = withContext(appDispatchers.ioDispatcher) {
        return@withContext getReminderByIdUseCase(id)
    }

    fun getReminder(id: String) {
        viewModelScope.launch {
            _reminderSharedFlow.emit(getReminderViewDataById(id))
        }
    }

    fun updateReminder(reminderViewData: ReminderViewData) {
        viewModelScope.launch(appDispatchers.ioDispatcher) {
            if (!reminderViewData.isComplited) {
                val workId = enqueWorkAndReturnId(reminderId = reminderViewData.id, reminderTime = reminderViewData.dateTime)
                reminderViewData.workId = workId
            }
            updateReminderUseCase(reminderViewData)
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