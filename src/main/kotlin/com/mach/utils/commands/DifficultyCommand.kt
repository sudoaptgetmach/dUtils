package com.mach.utils.commands

import com.mach.utils.messages.DifficultyMessages
import org.bukkit.Bukkit
import org.bukkit.Difficulty
import org.bukkit.entity.Player
import revxrsal.commands.annotation.Command
import revxrsal.commands.bukkit.annotation.CommandPermission

class DifficultyCommand {
    private val difficultyMessages = DifficultyMessages()

    @Command("difficulty", "diff", "dificuldade")
    @CommandPermission("dutils.difficulty")
    fun difficulty(sender: Player, args: Array<out String>? = null) {
        if (args.isNullOrEmpty() || args.size > 1) {
            return
        }

        val world = Bukkit.getServer().worlds[0]

        when (args[0]) {
            "p", "peaceful", "0" -> world.difficulty = Difficulty.PEACEFUL
            "e", "easy", "1" -> world.difficulty = Difficulty.EASY
            "n", "normal", "2" -> world.difficulty = Difficulty.NORMAL
            "h", "hard", "3" -> world.difficulty = Difficulty.HARD
            else -> {
                sender.sendMessage(
                    difficultyMessages.invalidDifficulty(
                        "peaceful (p, 0), easy (e, 1), normal (n, 2), hard (h, 3)")
                )
                return
            }
        }

        sender.sendMessage(
            difficultyMessages.difficultyChanged(args[0])
        )
        return
    }
}