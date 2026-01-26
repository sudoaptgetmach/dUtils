package com.mach.utils

import com.mach.utils.commands.DifficultyCommand
import com.mach.utils.commands.GamemodeCommand
import org.bukkit.plugin.java.JavaPlugin

class Main : JavaPlugin() {

    override fun onEnable() {
        saveDefaultConfig()

        getCommand("gamemode")!!.setExecutor(GamemodeCommand(this))
        getCommand("difficulty")!!.setExecutor(DifficultyCommand(this))
    }

    override fun onDisable() {

    }

}