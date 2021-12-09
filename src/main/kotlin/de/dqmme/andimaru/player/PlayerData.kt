package de.dqmme.andimaru.util

import net.axay.kspigot.main.KSpigotMainInstance
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import java.io.File

private val file = File(KSpigotMainInstance.dataFolder, "playerdata.yml")
private lateinit var playerDataConfiguration: FileConfiguration

fun Player.availableHomes(): Int {
    return playerDataConfiguration.getInt("$uniqueId.availablehomes")
}

fun Player.setAvailableHomes(availableHomes: Int) {
    playerDataConfiguration.set("$uniqueId.availablehomes", availableHomes)
    savePlayerDataFile()
}

fun Player.hasFlyingEnabled(): Boolean {
    if (playerDataConfiguration.get("$uniqueId.flyingEnabled") == null) {
        playerDataConfiguration.set("$uniqueId.flyingEnabled", true)
        savePlayerDataFile()
    }

    return playerDataConfiguration.getBoolean("$uniqueId.flyingEnabled")
}

fun Player.setFlyingEnabled(flyingEnabled: Boolean) {
    playerDataConfiguration.set("$uniqueId.flyingEnabled", flyingEnabled)
    savePlayerDataFile()
}

fun reloadPlayerData() {
    playerDataConfiguration = YamlConfiguration.loadConfiguration(file)
    savePlayerDataFile()
}

private fun savePlayerDataFile() {
    playerDataConfiguration.save(file)
}