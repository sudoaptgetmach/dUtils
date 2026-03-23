package com.mach.utils.enums

import org.bukkit.GameMode

enum class GameModes(val short: String, val number: String) {
    SURVIVAL("s", "0") {
        override fun gamemode(): GameMode {
            return GameMode.SURVIVAL
        }

    },
    CREATIVE("c", "1") {
        override fun gamemode(): GameMode {
            return GameMode.CREATIVE
        }

    },
    ADVENTURE("a", "2") {
        override fun gamemode(): GameMode {
            return GameMode.ADVENTURE
        }

    },
    SPECTATOR("sp", "3") {
        override fun gamemode(): GameMode {
            return GameMode.SPECTATOR
        }
    };

    abstract fun gamemode(): GameMode

    companion object {
        fun find(input: String): GameMode? {
            val lowerInput = input.lowercase()
            return entries.find {
                it.name.lowercase() == lowerInput ||
                        it.short == lowerInput ||
                        it.number == lowerInput
            }?.gamemode()
        }
    }
}