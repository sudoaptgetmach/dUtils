package com.mach.utils.listener

import net.kyori.adventure.audience.Audience
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryCloseEvent

class EnderchestListener : Listener {

    @EventHandler
    private fun onEnderchestClose(e: InventoryCloseEvent) {
        val title = e.view.title()
        val audience = Audience.audience(e.player)
        val musicDisc = Sound.sound(Key.key("block.ender_chest.close"), Sound.Source.BLOCK, 1f, 1f)

        if (title == Component.text("Enderchest", NamedTextColor.GOLD)) {
            audience.playSound(musicDisc)
        }
    }
}