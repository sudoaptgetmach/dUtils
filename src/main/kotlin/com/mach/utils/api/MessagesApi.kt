package com.mach.utils.api

import com.mach.dFramework.core.context.FrameworkContext
import net.kyori.adventure.text.Component

object MessagesApi {
    private lateinit var ctx: FrameworkContext

    fun init(context: FrameworkContext) {
        ctx = context
    }

    fun get(path: String, fallback: String = ""): Component {
        return ctx.messages.getComponent(path, fallback)
    }

    fun get(path: String, fallback: String = "", extra: Map<String, String>): Component {
        return ctx.messages.getComponent(path, fallback, extra)
    }
}
