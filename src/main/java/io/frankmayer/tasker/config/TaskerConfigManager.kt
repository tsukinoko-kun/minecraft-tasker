package io.frankmayer.tasker.config

import com.google.gson.GsonBuilder
import java.io.File
import java.util.*

class TaskerConfigManager(location: String, defaultTask: Task) {
    private val fileName = "$location/config.json"
    private val file = File(fileName)
    private val gson = GsonBuilder().setPrettyPrinting().create()
    private var config = Configuration()

    init {
        if (file.exists() && file.canRead()) {
            val configContent = file.readText()
            config = gson.fromJson(configContent, Configuration::class.java)
        } else {
            config.tasks.add(defaultTask)
        }

        exportConfig()
    }

    fun exportConfig() {
        file.writeText(gson.toJson(config, Configuration::class.java))
    }

    val tasks
        get(): List<Task> {
            return config.tasks
        }

    val timeZone
        get(): TimeZone {
            return TimeZone.getTimeZone(config.timeZone)
        }
}
