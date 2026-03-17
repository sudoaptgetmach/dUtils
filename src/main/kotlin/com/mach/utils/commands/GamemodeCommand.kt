package com.mach.utils.commands

import com.mach.utils.messages.CoreMessages
import com.mach.utils.messages.GamemodeMessages
import org.bukkit.Bukkit
import org.bukkit.GameMode
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class GamemodeCommand : CommandExecutor {

    private val messages = CoreMessages()
    private val gamemodeMessages = GamemodeMessages()

    override fun onCommand(
        sender: CommandSender,
        cmd: Command,
        string: String,
        args: Array<out String>
    ): Boolean {
        if (sender !is Player || !sender.hasPermission("dutils.gamemode")) {
            sender.sendMessage(
                messages.noPermission()
            )
            return false
        }

        if (args.isEmpty() || args.size > 2) {
            sender.sendMessage(
                messages.invalidSyntax("gamemode", "<gamemode> [user]")
            )

            return false
        }

        val playerName = if (args.size > 1) args[1] else sender.name

        val player = Bukkit.getPlayer(playerName)

        if (!playerName.isEmpty() && player == null) {
            sender.sendMessage(
                messages.invalidPlayer()
            )
            return false
        }

        when (args[0]) {
            "survival", "s", "0" -> player!!.gameMode = GameMode.SURVIVAL
            "creative", "c", "1" -> player!!.gameMode = GameMode.CREATIVE
            "adventure", "a", "2" -> player!!.gameMode = GameMode.ADVENTURE
            "spectator", "sp", "3" -> player!!.gameMode = GameMode.SPECTATOR
        }

        player!!.sendMessage(
            gamemodeMessages.changed(player.gameMode.toString())
        )
        return false
    }
}