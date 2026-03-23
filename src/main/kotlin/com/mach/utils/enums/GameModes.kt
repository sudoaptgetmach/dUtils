package com.mach.utils.enums

import org.bukkit.GameMode

enum class GameModes(val short: String, val number: String, val mode: GameMode) {
    SURVIVAL("s", "0", GameMode.SURVIVAL),
    CREATIVE("c", "1", GameMode.CREATIVE),
    ADVENTURE("a", "2", GameMode.ADVENTURE),
    SPECTATOR("sp", "3", GameMode.SPECTATOR);

    companion object {
        fun find(input: String): GameMode? {
            val lowerInput = input.lowercase()
            return entries.find {
                        it.name.lowercase() == lowerInput ||
                        it.short == lowerInput ||
                        it.number == lowerInput
            }?.mode
        }
    }
}