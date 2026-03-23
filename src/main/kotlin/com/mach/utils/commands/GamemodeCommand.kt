package com.mach.utils.commands

import com.mach.utils.enums.GameModes
import com.mach.utils.messages.CoreMessages
import com.mach.utils.messages.GamemodeMessages
import org.bukkit.entity.Player
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Named
import revxrsal.commands.annotation.Optional
import revxrsal.commands.annotation.Suggest
import revxrsal.commands.bukkit.annotation.CommandPermission
import revxrsal.commands.bukkit.parameters.EntitySelector

@Suppress("UNCHECKED_CAST")
class GamemodeCommand {
    private val core = CoreMessages()
    private val messages = GamemodeMessages()

    @Command("gm", "gamemode")
    @CommandPermission("dutils.gamemode")
    fun run(
        sender: Player,
        @Suggest("sp", "s", "c", "a") @Named("gamemode")
        gameMode: String,
        @Optional @Named("player")
        targetInput: EntitySelector<Player>?
    ) {
        val mode = GameModes.find(gameMode)
        if (mode == null) {
            sender.sendMessage(messages.invalidGameMode())
            return
        }

        val isSelfTarget = targetInput.isNullOrEmpty() || targetInput.singleOrNull() == sender

        if (isSelfTarget) {
            sender.gameMode = mode
            sender.sendMessage(messages.set(mode.toString()))
            return
        }

        if (!sender.hasPermission("dutils.gamemode.other")) {
            sender.sendMessage(core.noPermission())
            return
        }

        sender.sendMessage(messages.set(mode.toString()))

        targetInput.forEach { target ->
            target.gameMode = mode
            target.sendMessage(messages.changed(mode.toString()))
        }
    }
}