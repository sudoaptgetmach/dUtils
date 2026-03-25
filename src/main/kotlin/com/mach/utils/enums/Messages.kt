package com.mach.utils.enums

import com.mach.dFramework.core.messages.MessageKey

@Suppress("unused")
enum class Messages(private val _path: String) : MessageKey {
    NO_PERMISSION("messages.no_permission"),

    INVALID_PLAYER("messages.invalid_player"),
    INVALID_SYNTAX("messages.invalid_syntax"),
    CONFIG_RELOADED("messages.config_reloaded"),

    DIFFICULTY_CHANGED("messages.difficulty.changed"),
    DIFFICULTY_INVALID("messages.difficulty.invalid"),

    WARP_ADDED("messages.warps.added"),
    WARP_REMOVED("messages.warps.removed"),
    WARP_LIST("messages.warps.list"),
    WARP_EMPTY_LIST("messages.warps.list_empty"),
    WARP_NOT_FOUND("messages.warps.not_found"),
    WARP_TELEPORTED_SUCCESSFULLY("messages.warps.teleported_successfully"),

    GAMEMODE_SET("messages.gamemode.set"),
    GAMEMODE_CHANGED("messages.gamemode.changed"),
    GAMEMODE_INVALID("messages.gamemode.invalid"),

    FLY_TOGGLE("messages.fly.toggle"),
    FLY_TOGGLE_OTHER("messages.fly.toggle_other"),
    FLY_DISABLE("messages.fly.disable"),
    FLY_DISABLE_OTHER("messages.fly.disable_other"),
    ;

    override fun getPath(): String = _path
}