package com.mach.utils.model

import org.bukkit.Location

class Warps {
    private lateinit var name: String
    private lateinit var location: Location
    private lateinit var world: String
    private var public: Boolean = true

    fun addWarp(name: String, location: Location, world: String, public: Boolean) {

    }

    fun removeWarp(name: String) {

    }

    fun warpExists(name: String): Boolean {
        return false
    }

    fun getWarpLocation(name: String): Location {
        TODO()
    }

    fun listWarps(): List<String> {
        return listOf("teste", "teste1")
    }
}