package com.example.reminder.ui.common

import androidx.fragment.app.Fragment

abstract class BaseFragment(layoutId: Int): Fragment(layoutId) {
    protected abstract val viewModel: BaseViewModel
}