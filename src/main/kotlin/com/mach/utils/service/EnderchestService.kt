package com.mach.utils.service

import com.mach.dFramework.core.context.FrameworkContext
import com.mach.dFramework.ui.builder.InventoryBuilder
import com.mach.utils.enums.Messages
import com.mach.utils.factory.ItemFactory
import com.mach.utils.model.ItemData
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import java.util.*
import java.util.concurrent.ConcurrentHashMap

class EnderchestService(private val ctx: FrameworkContext) {
    private val cache = ConcurrentHashMap<UUID, MutableMap<Int, Inventory>>()

    fun openSelector(player: Player) {
        val musicDisc = Sound.sound(Key.key("block.ender_chest.open"), Sound.Source.BLOCK, 1f, 1f)
        val audience = Audience.audience(player)

        player.openInventory(handleItemCreation(player))
        audience.playSound(musicDisc)
    }

    fun openEnderchest(player: Player, chestNumber: Int) {
        if (chestNumber !in 1..7) return

        val musicDisc = Sound.sound(Key.key("block.chest.open"), Sound.Source.BLOCK, 1f, 1f)
        val audience = Audience.audience(player)

        val playerVaults = cache.getOrPut(player.uniqueId) { mutableMapOf() }

        val inventory = playerVaults.getOrPut(chestNumber) {
            InventoryBuilder(player, 27, Component.text("Enderchest #$chestNumber", NamedTextColor.LIGHT_PURPLE)).build()
        }

        player.openInventory(inventory)
        audience.playSound(musicDisc)

        ctx.messaging.actionbar(
            player,
            Messages.ENDERCHEST_OPEN,
            "<green>Enderchest {number} aberto com sucesso!", mapOf(
                "{number}" to chestNumber.toString()
            )
        )
    }

    fun handleItemCreation(player: Player): Inventory {
        val builder = InventoryBuilder(player, 27, Component.text("Meus Enderchests", NamedTextColor.GOLD))

        val slots = listOf(10, 11, 12, 13, 14, 15, 16)

        slots.forEachIndexed { index, slot ->
            val chestNumber = index + 1
            val chestName = "Enderchest #$chestNumber"

            val stack = ItemStack(Material.ENDER_CHEST)

            val item = ItemFactory.create(
                stack,
                ItemData(
                    chestName,
                    Component.text(chestName, NamedTextColor.AQUA),
                    listOf(
                        Component.text("Clique para abrir a mochila #$chestNumber", NamedTextColor.GRAY)
                    ),
                    1,
                    player
                )
            )

            builder.slot(slot, item)
        }

        return builder.build()
    }
}