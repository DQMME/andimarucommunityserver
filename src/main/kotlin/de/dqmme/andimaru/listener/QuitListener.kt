package de.dqmme.andimaru.listener

import de.dqmme.andimaru.util.message
import de.dqmme.andimaru.util.rank
import net.axay.kspigot.event.listen
import net.kyori.adventure.text.Component
import org.bukkit.event.player.PlayerQuitEvent

fun onQuit() = listen<PlayerQuitEvent> { event ->
    val player = event.player

    event.quitMessage(
        Component.text(
            message("player_left")
                .replace("\${player_name}", player.name)
                .replace("\${player_color}", player.rank().color)
        )
    )
}