package com.example.reminder.ui.common

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.reminder.ui.navigation.NavigationCommand
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

abstract class BaseFragment(layoutId: Int): Fragment(layoutId) {
    protected abstract val viewModel: BaseViewModel

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeNavigation()
    }

    private fun observeNavigation() {
        viewModel.navigationFlow
            .onEach { command ->
                command?.let {
                    viewModel.setNavigationNull()
                    when (it) {
                        is NavigationCommand.Back -> {
                            findNavController().navigateUp()
                        }
                        is NavigationCommand.To -> findNavController().navigate(it.direction)
                    }
                }
            }.launchIn(viewLifecycleOwner.lifecycleScope)
    }
}