package de.dqmme.andimaru.listener

import de.dqmme.andimaru.util.*
import net.axay.kspigot.event.listen
import net.kyori.adventure.text.Component
import org.bukkit.event.player.PlayerJoinEvent

fun onJoin() = listen<PlayerJoinEvent> { event ->
    val player = event.player

    event.joinMessage(
        Component.text(
            message("player_joined")
                .replace("\${player_name}", player.name)
                .replace("\${player_color}", player.rank().color)
        )
    )

    val spawn = spawnLocation()

    if (spawn != null) {
        player.teleportAsync(spawn)
    }

    player.addToScoreboard()

    if (player.availableHomes() < 2 || player.availableHomes() > 5) {
        player.setAvailableHomes(2)
    }
}