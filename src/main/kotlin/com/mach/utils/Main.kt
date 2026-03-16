package com.mach.utils

import com.mach.utils.commands.DifficultyCommand
import com.mach.utils.commands.GamemodeCommand
import com.mach.utils.commands.WarpCommand
import org.bukkit.plugin.java.JavaPlugin
import revxrsal.commands.Lamp
import revxrsal.commands.bukkit.BukkitLamp
import revxrsal.commands.bukkit.actor.BukkitCommandActor

class Main : JavaPlugin() {
    override fun onEnable() {
        val lamp: Lamp<BukkitCommandActor> = BukkitLamp.builder(this)
            .build()

        getCommand("gamemode")!!.setExecutor(GamemodeCommand(this))
        lamp.register(DifficultyCommand())
        lamp.register(WarpCommand())
    }

    override fun onDisable() {

    }
}