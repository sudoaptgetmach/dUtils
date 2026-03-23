package com.mach.utils.messages

import com.mach.utils.enums.Messages
import com.mach.utils.interfaces.MessageAccessor
import net.kyori.adventure.text.Component

class GamemodeMessages : MessageAccessor {
    fun set(gamemode: String): Component = getMessage(
        Messages.GAMEMODE_SET.getPath(),
        "<green>Gamemode changed to <yellow>{gamemode}</yellow>.",
        mapOf(
            "{gamemode}" to gamemode
        )
    )

    fun changed(gamemode: String): Component = getMessage(
        Messages.GAMEMODE_CHANGED.getPath(),
        "<green>Your gamemode was changed to <yellow>{gamemode}</yellow>.",
        mapOf(
            "{gamemode}" to gamemode
        )
    )

    fun invalidGameMode(): Component = getMessage(
        Messages.GAMEMODE_INVALID.getPath(),
        "<red>Invalid gamemode.",
    )
}