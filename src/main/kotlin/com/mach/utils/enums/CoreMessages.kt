package com.mach.utils.enums

import com.mach.dFramework.interfaces.IConfigurableEnum
import org.bukkit.configuration.file.FileConfiguration

@Suppress("unused")
enum class CoreMessages(private val _path: String) : IConfigurableEnum {
    NO_PERMISSION("no_permission"),

    INVALID_PLAYER("invalid_player"),
    INVALID_SYNTAX("invalid_syntax"),

    DIFFICULTY_CHANGED("difficulty.changed"),
    DIFFICULTY_INVALID("difficulty.invalid"),

    WARP_ADDED("warps.added"),
    WARP_REMOVED("warps.removed"),
    WARP_NOT_FOUND("warps.not_found"),
    WARP_TELEPORTED_SUCCESSFULLY("warps.teleported_successfully");

    private var _config: FileConfiguration? = null

    override fun getPath(): String = _path

    override fun getConfig(): FileConfiguration = _config!!

    override fun init(config: FileConfiguration) {
        this._config = config
    }
}