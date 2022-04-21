package com.example.reminder.ui.features.addeditreminder

import com.example.reminder.R
import com.example.reminder.ui.common.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class AddEditReminderFragment : BaseFragment(R.layout.fragment_add_edit_reminder) {
    override val viewModel: AddEditReminderViewModel by viewModel()
}