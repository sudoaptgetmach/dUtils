package com.mach.utils.commands

import com.mach.utils.messages.CoreMessages
import com.mach.utils.messages.WarpMessages
import com.mach.utils.model.Warp
import com.mach.utils.service.WarpService
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import revxrsal.commands.annotation.Command
import revxrsal.commands.bukkit.actor.BukkitCommandActor
import revxrsal.commands.bukkit.annotation.CommandPermission

class WarpCommand(private val service: WarpService) {
    private var coreMessages = CoreMessages()
    private var messages = WarpMessages()

    @Command("warp")
    @CommandPermission("dutils.warp")
    fun warp(sender: BukkitCommandActor, args: Array<out String>?) {
        if (args.isNullOrEmpty() || args.size > 2) {
            return
        }

        val player = sender.asPlayer() ?: return

        val name = args[0]

        if (name.isEmpty() || args.size > 1) {
            player.sendMessage(coreMessages.invalidSyntax("warp", "<name>"))
            return
        }

        val warp = service.find(name)

        if (warp == null) {
            player.sendMessage(messages.warpNotFound(name))
            return
        }

        player.teleport(warp.location)
        player.sendMessage(messages.teleportedSuccessfully(name))
    }

    @Command("setwarp")
    @CommandPermission("dutils.warps.set")
    fun set(sender: BukkitCommandActor, args: Array<out String>?) {
        if (args.isNullOrEmpty() || args.size > 1) {
            return
        }

        val player = sender.asPlayer()

        if (player == null) {
            sender.reply(coreMessages.noPermission())
            return
        }

        val warp = Warp(args[0], player.location, true)

        if (!service.save(warp)) {
            sender.reply(Component.text("Unable to set warp ${warp.name}", NamedTextColor.RED))
            return
        }

        sender.reply(messages.warpAdded(warp.name))
        return
    }

    @Command("delwarp")
    @CommandPermission("dutils.warps.delete")
    fun delete(sender: BukkitCommandActor, args: Array<out String>?) {
        if (args.isNullOrEmpty() || args.size > 1) {
            return
        }

        val player = sender.asPlayer()
        val name = args[0]

        if (player == null) {
            sender.reply(coreMessages.noPermission())
            return
        }

        if (!service.exists(name)) {
            sender.reply(messages.warpNotFound(name))
            return
        }

        if (!service.delete(name)) {
            sender.reply(Component.text("Unable to delete warp $name", NamedTextColor.RED))
            return
        }

        sender.reply(messages.warpRemoved(name))
    }

    @Command("warps")
    fun warps(sender: BukkitCommandActor) {
        val warpList = service.findAll()
            .map { it.name }
            .sorted()
            .joinToString("\n")

        val response =
        if (warpList.isEmpty()) {
            messages.warpEmptyList()
        } else {
            messages.warpList(warpList)
        }

        sender.reply(response)
    }
}