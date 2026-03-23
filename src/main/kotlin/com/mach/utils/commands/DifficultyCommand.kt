package com.mach.utils.commands

import com.mach.utils.messages.DifficultyMessages
import org.bukkit.Difficulty
import org.bukkit.entity.Player
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Named
import revxrsal.commands.bukkit.annotation.CommandPermission

class DifficultyCommand {
    private val messages = DifficultyMessages()

    @Command("difficulty", "diff", "dificuldade")
    @CommandPermission("dutils.difficulty")
    fun run(sender: Player, @Named("difficulty") difficultyInput: String) {
        if (difficultyInput.isEmpty()) {
            return
        }

        val world = sender.world

        when (difficultyInput) {
            "p", "peaceful", "0" -> world.difficulty = Difficulty.PEACEFUL
            "e", "easy", "1" -> world.difficulty = Difficulty.EASY
            "n", "normal", "2" -> world.difficulty = Difficulty.NORMAL
            "h", "hard", "3" -> world.difficulty = Difficulty.HARD
            else -> {
                sender.sendMessage(
                    messages.invalidDifficulty("peaceful (p, 0), easy (e, 1), normal (n, 2), hard (h, 3)")
                )
                return
            }
        }

        sender.sendMessage(messages.difficultyChanged(world.difficulty.toString()))
    }
}