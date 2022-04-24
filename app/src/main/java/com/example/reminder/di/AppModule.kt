package com.example.reminder.di

import com.example.reminder.data.local.di.localModule
import com.example.reminder.domain.di.domainModule
import com.example.reminder.ui.di.featureModule

val appModule = listOf(
    localModule,
    domainModule,
    featureModule
)