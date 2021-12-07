package de.dqmme.andimaru.npc

import com.github.juliarn.npc.NPC
import com.github.juliarn.npc.event.PlayerNPCInteractEvent
import net.axay.kspigot.event.SingleListener
import org.bukkit.Location

abstract class NPC {
    abstract val displayName: String
    abstract val location: Location
    abstract val imitatePlayer: Boolean
    abstract val lookAtPlayer: Boolean
    abstract var finalNPC: NPC?

    abstract fun onInteract(event: PlayerNPCInteractEvent)
}