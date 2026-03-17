package com.mach.utils.interfaces

import com.mach.utils.api.MessagesApi
import net.kyori.adventure.text.Component

interface MessageAccessor {
    fun getMessage(path: String, fallback: String = ""): Component {
        return MessagesApi.get(path, fallback)
    }

    fun getMessage(path: String, fallback: String = "", extra: Map<String, String>): Component {
        return MessagesApi.get(path, fallback, extra)
    }
}