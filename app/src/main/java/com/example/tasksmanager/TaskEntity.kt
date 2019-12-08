package com.example.tasksmanager

import java.util.*

class TaskEntity(
    val id: Int?,
    var title: String,
    var content: String,
    var status: TaskStatusEnum,
    var priority: TaskPriorityEnum,
    var validFrom: Date
)