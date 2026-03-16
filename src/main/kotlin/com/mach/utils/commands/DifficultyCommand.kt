package com.mach.utils.commands

import com.mach.utils.enums.CoreMessages
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.Difficulty
import org.bukkit.entity.Player
import revxrsal.commands.annotation.Command

class DifficultyCommand {
    @Command("difficulty", "diff", "dificuldade")
    fun difficulty(sender: Player, args: Array<out String>? = null) {
        if (!sender.hasPermission("dutils.difficulty")) {
            sender.sendMessage(
                Component.text(CoreMessages.NO_PERMISSION.get())
                    .color(NamedTextColor.RED)
            )
            return
        }

        if (args.isNullOrEmpty() || args.size > 1) {
            sender.sendMessage(
                Component.text("Utilize: /difficulty <difficulty>")
                    .color(NamedTextColor.RED)
            )
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
                    Component.text(CoreMessages.DIFFICULTY_INVALID.get()).color(NamedTextColor.RED)
                )
                return
            }
        }

        sender.sendMessage(
            Component.text(CoreMessages.DIFFICULTY_CHANGED.get(
                mapOf(
                    "player" to sender.name,
                    "difficulty" to world.difficulty.name
                )
            ).toString()).color(NamedTextColor.GREEN)
        )
    }
}