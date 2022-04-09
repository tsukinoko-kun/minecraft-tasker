package executor

import config.Task
import config.TaskerConfigManager
import org.quartz.CronScheduleBuilder.cronSchedule
import org.quartz.CronTrigger
import org.quartz.JobBuilder
import org.quartz.JobDetail
import org.quartz.Scheduler
import org.quartz.TriggerBuilder.newTrigger
import org.quartz.impl.StdSchedulerFactory
import java.util.*
import java.util.logging.Level

class TaskExecutor(
    config: TaskerConfigManager,
    commandExecutor: (String) -> Any,
    private val log: (Level, String) -> Any
) {
    companion object {
        internal var commandExecutor: ((String) -> Any)? = null
        internal var log: ((Level, String) -> Any)? = null
    }

    private val schedulers = mutableListOf<Scheduler>()
    private val timeZone: TimeZone = config.timeZone

    init {
        TaskExecutor.commandExecutor = commandExecutor
        TaskExecutor.log = log
        for (task in config.tasks) {
            addTrigger(task)
        }
    }

    private fun addTrigger(task: Task) {
        try {
            val index: Int = schedulers.count()
            val job: JobDetail = JobBuilder.newJob(CommandJob::class.java)
                .withIdentity(index.toString(), "tasker")
                .setJobData(CommandJob.createJobDataMap(task))
                .build()

            val trigger: CronTrigger = newTrigger()
                .withIdentity(index.toString(), "tasker")
                .withSchedule(cronSchedule(task.cron).inTimeZone(timeZone))
                .startNow()
                .build()

            val scheduler: Scheduler = StdSchedulerFactory().scheduler
            scheduler.start()
            scheduler.scheduleJob(job, trigger)

            schedulers.add(scheduler)
        } catch (e: Exception) {
            log(Level.ALL, "Failed to add task: '${task.cron}', ''${task.command}' Error: ${e.message}")
        }
    }

    fun shutdown() {
        for (scheduler in schedulers) {
            scheduler.shutdown()
        }
    }
}
