package com.mach.utils.messages

import com.mach.utils.enums.Messages
import com.mach.utils.interfaces.MessageAccessor
import net.kyori.adventure.text.Component

class FlyMessages : MessageAccessor {
    fun toggled(): Component = getMessage(Messages.FLY_TOGGLE.getPath(), "<green>Flying toggled.")
    fun toggledOther(player: String): Component = getMessage(Messages.FLY_TOGGLE_OTHER.getPath(),
        "<green>Flying toggled for <yellow>{player}</yellow>.",
        mapOf(
            "{player}" to player
        )
    )

    fun disabled(): Component = getMessage(Messages.FLY_DISABLE.getPath(),
        "<red>Flying toggled."
    )

    fun disabledOther(player: String): Component = getMessage(Messages.FLY_DISABLE_OTHER.getPath(),
        "<red>Flying toggled for {player}.",
        mapOf(
            "{player}" to player
        )
    )
}