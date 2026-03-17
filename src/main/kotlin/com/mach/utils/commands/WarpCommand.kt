package com.mach.utils.commands

import com.mach.utils.messages.CoreMessages
import com.mach.utils.messages.WarpMessages
import com.mach.utils.model.Warps
import revxrsal.commands.annotation.Command
import revxrsal.commands.bukkit.actor.BukkitCommandActor

class WarpCommand {

    private var warps: Warps = Warps()
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

        val player = sender.asPlayer()

        when (args[0]) {
            "set" -> {
                if (!player!!.hasPermission("dutils.warps.set")) {
                    player.sendMessage(coreMessages.noPermission())
                    return
                }
                // warps.addWarp(args[1], player.location, player.world.name, true);
                player.sendMessage(messages.warpAdded(args[1]))
                return
            }
            "delete" -> {
                if (!player!!.hasPermission("dutils.warps.delete")) {
                    player.sendMessage(coreMessages.noPermission())
                    return
                }
                // warps.removeWarp(args[1]);
                player.sendMessage(messages.warpRemoved(args[1]))
                return
            }
            else -> {
                val name = args[0]

                if (name.isEmpty()) {
                    player!!.sendMessage(coreMessages.invalidSyntax("warp", "<warp name>"))
                    return
                }

                if (warps.warpExists(name)) {
                    val location = warps.getWarpLocation(name)

                    player!!.teleport(location)
                    player.sendMessage(messages.teleportedSuccessfully(name))
                }

                player!!.sendMessage(messages.warpNotFound(name))
                return
            }
        }
    }

    @Command("warps")
    fun warps(sender: BukkitCommandActor) {
        var warpList = ""
        val warps = warps.listWarps()

        if (warps.isNotEmpty()) {
            for (warp in warps) {
                warpList += "$warp\n"
            }
        }

        sender.reply(messages.warpList(warpList))
        return
    }
}