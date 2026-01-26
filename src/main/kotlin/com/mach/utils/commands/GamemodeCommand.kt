package com.mach.utils.commands

import com.mach.utils.Main
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class GamemodeCommand(val main: Main) : CommandExecutor {
    override fun onCommand(
        sender: CommandSender,
        cmd: Command,
        string: String,
        args: Array<out String>?
    ): Boolean {
        if (sender !is Player || !sender.hasPermission("dutils.gamemode")) {
            sender.sendMessage(
                Component.text(main.config.getString("messages.no-permission").toString())
                    .color(NamedTextColor.RED)
            )
            return false
        }

        if (args.isNullOrEmpty() || args.size > 2) {
            sender.sendMessage(
                Component.text("Utilize: /gamemode <gamemode> [user]")
                    .color(NamedTextColor.RED)
            )

            return false
        }

        val gamemode = args[0]
        val playerName = if (args.size > 1) args[1] else sender.name

        val player = Bukkit.getPlayer(playerName)

        if (!playerName.isEmpty() && player == null) {
            sender.sendMessage(
                Component.text(main.config.getString("messages.invalid-player").toString())
                    .color(NamedTextColor.RED)
            )

            return false
        }

        when (gamemode) {
            "survival", "s", "0" -> player!!.gameMode = GameMode.SURVIVAL
            "creative", "c", "1" -> player!!.gameMode = GameMode.CREATIVE
            "adventure", "a", "2" -> player!!.gameMode = GameMode.ADVENTURE
            "spectator", "sp", "3" -> player!!.gameMode = GameMode.SPECTATOR
        }

        player!!.sendMessage(
            Component.text("Gamemode changed to " + player.gameMode.toString() + ".").color(NamedTextColor.GREEN)
        )
        return false
    }
}