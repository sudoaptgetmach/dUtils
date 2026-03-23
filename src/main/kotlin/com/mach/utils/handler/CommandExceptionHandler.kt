package com.mach.utils.handler

import com.mach.dFramework.context.FrameworkContext
import com.mach.utils.messages.CoreMessages
import org.bukkit.Bukkit.getConsoleSender
import org.bukkit.command.ConsoleCommandSender
import revxrsal.commands.bukkit.actor.BukkitCommandActor
import revxrsal.commands.bukkit.exception.*
import revxrsal.commands.exception.MissingArgumentException
import revxrsal.commands.exception.NoPermissionException
import revxrsal.commands.node.ParameterNode

class CommandExceptionHandler(private val ctx: FrameworkContext) : BukkitExceptionHandler() {
    val messages: CoreMessages = CoreMessages()

    override fun onInvalidPlayer(e: InvalidPlayerException?, actor: BukkitCommandActor) {
        if (actor.isConsole) {
            val console: ConsoleCommandSender = getConsoleSender()
            console.sendMessage(messages.invalidPlayer())
        } else {
            actor.asPlayer()!!.sendMessage(messages.invalidPlayer())
        }
    }

    override fun onMalformedEntitySelector(e: MalformedEntitySelectorException, actor: BukkitCommandActor) {
        actor.reply(messages.invalidPlayer())
        ctx.logger.warn("Malformed entity: ${e.errorMessage()}")
    }

    override fun onEmptyEntitySelector(e: EmptyEntitySelectorException?, actor: BukkitCommandActor) {
        actor.reply(messages.invalidPlayer())
    }

    override fun onSenderNotPlayer(e: SenderNotPlayerException?, actor: BukkitCommandActor) {
        actor.reply(messages.noPermission())
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