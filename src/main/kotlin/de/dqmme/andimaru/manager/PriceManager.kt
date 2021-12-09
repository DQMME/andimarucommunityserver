package de.dqmme.andimaru.manager

import net.axay.kspigot.main.KSpigotMainInstance
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

val priceFile = File(KSpigotMainInstance.dataFolder, "prices.yml")
private lateinit var fileConfiguration: FileConfiguration

fun price(name: String): Double {
    if (fileConfiguration.get(name.lowercase()) == null) return Double.MAX_VALUE

    return fileConfiguration.getDouble(name.lowercase())
}

fun reloadPrices() {
    fileConfiguration = YamlConfiguration.loadConfiguration(priceFile)
    savePriceFile()
}

private fun savePriceFile() {
    fileConfiguration.save(priceFile)
}