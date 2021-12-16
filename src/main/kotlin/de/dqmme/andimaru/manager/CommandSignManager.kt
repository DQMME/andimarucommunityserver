package de.dqmme.andimaru.manager

import de.dqmme.andimaru.dataclass.CommandSign
import net.axay.kspigot.main.KSpigotMainInstance
import org.bukkit.Location
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

private val commandSignFile = File(KSpigotMainInstance.dataFolder, "commandsigns.yml")
private lateinit var fileConfiguration: FileConfiguration

fun registerCommandSign(commandSign: CommandSign) {
    fileConfiguration.set("${commandSign.id}.location", commandSign.location)
    fileConfiguration.set("${commandSign.id}.command", commandSign.command)
    saveCommandSigns()
}

fun removeCommandSign(commandSign: CommandSign) {
    fileConfiguration.set("${commandSign.id}.location", null)
    fileConfiguration.set("${commandSign.id}.command", null)
    fileConfiguration.set("${commandSign.id}", null)
    saveCommandSigns()
}

fun commandSignById(id: Int): CommandSign? {
    val location = fileConfiguration.getLocation("$id.location") ?: return null
    val command = fileConfiguration.getString("$id.command") ?: return null

    return CommandSign(id, location, command)
}

fun commandSignByLocation(location: Location): CommandSign? {
    for (commandSign in commandSigns()) {
        if (commandSign.location != location) continue

        return commandSign
    }

    return null
}

fun commandSigns(): ArrayList<CommandSign> {
    val commandSignList = arrayListOf<CommandSign>()

    for (key in fileConfiguration.getKeys(false)) {
        val id = key.toIntOrNull() ?: continue

        val commandSign = commandSignById(id) ?: continue

        commandSignList.add(commandSign)
    }

    return commandSignList
}

fun newCommandSignId(oldId: Int = 0): Int {
    return if (commandSignById(oldId) == null) {
        oldId
    } else {
        newCommandSignId(oldId + 1)
    }
}

fun reloadCommandSigns() {
    fileConfiguration = YamlConfiguration.loadConfiguration(commandSignFile)
    saveCommandSigns()
}

private fun saveCommandSigns() {
    fileConfiguration.save(commandSignFile)
}