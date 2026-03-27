package com.mach.utils.factory

import com.mach.dFramework.ui.builder.ItemBuilder
import com.mach.utils.model.ItemData
import org.bukkit.inventory.ItemStack

class ItemFactory {
    companion object {
        fun create(stack: ItemStack, data: ItemData): ItemStack {
            val item = ItemBuilder(stack)
                .name(data.name)
                .amount(data.amount)
                .owning(data.owning)
                .build()

            val meta = item.itemMeta

            meta.displayName(data.displayName)
            meta.lore(data.lore)

            return item
        }
    }
}