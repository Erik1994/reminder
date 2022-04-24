package com.example.reminder.ui.navigation

import androidx.navigation.NavDirections

sealed class NavigationCommand {
    data class To (val direction: NavDirections): NavigationCommand()
    object Back: NavigationCommand()
}