package com.mach.utils.service

import com.mach.dFramework.core.context.FrameworkContext
import com.mach.dFramework.core.utils.InventorySerializer
import com.mach.dFramework.ui.builder.InventoryBuilder
import com.mach.utils.enums.Messages
import com.mach.utils.factory.ItemFactory
import com.mach.utils.model.ItemData
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.key.Key
import net.kyori.adventure.sound.Sound
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import org.bukkit.Bukkit
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.Inventory
import org.bukkit.inventory.ItemStack
import org.bukkit.plugin.Plugin
import java.util.UUID
import java.util.concurrent.ConcurrentHashMap

class EnderchestService(private val ctx: FrameworkContext, private val plugin: Plugin) {
    private val cache = ConcurrentHashMap<UUID, MutableMap<Int, Inventory>>()
    private fun getChestTitle(number: Int) = Component.text("Enderchest #$number", NamedTextColor.LIGHT_PURPLE)

    fun loadPlayerEnderchests(player: Player) {
        ctx.database.queryAsync(
            "SELECT chest_id, items FROM player_enderchests WHERE uuid = ?",
            player.uniqueId.toString()
        ) { rs ->
            val rawChests = mutableMapOf<Int, String>()
            do {
                rawChests[rs.getInt("chest_id")] = rs.getString("items")
            } while (rs.next())
            rawChests
        }.thenAccept { rawChests ->
            if (rawChests.isNullOrEmpty()) return@thenAccept

            Bukkit.getScheduler().runTask(plugin, Runnable {
                val playerVaults = mutableMapOf<Int, Inventory>()

                rawChests.forEach { (chestId, base64) ->
                    val inventory = InventoryBuilder(player, 27, getChestTitle(chestId)).build()
                    inventory.contents = InventorySerializer.fromBase64(base64)
                    playerVaults[chestId] = inventory
                }

                cache[player.uniqueId] = playerVaults
                println("[dUtils] Carregados ${playerVaults.size} baús para ${player.name}")
            })
        }
    }

    fun saveEnderchest(player: Player, chestNumber: Int, inventory: Inventory) {
        val base64 = InventorySerializer.toBase64(inventory.contents)

        ctx.database.executeUpdateAsync(
            """
            INSERT INTO player_enderchests (uuid, chest_id, items) 
            VALUES (?, ?, ?) 
            ON CONFLICT(uuid, chest_id) DO UPDATE SET items = excluded.items
            """,
            player.uniqueId.toString(), chestNumber, base64
        ).thenAccept {
            println("[dUtils] Itens salvos com sucesso no Enderchest #$chestNumber de ${player.name}!")
        }
    }

    fun clearCache(uuid: UUID) {
        cache.remove(uuid)
    }

    fun openSelector(player: Player) {
        val audience = Audience.audience(player)
        val musicDisc = Sound.sound(Key.key("block.ender_chest.open"), Sound.Source.BLOCK, 1f, 1f)

        player.openInventory(buildSelectorGUI(player))
        audience.playSound(musicDisc)
    }

    fun openEnderchest(player: Player, chestNumber: Int) {
        if (chestNumber !in 1..7) return

        val audience = Audience.audience(player)
        val musicDisc = Sound.sound(Key.key("block.chest.open"), Sound.Source.BLOCK, 1f, 1f)

        val playerVaults = cache.getOrPut(player.uniqueId) { mutableMapOf() }
        val inventory = playerVaults.getOrPut(chestNumber) {
            InventoryBuilder(player, 27, getChestTitle(chestNumber)).build()
        }

        player.openInventory(inventory)
        audience.playSound(musicDisc)

        ctx.messaging.actionbar(
            player,
            Messages.ENDERCHEST_OPEN,
            "<green>Enderchest {number} aberto com sucesso!", mapOf("{number}" to chestNumber.toString())
        )
    }

    private fun buildSelectorGUI(player: Player): Inventory {
        val builder = InventoryBuilder(player, 27, Component.text("Meus Enderchests", NamedTextColor.GOLD))

        (10..16).forEachIndexed { index, slot ->
            val chestNumber = index + 1
            val chestName = "Enderchest #$chestNumber"

            val item = ItemFactory.create(
                ItemStack(Material.ENDER_CHEST),
                ItemData(
                    chestName,
                    Component.text(chestName, NamedTextColor.AQUA),
                    listOf(Component.text("Clique para abrir a mochila #$chestNumber", NamedTextColor.GRAY)),
                    1,
                    player
                )
            )
            builder.slot(slot, item)
        }
        return builder.build()
    }
}