package executor

import config.Task

data class TaskExeData(val index: Int, var nextExecutionTime: Long, val task: Task)
