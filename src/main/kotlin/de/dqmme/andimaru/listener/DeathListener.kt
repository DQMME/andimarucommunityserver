package de.dqmme.andimaru.listener

import de.dqmme.andimaru.util.*
import net.axay.kspigot.event.listen
import net.kyori.adventure.text.Component
import org.bukkit.event.entity.PlayerDeathEvent

fun onDeath() = listen<PlayerDeathEvent> { event ->
    val player = event.player

    val deaths = player.deaths() + 1

    player.addToScoreboard(deaths)

    event.deathMessage(
        Component.text(
            message("player_died")
                .replace("\${player_name}", player.name)
                .replace("\${player_color}", player.rank().color)
                .replace("\${deaths}", deaths)
        )
    )
}