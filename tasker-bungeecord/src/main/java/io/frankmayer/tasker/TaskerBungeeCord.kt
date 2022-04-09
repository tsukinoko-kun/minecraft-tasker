package io.frankmayer.tasker

import config.Task
import config.TaskerConfigManager
import executor.TaskExecutor
import net.md_5.bungee.api.plugin.Plugin
import java.util.logging.Level

class TaskerBungeeCord : Plugin() {
    private var config: TaskerConfigManager? = null
    private var executor: TaskExecutor? = null

    override fun onEnable() {
        val dir = dataFolder
        if (!dir.exists()) {
            dir.mkdir()
        }

        val log = { level: Level, msg: String ->
            proxy.logger.log(level, msg)
        }

        val exec = { command: String ->
            proxy.pluginManager.dispatchCommand(proxy.console, command)
        }

        config = TaskerConfigManager(dir.absolutePath, Task("* * * * *", "alert Hello from Tasker!"))
        executor = TaskExecutor(config!!, exec, log)
    }

    override fun onDisable() {
        // Plugin shutdown logic
    }
}
