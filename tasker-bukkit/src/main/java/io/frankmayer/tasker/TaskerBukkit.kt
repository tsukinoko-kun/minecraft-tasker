package io.frankmayer.tasker

import io.frankmayer.tasker.common.TaskerConfigManager
import org.bukkit.plugin.java.JavaPlugin

class TaskerBukkit : JavaPlugin() {
    private var config: TaskerConfigManager? = null

    override fun onEnable() {
        val dir = dataFolder
        if (!dir.exists()) {
            dir.mkdir()
        }

        config = TaskerConfigManager(dir.absolutePath)
    }

    override fun onDisable() {
        config?.exportConfig()
    }
}
