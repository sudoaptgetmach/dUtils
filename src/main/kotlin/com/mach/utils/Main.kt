package com.mach.utils

import com.mach.dFramework.context.FrameworkContext
import com.mach.utils.api.MessagesApi
import com.mach.utils.commands.DUtilsCommand
import com.mach.utils.commands.DifficultyCommand
import com.mach.utils.commands.GamemodeCommand
import com.mach.utils.commands.WarpCommand
import org.bukkit.plugin.java.JavaPlugin
import revxrsal.commands.Lamp
import revxrsal.commands.bukkit.BukkitLamp
import revxrsal.commands.bukkit.actor.BukkitCommandActor

class Main : JavaPlugin() {
    private lateinit var ctx: FrameworkContext

    override fun onEnable() {
        val lamp: Lamp<BukkitCommandActor> = BukkitLamp.builder(this)
            .build()

        ctx = FrameworkContext.create(this, "lang.yml")
            .registerConfig("config.yml", true)
            .registerConfig("warps.yml", true)
            .registerMessageFile("lang.yml", true)

        ctx.initialize()
        MessagesApi.init(ctx)

        getCommand("gamemode")!!.setExecutor(GamemodeCommand())
        lamp.register(DifficultyCommand())
        lamp.register(WarpCommand())
        lamp.register(DUtilsCommand(ctx))
    }

    override fun onDisable() {
        if (::ctx.isInitialized) {
            ctx.shutdown()
        }
    }
}