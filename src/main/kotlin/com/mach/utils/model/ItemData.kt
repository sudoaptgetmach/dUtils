package com.mach.utils.model

import net.kyori.adventure.text.Component
import org.bukkit.entity.Player

data class ItemData(
    val name: String,
    val displayName: Component,
    val lore: List<Component>,
    val amount: Int,
    val owning: Player,
)
