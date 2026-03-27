package com.mach.utils

import com.mach.dFramework.core.context.FrameworkContext
import com.mach.utils.api.MessagesApi
import com.mach.utils.commands.*
import com.mach.utils.handler.CommandExceptionHandler
import com.mach.utils.listener.enderchest.EnderchestInventoryListener
import com.mach.utils.listener.enderchest.EnderchestJoinListener
import com.mach.utils.service.EnderchestService
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

        val enderchestService = EnderchestService(ctx, this)
        server.pluginManager.registerEvents(EnderchestJoinListener(enderchestService), this)
        server.pluginManager.registerEvents(EnderchestInventoryListener(enderchestService), this)

        lamp.register(DifficultyCommand())
        lamp.register(GamemodeCommand())
        lamp.register(WarpCommand(WarpService(ctx.configs)))
        lamp.register(DUtilsCommand(ctx.configs))
        lamp.register(FlyCommand())
        lamp.register(EnderchestCommand(ctx, enderchestService))
    }

    override fun onDisable() {
        if (::ctx.isInitialized) {
            ctx.shutdown()
        }
    }
}