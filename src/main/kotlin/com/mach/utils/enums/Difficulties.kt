package com.mach.utils.enums

import org.bukkit.Difficulty

enum class Difficulties(val short: String, val number: String, val mode: Difficulty) {
    PEACEFUL("p", "0", Difficulty.PEACEFUL),
    EASY("e", "1", Difficulty.EASY),
    NORMAL("n", "2", Difficulty.NORMAL),
    HARD("h", "3", Difficulty.HARD);

    companion object {
        fun find(input: String): Difficulty? {
            val lowerInput = input.lowercase()
            return Difficulties.entries.find {
                        it.name.lowercase() == lowerInput ||
                        it.short == lowerInput ||
                        it.number == lowerInput
            }?.mode
        }
    }
}