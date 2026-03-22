package com.mach.utils.handler

import com.mach.utils.messages.CoreMessages
import org.bukkit.Bukkit.getConsoleSender
import org.bukkit.command.ConsoleCommandSender
import revxrsal.commands.bukkit.actor.BukkitCommandActor
import revxrsal.commands.bukkit.exception.BukkitExceptionHandler
import revxrsal.commands.bukkit.exception.InvalidPlayerException
import revxrsal.commands.exception.MissingArgumentException
import revxrsal.commands.exception.NoPermissionException
import revxrsal.commands.node.ParameterNode

class CommandExceptionHandler : BukkitExceptionHandler() {
    val messages: CoreMessages = CoreMessages()

    override fun onInvalidPlayer(e: InvalidPlayerException?, actor: BukkitCommandActor) {
        if (actor.isConsole) {
            val console: ConsoleCommandSender = getConsoleSender()
            console.sendMessage(messages.invalidPlayer())
        } else {
            actor.asPlayer()!!.sendMessage(messages.invalidPlayer())
        }
    }

    override fun onNoPermission(e: NoPermissionException, actor: BukkitCommandActor) {
        if (actor.isConsole) {
            getConsoleSender().sendMessage(messages.noPermission())
        } else {
            actor.asPlayer()!!.sendMessage(messages.noPermission())
        }
    }

    override fun onMissingArgument(
        e: MissingArgumentException,
        actor: BukkitCommandActor,
        parameter: ParameterNode<BukkitCommandActor?, *>
    ) {
        if (!actor.isConsole && !parameter.command().permission().isExecutableBy(actor)) {
            actor.asPlayer()!!.sendMessage(messages.noPermission())
        } else {
            actor.reply(messages.invalidSyntax(parameter.command().usage(), ""))
        }
    }
}