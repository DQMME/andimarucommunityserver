package de.dqmme.andimaru.util

import net.axay.kspigot.main.KSpigotMainInstance
import net.axay.kspigot.runnables.task
import net.kyori.adventure.text.Component
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.io.File

private val coinFile = File(KSpigotMainInstance.dataFolder, "coins.yml")
private lateinit var fileConfiguration: FileConfiguration

fun Player.coins(): Double {
    return fileConfiguration.getDouble(uniqueId.toString())
}

fun Player.setCoins(newCoins: Double) {
    fileConfiguration.set(uniqueId.toString(), newCoins)
    savePlayerCoins()
}

fun Player.addCoins(coinsToAdd: Double) {
    setCoins(coins() + coinsToAdd)
}

fun Player.removeCoins(coinsToRemove: Double) {
    var newCoins = coins() - coinsToRemove

    if (newCoins < 0) newCoins = 0.0

    setCoins(newCoins)
}

fun sendPlayerCoins() = task(true, 0, 20) {
    for (player in KSpigotMainInstance.server.onlinePlayers) {
        player.sendActionBar(Component.text("§6${player.coins()} Coins"))
    }
}

fun reloadCoins() {
    fileConfiguration = YamlConfiguration.loadConfiguration(coinFile)
    savePlayerCoins()
}

private fun savePlayerCoins() {
    fileConfiguration.save(coinFile)
}