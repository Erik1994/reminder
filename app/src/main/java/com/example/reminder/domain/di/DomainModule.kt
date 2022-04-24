package com.example.reminder.domain.di

import com.example.reminder.domain.dispatchers.AppDispatchers
import com.example.reminder.domain.usecase.*
import com.example.reminder.domain.usecaseimpl.*
import org.koin.dsl.module

val domainModule = module {
    factory<AddReminderUseCase> { AddReminderUseCaseImpl(repository = get()) }
    factory<DeleteReminderUseCase> { DeleteReminderUseCaseImpl(repository = get()) }
    factory<DeleteReminderByIdUseCase> { DeleteReminderByIdUseCaseImpl(repository = get()) }
    factory<GetRemindersUseCase> { GetRemindersUseCaseImpl(repository = get()) }
    factory<UpdateReminderUseCase> { UpdateRemindersUseCaseImpl(repository = get()) }
    factory<UpdateComplitionByIdUseCase> { UpdateComplitionByIdUseCaseImpl(repository = get()) }
    factory <GetReminderByIdUseCase> { GetReminderByIdUSeCaseImpl(repository = get()) }
    factory { AppDispatchers() }
}