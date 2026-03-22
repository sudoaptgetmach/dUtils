package com.mach.utils.model

import org.bukkit.Location

data class Warp(
    val name: String,
    val location: Location,
    val isPublic: Boolean
)