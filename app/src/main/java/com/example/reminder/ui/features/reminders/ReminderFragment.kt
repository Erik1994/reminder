package com.example.reminder.ui.features.reminders

import com.example.reminder.R
import com.example.reminder.ui.common.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ReminderFragment: BaseFragment(R.layout.fragment_reminder) {
    override val viewModel: ReminderVIewModel by viewModel()
}