package com.mach.utils.commands

import com.mach.dFramework.ui.builder.InventoryBuilder
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player
import revxrsal.commands.annotation.Command
import revxrsal.commands.bukkit.annotation.CommandPermission

@Suppress("unused")
class EnderchestCommand {
    @Command("enderchest", "ec")
    @CommandPermission("dutils.enderchest.usage")
    fun run(
        sender: Player
    ) {
        val chest = InventoryBuilder(sender, 27, Component.text("Enderchest", NamedTextColor.GOLD))
            .build()
        val musicDisc = Sound.sound(Key.key("block.ender_chest.open"), Sound.Source.BLOCK, 1f, 1f)
        val audience = Audience.audience(sender)

        sender.openInventory(chest)
        audience.playSound(musicDisc)
    }
}