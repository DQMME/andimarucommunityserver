package de.dqmme.andimaru.listener

import de.dqmme.andimaru.util.addToScoreboard
import de.dqmme.andimaru.util.message
import de.dqmme.andimaru.util.rank
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

    player.addToScoreboard()
}