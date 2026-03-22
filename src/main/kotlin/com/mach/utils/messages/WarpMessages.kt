package com.mach.utils.messages

import com.mach.utils.enums.Messages
import com.mach.utils.interfaces.MessageAccessor
import net.kyori.adventure.text.Component

class WarpMessages : MessageAccessor {
    fun warpAdded(name: String): Component = getMessage(
        Messages.WARP_ADDED.getPath(),
        "<green>Warp added successfully.",
        mapOf(
            "{name}" to name
        )
    )

    fun warpRemoved(name: String): Component = getMessage(
        Messages.WARP_REMOVED.getPath(),
        "<red>Warp removed successfully.",
        mapOf(
            "{name}" to name
        )
    )

    fun warpNotFound(name: String): Component = getMessage(
        Messages.WARP_NOT_FOUND.getPath(),
        "<red>Warp <yellow>{name}</yellow> not found.",
        mapOf(
            "{name}" to name
        )
    )

    fun warpList(warpList: String): Component = getMessage(
        Messages.WARP_LIST.getPath(),
        "<green>Available warp list: \\n\\n{warpList}",
        mapOf(
            "warpList" to warpList
        )
    )

    fun warpEmptyList(): Component {
        return warpList("").append {
            getMessage(
                Messages.WARP_EMPTY_LIST.getPath(),
                "<gray>No warps available. :("
            )
        }
    }

    fun teleportedSuccessfully(name: String): Component = getMessage(
        Messages.WARP_TELEPORTED_SUCCESSFULLY.getPath(),
        "<green>Teleported to warp <yellow>{name}</yellow> successfully.",
        mapOf(
            "{name}" to name
        )
    )
}