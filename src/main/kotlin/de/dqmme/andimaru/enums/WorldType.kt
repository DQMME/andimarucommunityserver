package de.dqmme.andimaru.enums

import org.bukkit.World

enum class WorldType(val environment: World.Environment) {
    NORMAL(World.Environment.NORMAL),
    NETHER(World.Environment.NETHER),
    END(World.Environment.THE_END),
    FLAT(World.Environment.NORMAL);

    companion object {
        fun parse(name: String): WorldType {
            return when (name.lowercase()) {
                "normal" -> NORMAL
                "nether" -> NETHER
                "end" -> END
                "flat" -> FLAT
                else -> NORMAL
            }
        }
    }
}