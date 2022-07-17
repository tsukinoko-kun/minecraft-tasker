package io.frankmayer.tasker.executor

import io.frankmayer.tasker.config.Task

data class TaskExeData(val index: Int, var nextExecutionTime: Long, val task: Task)
