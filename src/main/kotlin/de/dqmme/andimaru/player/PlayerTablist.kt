package de.dqmme.andimaru.util

import de.dqmme.andimaru.enums.Rank
import net.axay.kspigot.main.KSpigotMainInstance
import net.kyori.adventure.text.Component
import org.bukkit.Statistic
import org.bukkit.entity.Player

fun Player.rank(): Rank {
    val ranks = Rank.values()

    var playerRank: Rank = Rank.DEFAULT

    for (rank in ranks) {
        if (rank.permission == null) continue

        if (hasPermission(rank.permission)) playerRank = rank
    }

    return playerRank
}

fun Player.deaths(): Int {
    return getStatistic(Statistic.DEATHS)
}

fun Player.addToScoreboard(deaths: Int) {
    val instance = KSpigotMainInstance
    val scoreboardManager = instance.server.scoreboardManager
    val scoreboard = scoreboardManager.newScoreboard

    this.scoreboard = scoreboard

    for (onlinePlayer in instance.server.onlinePlayers) {
        val rank = onlinePlayer.rank()

        val team = scoreboard.getTeam(onlinePlayer.name.lowercase())
            ?: scoreboard.registerNewTeam(onlinePlayer.name.lowercase())
        team.removeEntry(onlinePlayer.name)
        team.prefix(Component.text(rank.prefix))
        team.suffix(Component.text(" §c${onlinePlayer.deaths()}"))
        team.addEntry(onlinePlayer.name)

        val onlinePlayerScoreboard = onlinePlayer.scoreboard
        val playerTeam =
            onlinePlayerScoreboard.getTeam(name.lowercase()) ?: onlinePlayerScoreboard.registerNewTeam(name.lowercase())
        playerTeam.removeEntry(name)
        playerTeam.prefix(Component.text(rank().prefix))
        playerTeam.suffix(Component.text(" §c$deaths"))
        playerTeam.addEntry(name)
    }
}

fun Player.addToScoreboard() {
    addToScoreboard(deaths())
}

fun Player.removeFromScoreboard() {
    val server = KSpigotMainInstance.server

    for (onlinePlayer in server.onlinePlayers) {
        val scoreboard = onlinePlayer.scoreboard

        val team = scoreboard.getTeam(name.lowercase()) ?: continue

        team.removeEntry(name)
        team.unregister()
    }
}