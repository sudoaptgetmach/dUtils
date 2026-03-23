package com.mach.utils.messages

import com.mach.utils.enums.Messages
import com.mach.utils.interfaces.MessageAccessor
import net.kyori.adventure.text.Component

class DifficultyMessages : MessageAccessor {
    fun difficultyChanged(difficulty: String): Component = getMessage(
        Messages.DIFFICULTY_CHANGED.getPath(),
        "<green>Difficulty changed to {difficulty}.",
        mapOf(
            "{difficulty}" to difficulty
        )
    )

    fun invalidDifficulty(difficultyList: String): Component = getMessage(
        Messages.DIFFICULTY_INVALID.getPath(),
        "<red>Invalid difficulty. List: {difficulties}",
        mapOf(
            "{difficulties}" to difficultyList
        )
    )
}