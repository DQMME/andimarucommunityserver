package de.dqmme.andimaru.dataclass

import org.bukkit.Location

data class TeleportNPCData(
    var displayName: String?,
    var location: Location?,
    var imitatePlayer: Boolean = true,
    var lookAtPlayer: Boolean = true,
    var teleportLocation: Location?,
    var title: String?
)
