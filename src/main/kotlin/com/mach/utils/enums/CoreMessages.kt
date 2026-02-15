package com.mach.utils.enums

import com.mach.dFramework.interfaces.IConfigurableEnum
import org.bukkit.configuration.file.FileConfiguration

@Suppress("unused")
enum class CoreMessages(private val _path: String) : IConfigurableEnum {
    NO_PERMISSION("no_permission"),
    INVALID_PLAYER("invalid_player");

    private var _config: FileConfiguration? = null

    override fun getPath(): String = _path

    override fun getConfig(): FileConfiguration = _config!!

    override fun init(config: FileConfiguration) {
        this._config = config
    }
}