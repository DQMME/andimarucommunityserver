package de.dqmme.andimaru.manager

import net.axay.kspigot.main.KSpigotMainInstance
import org.bukkit.ChatColor
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

val messageFile = File(KSpigotMainInstance.dataFolder, "messages.yml")
private lateinit var fileConfiguration: FileConfiguration

fun message(name: String): String {
    val message = fileConfiguration.getString(name.lowercase()) ?: return "§cMessage §e$name §cnot found."

    val coloredMessage = ChatColor.translateAlternateColorCodes('&', message)

    val prefix = fileConfiguration.getString("prefix") ?: return coloredMessage

    val coloredPrefix = ChatColor.translateAlternateColorCodes('&', prefix)

    return coloredMessage.replace("\${prefix}", coloredPrefix)
}

fun reloadMessages() {
    fileConfiguration = YamlConfiguration.loadConfiguration(messageFile)
}