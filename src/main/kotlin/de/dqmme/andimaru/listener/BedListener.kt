package de.dqmme.andimaru.listener

import de.dqmme.andimaru.util.message
import de.dqmme.andimaru.util.rank
import de.dqmme.andimaru.util.replace
import net.axay.kspigot.event.listen
import net.axay.kspigot.main.KSpigotMainInstance
import net.kyori.adventure.text.Component
import org.bukkit.event.player.PlayerBedEnterEvent
import org.bukkit.event.player.PlayerBedLeaveEvent
import java.util.*

class BedListener {
    private val players = arrayListOf<UUID>()

    fun onBedEnter() = listen<PlayerBedEnterEvent> { event ->
        val server = KSpigotMainInstance.server
        val player = event.player

        if (event.bedEnterResult != PlayerBedEnterEvent.BedEnterResult.OK) return@listen

        players.add(player.uniqueId)

        val playersInBed = players.size
        val requiredPlayers = server.onlinePlayers.size / 2

        server.broadcast(
            Component.text(
                message("player_entered_bed")
                    .replace("\${player_name}", player.name)
                    .replace("\${player_color}", player.rank().color)
                    .replace("\${players_in_bed}", playersInBed)
                    .replace("\${required_players}", requiredPlayers)
            )
        )

        if (playersInBed < requiredPlayers) return@listen

        for (world in server.worlds) {
            world.time = 0
        }

        server.broadcast(Component.text(message("night_skipped")))
    }

    fun onBedLeave() = listen<PlayerBedLeaveEvent> { event ->
        val player = event.player
        val server = KSpigotMainInstance.server

        players.remove(player.uniqueId)

        val playersInBed = players.size
        val requiredPlayers = server.onlinePlayers.size / 2

        server.broadcast(
            Component.text(
                message("player_left_bed")
                    .replace("\${player_name}", player.name)
                    .replace("\${player_color}", player.rank().color)
                    .replace("\${players_in_bed}", playersInBed.toString())
                    .replace("\${required_players}", requiredPlayers.toString())
            )
        )
    }
}