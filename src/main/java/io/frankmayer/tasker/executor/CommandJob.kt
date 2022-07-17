package io.frankmayer.tasker.executor

import io.frankmayer.tasker.config.Task
import org.quartz.Job
import org.quartz.JobDataMap
import org.quartz.JobExecutionContext
import java.util.logging.Level

class CommandJob : Job {
    companion object {
        fun createJobDataMap(task: Task): JobDataMap {
            val jobDataMap = JobDataMap()
            jobDataMap["command"] = task.command
            return jobDataMap
        }
    }

    override fun execute(context: JobExecutionContext?) {
        val command = context?.jobDetail?.jobDataMap?.getString("command")
        if (command != null) {
            TaskExecutor.log?.invoke(Level.INFO, "Tasker executes '$command'")
            TaskExecutor.commandExecutor?.invoke(command)
        }
    }
}
