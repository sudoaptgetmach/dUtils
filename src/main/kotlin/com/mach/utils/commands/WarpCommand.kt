package com.mach.utils.commands

import com.mach.utils.messages.WarpMessages
import com.mach.utils.messages.MessageSanitizer
import com.mach.utils.model.Warp
import com.mach.utils.service.WarpService
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player
import revxrsal.commands.annotation.Command
import revxrsal.commands.bukkit.actor.BukkitCommandActor
import revxrsal.commands.bukkit.annotation.CommandPermission

class WarpCommand(private val service: WarpService) {
    private var messages = WarpMessages()

    @Command("warp")
    @CommandPermission("dutils.warp")
    fun warp(sender: Player, name: String) {
        val sanitizedName = MessageSanitizer.canonicalizeWarpName(name)
        if (sanitizedName.isEmpty()) {
            return
        }
        val warp = service.find(sanitizedName)

        if (warp == null) {
            sender.sendMessage(messages.warpNotFound(sanitizedName))
            return
        }

        sender.teleport(warp.location)
        sender.sendMessage(messages.teleportedSuccessfully(sanitizedName))
    }

    @Command("setwarp")
    @CommandPermission("dutils.warps.set")
    fun set(sender: Player, name: String) {
        val sanitizedName = MessageSanitizer.canonicalizeWarpName(name)
        if (sanitizedName.isEmpty()) {
            return
        }

        val warp = Warp(sanitizedName, sender.location, true)

        if (!service.save(warp)) {
            sender.sendMessage(Component.text("Unable to set warp ${warp.name}", NamedTextColor.RED))
            return
        }

        sender.sendMessage(messages.warpAdded(warp.name))
    }

    @Command("delwarp")
    @CommandPermission("dutils.warps.delete")
    fun delete(player: Player, name: String) {
        val sanitizedName = MessageSanitizer.canonicalizeWarpName(name)
        if (sanitizedName.isEmpty()) {
            return
        }

        if (!service.exists(sanitizedName)) {
            player.sendMessage(messages.warpNotFound(sanitizedName))
            return
        }

        if (!service.delete(sanitizedName)) {
            player.sendMessage(Component.text("Unable to delete warp $sanitizedName", NamedTextColor.RED))
            return
        }

        player.sendMessage(messages.warpRemoved(sanitizedName))
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