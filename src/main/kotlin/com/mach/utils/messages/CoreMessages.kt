package com.mach.utils.messages

import com.mach.utils.enums.Messages
import com.mach.utils.interfaces.MessageAccessor
import net.kyori.adventure.text.Component

class CoreMessages : MessageAccessor {
    fun noPermission(): Component = getMessage(Messages.NO_PERMISSION.getPath(), "")
    fun invalidPlayer(): Component = getMessage(Messages.INVALID_PLAYER.getPath(), "")
    fun invalidSyntax(cmd: String, args: String): Component = getMessage(
        Messages.INVALID_SYNTAX.getPath(),
        "",
        mapOf(
            "{command}" to cmd,
            "{args}" to args
        )
    )
    fun reloadMessage(): Component = getMessage(Messages.CONFIG_RELOADED.getPath(), "<red>Config reloaded.")
}