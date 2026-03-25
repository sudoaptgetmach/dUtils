package com.mach.utils.commands

import com.mach.utils.messages.CoreMessages
import com.mach.utils.messages.FlyMessages
import org.bukkit.entity.Player
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Named
import revxrsal.commands.annotation.Optional
import revxrsal.commands.bukkit.annotation.CommandPermission
import revxrsal.commands.bukkit.parameters.EntitySelector

class FlyCommand {
    private val core = CoreMessages()
    private val messages = FlyMessages()
    
    @Command("fly", "voar")
    @CommandPermission("dutils.fly.usage")
    fun run(
        sender: Player,
        @Optional @Named("player")
        targetInput: EntitySelector<Player>?
    ) {
        val targets = targetInput ?: listOf(sender)
        val isSelf = targets.size == 1 && targets.first() == sender

        if (!isSelf && !sender.hasPermission("dutils.fly.other")) {
            sender.sendMessage(core.noPermission())
            return
        }

        targets.forEach { target ->
            val newState = !target.allowFlight
            target.allowFlight = newState
            target.isFlying = newState
            if (target.isFlying) {
                target.sendMessage(messages.toggled())
            } else {
                target.sendMessage(messages.disabled())
            }
        }

        if (!isSelf) {
            val feedbackName = if (targets.size == 1) targets.first().name else "todos os jogadores (${targets.size})"
            if (targets.first().isFlying) {
                sender.sendMessage(messages.toggledOther(feedbackName))
            } else {
                sender.sendMessage(messages.disabledOther(feedbackName))
            }
        }
    }
}