package de.dqmme.andimaru.dataclass

import org.bukkit.Location

data class NPCData(var displayName: String?, var location: Location?, var imitatePlayer: Boolean = true, var lookAtPlayer: Boolean = true)
