package io.frankmayer.tasker

import config.TaskerConfigManager
import executor.TaskExecutor
import org.bukkit.Bukkit
import org.bukkit.plugin.java.JavaPlugin
import java.util.logging.Level

class TaskerBukkit : JavaPlugin() {
    private var config: TaskerConfigManager? = null
    private var executor: TaskExecutor? = null

    override fun onEnable() {
        val dir = dataFolder
        if (!dir.exists()) {
            dir.mkdir()
        }

        val log = { level: Level, msg: String ->
            Bukkit.getScheduler().callSyncMethod(this) {
                Bukkit.getLogger().log(level, msg)
            }
        }

        val exec = { command: String ->
            Bukkit.getScheduler().callSyncMethod(this) {
                server.dispatchCommand(
                    server.consoleSender,
                    command
                )
            }
        }

        config = TaskerConfigManager(dir.absolutePath)
        executor = TaskExecutor(config!!, exec, log)
    }

    override fun onDisable() {
        config?.exportConfig()
        executor?.shutdown()
    }
}
