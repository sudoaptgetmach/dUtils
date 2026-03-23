package com.mach.utils.handler

import com.mach.dFramework.context.FrameworkLogger
import com.mach.utils.messages.CoreMessages
import revxrsal.commands.bukkit.actor.BukkitCommandActor
import revxrsal.commands.bukkit.exception.*
import revxrsal.commands.exception.MissingArgumentException
import revxrsal.commands.exception.NoPermissionException
import revxrsal.commands.node.ParameterNode

class CommandExceptionHandler(private val logger: FrameworkLogger) : BukkitExceptionHandler() {
    val messages: CoreMessages = CoreMessages()

    override fun onInvalidPlayer(e: InvalidPlayerException?, actor: BukkitCommandActor) {
        actor.reply(messages.invalidPlayer())
    }

    override fun onMalformedEntitySelector(e: MalformedEntitySelectorException, actor: BukkitCommandActor) {
        actor.reply(messages.invalidPlayer())
        logger.warn("Malformed entity: ${e.errorMessage()}")
    }

    override fun onEmptyEntitySelector(e: EmptyEntitySelectorException?, actor: BukkitCommandActor) {
        actor.reply(messages.invalidPlayer())
    }

    override fun onSenderNotPlayer(e: SenderNotPlayerException?, actor: BukkitCommandActor) {
        actor.reply(messages.noPermission())
    }

    override fun onNoPermission(e: NoPermissionException, actor: BukkitCommandActor) {
        actor.reply(messages.noPermission())
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