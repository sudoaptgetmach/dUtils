package com.mach.utils.listener

import com.mach.dFramework.core.context.FrameworkContext
import com.mach.utils.service.EnderchestService
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent

class EnderchestListener(ctx: FrameworkContext) : Listener {
    val ecSelectorTitle = Component.text("Meus Enderchests", NamedTextColor.GOLD)
    val service = EnderchestService(ctx)

    @EventHandler
    private fun onEnderchestClose(e: InventoryCloseEvent) {
        val title = e.view.title()
        val audience = Audience.audience(e.player)
        val musicDisc = Sound.sound(Key.key("block.ender_chest.close"), Sound.Source.BLOCK, 1f, 1f)

        if (title == ecSelectorTitle) {
            audience.playSound(musicDisc)
        }
    }

    @EventHandler
    fun onInventoryClick(e: InventoryClickEvent) {
        val title = e.view.title()
        val clickedSlot = e.rawSlot

        if (title == ecSelectorTitle) {
            e.isCancelled = true
            if (clickedSlot in 10..16) {
                val ec = (clickedSlot - 10) + 1
                service.openEnderchest(e.whoClicked as Player, ec)
            }
        }
    }

    @EventHandler
    fun onInventoryDrag(e: InventoryDragEvent) {
        val title = e.view.title()

        if (title == ecSelectorTitle) {
            e.isCancelled = true
        }
    }
}