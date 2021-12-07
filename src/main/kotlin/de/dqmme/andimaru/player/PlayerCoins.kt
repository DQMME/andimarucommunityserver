package de.dqmme.andimaru.util

import net.axay.kspigot.main.KSpigotMainInstance
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.io.File

private val coinFile = File(KSpigotMainInstance.dataFolder, "prices.yml")
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

fun reloadCoins() {
    fileConfiguration = YamlConfiguration.loadConfiguration(coinFile)
    savePlayerCoins()
}

private fun savePlayerCoins() {
    fileConfiguration.save(coinFile)
}