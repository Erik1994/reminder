package com.example.reminder.data.model.mapper

import com.example.reminder.data.model.data.ReminderViewData
import com.example.reminder.data.model.entity.ReminderEntity

val REMINDER_ENTITY_TO_VIEW_DATA_MAPPER = object : Mapper<ReminderEntity, ReminderViewData> {
    override fun map(source: ReminderEntity): ReminderViewData = ReminderViewData(
        id = source.id,
        title = source.title,
        description = source.description,
        dateTime = source.dateTime,
        workId = source.workId,
        isComplited = source.isComplited
    )
}

val VIEW_DATA_MAPPER_TO_REMINDER_ENTITY = object : Mapper<ReminderViewData, ReminderEntity> {
    override fun map(source: ReminderViewData): ReminderEntity = ReminderEntity(
        id = source.id,
        title = source.title,
        description = source.description,
        dateTime = source.dateTime,
        workId = source.workId,
        isComplited = source.isComplited
    )
}