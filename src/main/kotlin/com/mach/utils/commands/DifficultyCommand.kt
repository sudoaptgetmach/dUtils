package com.mach.utils.commands

import com.mach.utils.enums.Difficulties
import com.mach.utils.messages.DifficultyMessages
import org.bukkit.entity.Player
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Named
import revxrsal.commands.annotation.Suggest
import revxrsal.commands.bukkit.annotation.CommandPermission

class DifficultyCommand {
    private val messages = DifficultyMessages()

    @Command("difficulty", "diff", "dificuldade")
    @CommandPermission("dutils.difficulty")
    fun run(sender: Player,
            @Named("difficulty")
            @Suggest("peaceful", "easy", "normal", "hard")
            difficultyInput: String) {
        if (difficultyInput.isEmpty()) {
            return
        }

        val world = sender.world
        val diff = Difficulties.find(difficultyInput)

        if (diff == null) {
            sender.sendMessage(messages.invalidDifficulty("""
                Peaceful (p, 0)
                Easy (e, 1)
                Normal (n, 2)
                Hard (h, 3)
            """.trimIndent()))
            return
        }

        world.difficulty = diff
        sender.sendMessage(messages.difficultyChanged(world.difficulty.toString()))
    }
}