package com.mach.utils.commands

import com.mach.utils.Main
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.Difficulty
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender

class DifficultyCommand(val main: Main) : CommandExecutor {
    override fun onCommand(
        sender: CommandSender,
        cmd: Command,
        string: String,
        args: Array<out String>?
    ): Boolean {
        if (!sender.hasPermission("dutils.difficulty")) {
            sender.sendMessage(
                Component.text(main.config.getString("messages.no-permission").toString())
                    .color(NamedTextColor.RED)
            )
            return false
        }

        if (args.isNullOrEmpty() || args.size > 1) {
            sender.sendMessage(
                Component.text("Utilize: /difficulty <difficulty>")
                    .color(NamedTextColor.RED)
            )

            return false
        }

        val world = Bukkit.getServer().worlds[0]

         when (args[0]) {
            "p", "peaceful", "0" -> world.difficulty = Difficulty.PEACEFUL
            "e", "easy", "1" -> world.difficulty = Difficulty.EASY
            "n", "normal", "2" -> world.difficulty = Difficulty.NORMAL
            "h", "hard", "3" -> world.difficulty = Difficulty.HARD
        }

        sender.sendMessage(
            Component.text("Difficulty changed to " + world.difficulty.toString() + ".").color(NamedTextColor.GREEN)
        )

        return false
    }
}