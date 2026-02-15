package com.mach.utils.enums

import com.mach.dFramework.interfaces.IConfigurableEnum
import org.bukkit.configuration.file.FileConfiguration

enum class CoreConfig : IConfigurableEnum {
    ;

    private var _config: FileConfiguration? = null
    private var _path: String = ""

    override fun getConfig(): FileConfiguration {
        return _config!!
    }

    override fun getPath(): String {
        return _path
    }

    override fun init(config: FileConfiguration) {
        this._config = config
    }
}