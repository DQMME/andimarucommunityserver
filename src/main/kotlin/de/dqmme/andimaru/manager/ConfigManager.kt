package de.dqmme.andimaru.manager

import net.axay.kspigot.main.KSpigotMainInstance
import org.bukkit.Location
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

val configFile = File(KSpigotMainInstance.dataFolder, "config.yml")
private lateinit var fileConfiguration: FileConfiguration

fun spawnLocation(): Location? {
    return fileConfiguration.getLocation("spawn")
}

fun setSpawnLocation(location: Location) {
    fileConfiguration.set("spawn", location)
    saveConfigFile()
}

fun tablistHeader(): String? {
    return fileConfiguration.getString("tablist_header")
}

fun tablistFooter(): String? {
    return fileConfiguration.getString("tablist_footer")
}

fun reloadConfigFile() {
    fileConfiguration = YamlConfiguration.loadConfiguration(configFile)
    saveConfigFile()
}

private fun saveConfigFile() {
    fileConfiguration.save(configFile)
}