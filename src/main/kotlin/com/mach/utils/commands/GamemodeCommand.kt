package com.mach.utils.commands

import com.mach.utils.enums.GameModes
import com.mach.utils.messages.GamemodeMessages
import org.bukkit.entity.Player
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Optional
import revxrsal.commands.annotation.Suggest
import revxrsal.commands.bukkit.annotation.CommandPermission

class GamemodeCommand {
    private val messages = GamemodeMessages()

    @Command("gm", "gamemode")
    @CommandPermission("dutils.gamemode")
    fun run(sender: Player,
            @Suggest("sp", "s", "c", "a")
            gameMode: String,
            @Optional player: Player = sender) {
        if (gameMode.isEmpty()) {
            return
        }

        val gamemode = GameModes.find(gameMode)

        if (gamemode == null) {
            sender.sendMessage(messages.invalidGameMode())
            return
        }

        player.gameMode = gamemode
        player.sendMessage(
            messages.changed(player.gameMode.toString())
        )
    }
}