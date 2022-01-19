package de.dqmme.andimaru.listener

import de.dqmme.andimaru.manager.spawnLocation
import de.dqmme.andimaru.util.home
import net.axay.kspigot.event.listen
import org.bukkit.event.player.PlayerRespawnEvent

fun onRespawn() = listen<PlayerRespawnEvent> { event ->
    val player = event.player
    val firstHome = player.home(1)

    if (firstHome != null) {
        event.respawnLocation = firstHome.location
    } else {
        val spawn = spawnLocation()

        if (spawn != null) {
            player.teleportAsync(spawn)
        }
    }
}