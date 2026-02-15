package com.mach.utils

import com.mach.dFramework.DFramework
import com.mach.dFramework.manager.DatabaseManager
import com.mach.utils.commands.DifficultyCommand
import com.mach.utils.commands.GamemodeCommand
import com.mach.utils.handler.ConfigHandler
import org.bukkit.plugin.java.JavaPlugin
import revxrsal.commands.Lamp
import revxrsal.commands.bukkit.BukkitLamp
import revxrsal.commands.bukkit.actor.BukkitCommandActor

class Main : JavaPlugin() {
    override fun onEnable() {
        val lamp: Lamp<BukkitCommandActor> = BukkitLamp.builder(this)
            .build()
        val configHandler = ConfigHandler(this)

        configHandler.initConfigs()
        DFramework.initConfigs(this, configHandler.getConfigs())
        saveDefaultConfig()

        getCommand("gamemode")!!.setExecutor(GamemodeCommand(this))
        lamp.register(DifficultyCommand())
    }

    override fun onDisable() {

    }

    fun getDatabaseManager(): DatabaseManager? {
        return DFramework.getDatabaseManager()
    }

}