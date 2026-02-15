package com.mach.utils.handler

import com.mach.dFramework.interfaces.IConfigurableEnum
import com.mach.dFramework.manager.ConfigManager
import com.mach.utils.Main
import com.mach.utils.enums.CoreConfig
import com.mach.utils.enums.CoreMessages
import kotlin.reflect.KClass

class ConfigHandler(var main: Main) {
    private var _configs: MutableMap<ConfigManager?, KClass<out IConfigurableEnum>?> =
        HashMap()

    fun initConfigs() {
        val configManager = ConfigManager(main, "config.yml")
        val messagesManager = ConfigManager(main, "lang.yml")

        _configs[configManager] = CoreConfig::class
        _configs[messagesManager] = CoreMessages::class
    }

    fun getConfigs(): MutableMap<ConfigManager?, KClass<out IConfigurableEnum>?> {
        return _configs
    }
}