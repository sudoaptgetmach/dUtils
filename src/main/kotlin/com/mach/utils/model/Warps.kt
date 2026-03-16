package com.mach.utils.model

import org.bukkit.Location

class Warps {
    private lateinit var name: String;
    private lateinit var location: Location;
    private lateinit var world: String;
    private var public: Boolean = true;

    fun addWarp(name: String, location: Location, world: String, public: Boolean) {
        TODO()
    }

    fun removeWarp(name: String) {
        TODO()
    }

    fun warpExists(name: String): Boolean {
        TODO()
    }

    fun getWarpLocation(name: String): Location {
        TODO()
    }

    fun listWarps(): List<String> {
        return listOf()
    }
}