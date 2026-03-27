package com.mach.utils.commands

import com.mach.dFramework.core.context.FrameworkContext
import com.mach.utils.enums.Messages
import com.mach.utils.service.EnderchestService
import org.bukkit.entity.Player
import revxrsal.commands.annotation.Command
import revxrsal.commands.bukkit.annotation.CommandPermission

@Suppress("unused")
class EnderchestCommand(val ctx: FrameworkContext) {

    val service = EnderchestService(ctx)

    @Command("enderchest", "ec")
    @CommandPermission("dutils.enderchest.usage")
    fun run(
        sender: Player
    ) {
        service.openSelector(sender)
        ctx.messaging.actionbar(
            sender,
            Messages.ENDERCHEST_SELECTOR_OPEN.getPath(),
            "<green>Enderchest aberto com sucesso!"
        )
    }
}