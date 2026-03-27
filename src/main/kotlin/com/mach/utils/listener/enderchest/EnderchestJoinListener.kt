package com.mach.utils.listener.enderchest

import com.mach.utils.service.EnderchestService
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.player.PlayerJoinEvent
import org.bukkit.event.player.PlayerQuitEvent

class EnderchestJoinListener(private val service: EnderchestService) : Listener {
    @EventHandler
    fun onPlayerJoin(e: PlayerJoinEvent) {
        service.loadPlayerEnderchests(e.player)
    }

    @EventHandler
    fun onPlayerQuit(e: PlayerQuitEvent) {
        service.clearCache(e.player.uniqueId)
    }
}