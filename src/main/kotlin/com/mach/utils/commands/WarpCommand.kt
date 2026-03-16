package com.mach.utils.commands

import com.mach.utils.enums.CoreMessages
import com.mach.utils.model.Warps
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.md_5.bungee.api.chat.BaseComponent
import revxrsal.commands.annotation.Command
import revxrsal.commands.bukkit.actor.BukkitCommandActor


class WarpCommand {

    private var warps: Warps = Warps();

    @Command("warp")
    fun warp(sender: BukkitCommandActor, args: Array<out String>? = null) {
        if (args.isNullOrEmpty() || args.size > 1) {
            CoreMessages.DIFFICULTY_CHANGED.get(
                mapOf(
                    "%command%" to "warp",
                    "%args%" to "<warp name>"
                ))?.let { sender.error(it) };
        }

        val player = sender.asPlayer();

        when (args?.get(0)) {
            "set" -> {
                if (!player!!.hasPermission("dutils.warps.set")) {
                    player.sendMessage(Component.text(CoreMessages.NO_PERMISSION.get()).color(NamedTextColor.RED))
                    return;
                }

                warps.addWarp(args[1], player.location, player.world.name, true);
            }
            "delete" -> {
                if (!player!!.hasPermission("dutils.warps.delete")) {
                    player.sendMessage(Component.text(CoreMessages.NO_PERMISSION.get()).color(NamedTextColor.RED))
                    return;
                }
                warps.removeWarp(args[1]);
            }
            else -> {
                val name = args?.get(0)

                if (warps.warpExists(name.toString())) {
                    val location = warps.getWarpLocation(name.toString());

                    player!!.teleport(location)
                    player.sendMessage(Component.text(CoreMessages.WARP_TELEPORTED_SUCCESSFULLY.get(
                        mapOf(
                            "%name%" to name,
                        )
                    ).toString()).color(NamedTextColor.GREEN))
                }

                player!!.sendMessage(Component.text(CoreMessages.WARP_NOT_FOUND.get(
                    mapOf(
                        "%name%" to name,
                    )
                ).toString()).color(NamedTextColor.RED))
                return
            }
        }
    }

    @Command("warps")
    fun warps(sender: BukkitCommandActor) {
        var warpList = "";
        var warps = warps.listWarps()

        if (warps.isEmpty()) {
            warpList = "Não há warps disponíveis. :("
        } else {
            for (warp in warps) {
                warpList += "$warp\n"
            }
        }

        val message = Component.text()
            .append(Component.text("Lista de warps disponíveis: \n", NamedTextColor.GREEN))
            .append(Component.text(warpList, NamedTextColor.GRAY))
            .build()

        sender.reply(message)
    }
}