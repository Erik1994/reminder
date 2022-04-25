package com.example.reminder.ui.common

import androidx.lifecycle.ViewModel
import androidx.navigation.NavDirections
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.example.reminder.ui.navigation.NavigationCommand
import com.example.reminder.workers.REMINDER_ID_KEY
import com.example.reminder.workers.ReminderWorker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.*
import java.util.concurrent.TimeUnit

abstract class BaseViewModel(private val workManager: WorkManager) : ViewModel() {
    private val _navigationFlow = MutableStateFlow<NavigationCommand?>(null)
    val navigationFlow = _navigationFlow.asStateFlow()

    fun navigate(direction: NavDirections) {
        _navigationFlow.value = NavigationCommand.To(direction)
    }

    fun navigateBack() {
        _navigationFlow.value = NavigationCommand.Back
    }

    fun setNavigationNull() {
        _navigationFlow.value = null
    }

    protected fun enqueWorkAndReturnId(reminderTime: Long, reminderId: String): String {
        val time = (reminderTime - System.currentTimeMillis()) / 1000
        val request = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInputData(createInputData(reminderId))
            .setInitialDelay(time, TimeUnit.SECONDS)
            .build()
        workManager.enqueue(request)
        return request.stringId
    }

    protected fun cancelEnquedWorkByID(workId: String) =
        workManager.cancelWorkById(UUID.fromString(workId))

    private fun createInputData(id: String): Data =
        Data.Builder().putString(REMINDER_ID_KEY, id).build()

}