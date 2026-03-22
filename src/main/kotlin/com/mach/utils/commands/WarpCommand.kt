package com.mach.utils.commands

import com.mach.utils.messages.CoreMessages
import com.mach.utils.messages.WarpMessages
import com.mach.utils.model.Warp
import com.mach.utils.service.WarpService
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import revxrsal.commands.annotation.Command
import revxrsal.commands.bukkit.actor.BukkitCommandActor

class WarpCommand(private val service: WarpService) {
    private var coreMessages = CoreMessages()
    private var messages = WarpMessages()

    @Command("warp")
    fun warp(sender: BukkitCommandActor, args: Array<out String>?) {
        if (args.isNullOrEmpty() || args.size > 2) {
            sender.reply(coreMessages.invalidSyntax("warp", "<warp name>"))
            return
        }

        if (!sender.isPlayer) {
            sender.reply(coreMessages.noPermission())
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
    fun set(sender: BukkitCommandActor, args: Array<out String>?) {
        if (args.isNullOrEmpty() || args.size > 1) {
            sender.reply(coreMessages.invalidSyntax("setwarp", "<warp name>"))
            return
        }

        val player = sender.asPlayer()

        if (player == null || !player.hasPermission("dutils.warps.set")) {
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
    fun delete(sender: BukkitCommandActor, args: Array<out String>?) {
        if (args.isNullOrEmpty() || args.size > 1) {
            sender.reply(coreMessages.invalidSyntax("delwarp", "<warp name>"))
            return
        }

        val player = sender.asPlayer()
        val name = args[0]

        if (player == null) {
            sender.reply(coreMessages.noPermission())
            return
        }

        if (!player.hasPermission("dutils.warps.delete")) {
            player.sendMessage(coreMessages.noPermission())
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
        var warpList = ""
        val warps = service.findAll()

        if (warps.isNotEmpty()) {
            for (warp in warps) {
                warpList += "${warp.name}\n"
            }
        }

        sender.reply(messages.warpList(warpList))
    }
}