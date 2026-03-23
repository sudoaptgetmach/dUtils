package com.mach.utils.commands

import com.mach.dFramework.context.FrameworkContext
import com.mach.utils.messages.CoreMessages
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import revxrsal.commands.annotation.Command
import revxrsal.commands.annotation.Subcommand
import revxrsal.commands.bukkit.actor.BukkitCommandActor
import revxrsal.commands.bukkit.annotation.CommandPermission

@Command("dutils")
@CommandPermission("dutils.admin")
@Suppress("unused")
class DUtilsCommand(private val ctx: FrameworkContext) {

    private val messages = CoreMessages()

    @Subcommand("reload")
    @CommandPermission("dutils.admin.reload")
    fun reload(sender: BukkitCommandActor) {
        val registeredFiles = ctx.configs.registeredFiles()

        if (registeredFiles.isEmpty()) {
            sender.reply(Component.text(
                "Nenhuma configuração encontrada", NamedTextColor.RED
            ))
            return
        }

        for (config in registeredFiles) {
            ctx.configs.reload(config)
        }
        sender.reply(messages.reloadMessage())
    }
}