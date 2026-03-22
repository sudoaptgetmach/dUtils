package com.mach.utils.service

import com.mach.dFramework.context.FrameworkConfigs
import com.mach.utils.model.Warp
import org.bukkit.Bukkit
import org.bukkit.Location

class WarpService(
    private val configs: FrameworkConfigs
) {
    private val file = "warps.yml"

    fun save(warp: Warp): Boolean {
        val world = warp.location.world ?: return false
        val base = "warps.${warp.name}"

        configs.set(file, "$base.world", world.name)
        configs.set(file, "$base.x", warp.location.x)
        configs.set(file, "$base.y", warp.location.y)
        configs.set(file, "$base.z", warp.location.z)
        configs.set(file, "$base.yaw", warp.location.yaw)
        configs.set(file, "$base.pitch", warp.location.pitch)
        configs.set(file, "$base.public", warp.isPublic)

        return configs.save(file)
    }

    fun delete(name: String): Boolean {
        if (!exists(name)) return false

        configs.remove(file, "warps.$name")
        return configs.save(file)
    }

    fun find(name: String): Warp? {
        val conf = configs.get(file) ?: return null
        val base = "warps.$name"

        if (!conf.isConfigurationSection(base)) return null

        val worldName = conf.getString("$base.world") ?: return null
        val world = Bukkit.getWorld(worldName) ?: return null

        val x = conf.getDouble("$base.x")
        val y = conf.getDouble("$base.y")
        val z = conf.getDouble("$base.z")
        val yaw = conf.getDouble("$base.yaw").toFloat()
        val pitch = conf.getDouble("$base.pitch").toFloat()
        val isPublic = conf.getBoolean("$base.public", false)

        return Warp(
            name,
            Location(world, x, y, z, yaw, pitch),
            isPublic
        )
    }

    fun findAll(): List<Warp> {
        val conf = configs.get(file) ?: return emptyList()
        val section = conf.getConfigurationSection("warps") ?: return emptyList()

        return section.getKeys(false).mapNotNull { find(it) }
    }

    fun exists(name: String): Boolean {
        val conf = configs.get(file) ?: return false
        return conf.isSet("warps.$name")
    }
}