package de.dqmme.andimaru.manager

import de.dqmme.andimaru.util.hasFlyingEnabled
import me.ryanhamshire.GriefPrevention.GriefPrevention
import net.axay.kspigot.main.KSpigotMainInstance
import net.axay.kspigot.runnables.task
import org.bukkit.GameMode

class ClaimManager {
    init {
        check()
    }

    private fun check() = task(true, 0, 20) {
        for (player in KSpigotMainInstance.server.onlinePlayers) {
            if(player.gameMode != GameMode.SURVIVAL) continue

            if (!player.hasFlyingEnabled()) continue

            val claim = GriefPrevention.instance.dataStore.getClaimAt(player.location, true, null)

            if(!claim.managers.contains(player.uniqueId.toString()) && claim.ownerID != player.uniqueId) continue

            if (claim == null) {
                player.allowFlight = false
                player.isFlying = false
            } else {
                player.allowFlight = true
                player.isFlying = true
            }
        }
    }
}