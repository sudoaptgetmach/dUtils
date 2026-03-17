package com.mach.utils.messages

import com.mach.utils.enums.Messages
import com.mach.utils.interfaces.MessageAccessor
import net.kyori.adventure.text.Component

class DifficultyMessages : MessageAccessor {
    fun difficultyChanged(difficulty: String): Component = getMessage(
        Messages.DIFFICULTY_CHANGED.getPath(),
        "",
        mapOf(
            "{difficulty}" to difficulty
        )
    )

    fun invalidDifficulty(difficultyList: String): Component = getMessage(
        Messages.DIFFICULTY_CHANGED.getPath(),
        "",
        mapOf(
            "{difficulties}" to difficultyList
        )
    )
}