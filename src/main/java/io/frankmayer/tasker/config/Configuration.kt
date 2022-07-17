package io.frankmayer.tasker.config

import java.util.*

data class Configuration(
    val timeZone: String = TimeZone.getDefault().id,
    val tasks: MutableList<Task> = mutableListOf()
)
