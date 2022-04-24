package com.example.reminder.di

import com.example.reminder.data.local.di.localModule
import com.example.reminder.domain.di.domainModule
import com.example.reminder.ui.di.featureModule
import com.example.reminder.workers.di.wokerModule

val appModule = listOf(
    localModule,
    domainModule,
    wokerModule,
    featureModule
)