package com.mach.utils.messages

import net.kyori.adventure.text.minimessage.MiniMessage

object MessageSanitizer {
    private val miniMessage = MiniMessage.miniMessage()

    /**
     * Removes MiniMessage tags from user input before persistence/lookup.
     */
    fun canonicalizeWarpName(value: String): String = miniMessage.stripTags(value).trim()
}

