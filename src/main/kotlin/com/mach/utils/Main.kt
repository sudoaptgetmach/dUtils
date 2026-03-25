package com.mach.utils

import com.mach.dFramework.core.context.FrameworkContext
import com.mach.utils.api.MessagesApi
import com.mach.utils.commands.DUtilsCommand
import com.mach.utils.commands.DifficultyCommand
import com.mach.utils.commands.EnderchestCommand
import com.mach.utils.commands.FlyCommand
import com.mach.utils.commands.GamemodeCommand
import com.mach.utils.commands.WarpCommand
import com.mach.utils.handler.CommandExceptionHandler
import com.mach.utils.listener.EnderchestListener
import com.mach.utils.service.WarpService
import org.bukkit.plugin.java.JavaPlugin
import revxrsal.commands.Lamp
import revxrsal.commands.bukkit.BukkitLamp
import revxrsal.commands.bukkit.actor.BukkitCommandActor

class Main : JavaPlugin() {
    private lateinit var ctx: FrameworkContext

    override fun onEnable() {
        ctx = FrameworkContext.create(this, "lang.yml")
            .registerConfig("config.yml", true)
            .registerConfig("warps.yml", true)
            .registerMessageFile("lang.yml", true)

        ctx.initialize()

        val lamp: Lamp<BukkitCommandActor> = BukkitLamp.builder(this)
            .exceptionHandler(CommandExceptionHandler(ctx.logger))
            .build()

        MessagesApi.init(ctx)

        server.pluginManager.registerEvents(EnderchestListener(), this)

        lamp.register(DifficultyCommand())
        lamp.register(GamemodeCommand())
        lamp.register(WarpCommand(WarpService(ctx.configs)))
        lamp.register(DUtilsCommand(ctx.configs))
        lamp.register(FlyCommand())
        lamp.register(EnderchestCommand())
    }

    override fun onDisable() {
        if (::ctx.isInitialized) {
            ctx.shutdown()
        }
    }
}