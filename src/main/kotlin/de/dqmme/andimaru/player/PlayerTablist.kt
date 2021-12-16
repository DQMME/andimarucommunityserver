package de.dqmme.andimaru.util

import de.dqmme.andimaru.enums.Rank
import de.dqmme.andimaru.manager.tablistFooter
import de.dqmme.andimaru.manager.tablistHeader
import net.axay.kspigot.main.KSpigotMainInstance
import net.axay.kspigot.runnables.task
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

fun Player.playtimeSeconds(): Int{
    return getStatistic(Statistic.PLAY_ONE_MINUTE) / 20
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

fun Player.setPlayerListHeaderFooter() {
    val playtime = formatPlaytime(playtimeSeconds())
    val header = tablistHeader()?.replace("\${player_playtime}", playtime)
    val footer = tablistFooter()?.replace("\${player_playtime}", playtime)

    if(header != null) {
        sendPlayerListHeader(Component.text(header))
    }

    if(footer != null) {
        sendPlayerListFooter(Component.text(footer))
    }
}

fun refreshPlayerTablist() = task(true, 0, 10*20) {
    for(player in KSpigotMainInstance.server.onlinePlayers) {
        player.setPlayerListHeaderFooter()
    }
}

private fun formatPlaytime(seconds: Int): String {
    if(seconds < 60) {
        return "$seconds Sekunden"
    }

    if(seconds < 3600) {
        return "${seconds / 60} Minuten"
    }

    return "${seconds / 60 / 60} Stunden"
}