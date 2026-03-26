package com.mach.utils.listener

import net.kyori.adventure.audience.Audience
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryMoveItemEvent

class EnderchestListener : Listener {

    @EventHandler
    private fun onEnderchestClose(e: InventoryCloseEvent, m: InventoryMoveItemEvent) {
        val title = e.view.title()
        val audience = Audience.audience(e.player)
        val musicDisc = Sound.sound(Key.key("block.ender_chest.close"), Sound.Source.BLOCK, 1f, 1f)

        if (title == Component.text("Meus Enderchests", NamedTextColor.GOLD)) {
            audience.playSound(musicDisc)
            m.isCancelled = true
        }
    }

    @EventHandler
    fun onInventoryClick(e: InventoryClickEvent) {
        val title = e.view.title()

        val targetTitle = Component.text("Meus Enderchests", NamedTextColor.GOLD)

        if (title == targetTitle) {
            e.isCancelled = true

            // val clickedSlot = e.rawSlot
            // if (clickedSlot == 10) { abrirEnderchest(1, e.whoClicked as Player) }
        }
    }
}