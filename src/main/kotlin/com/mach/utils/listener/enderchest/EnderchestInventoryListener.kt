package com.mach.utils.listener.enderchest

import com.mach.utils.service.EnderchestService
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryDragEvent

class EnderchestInventoryListener(private val service: EnderchestService) : Listener {
    private val ecSelectorTitle = Component.text("Meus Enderchests", NamedTextColor.GOLD)

    private fun playSound(player: Player) {
        val sound = Sound.sound(Key.key("block.ender_chest.close"), Sound.Source.BLOCK, 1f, 1f)
        Audience.audience(player).playSound(sound)
    }

    @EventHandler
    fun onInventoryClick(e: InventoryClickEvent) {
        val title = e.view.title()

        if (title == ecSelectorTitle) {
            e.isCancelled = true

            if (e.rawSlot in 10..16) {
                val chestNumber = (e.rawSlot - 10) + 1
                service.openEnderchest(e.whoClicked as Player, chestNumber)
            }
        }
    }

    @EventHandler
    fun onInventoryDrag(e: InventoryDragEvent) {
        if (e.view.title() == ecSelectorTitle) {
            e.isCancelled = true
        }
    }

    @EventHandler
    fun onInventoryClose(e: InventoryCloseEvent) {
        val title = e.view.title()
        val player = e.player as Player

        if (title == ecSelectorTitle) {
            playSound(player)
            return
        }

        val plainTitle = PlainTextComponentSerializer.plainText().serialize(title)

        if (plainTitle.startsWith("Enderchest #")) {
            val chestNumber = plainTitle.substringAfter("#").trim().toIntOrNull() ?: return

            service.saveEnderchest(player, chestNumber, e.inventory)
        }
    }
}