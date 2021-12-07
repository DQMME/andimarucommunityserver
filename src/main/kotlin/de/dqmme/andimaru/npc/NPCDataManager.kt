package de.dqmme.andimaru.npc

import de.dqmme.andimaru.dataclass.NPCData
import net.axay.kspigot.main.KSpigotMainInstance
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

private val npcDataFile = File(KSpigotMainInstance.dataFolder, "npc-data.yml")
private lateinit var fileConfiguration: FileConfiguration

fun npcData(id: String): NPCData {
    val displayName = fileConfiguration.getString("$id.display_name")
    val location = fileConfiguration.getLocation("$id.location")
    val imitatePlayer = fileConfiguration.getBoolean("$id.imitate_player")
    val lookAtPlayer = fileConfiguration.getBoolean("$id.look_at_player")

    return NPCData(displayName, location, imitatePlayer, lookAtPlayer)
}

fun setNPCData(id: String, npcData: NPCData) {
    fileConfiguration.set("$id.display_name", npcData.displayName)
    fileConfiguration.set("$id.location", npcData.location)
    fileConfiguration.set("$id.imitate_player", npcData.imitatePlayer)
    fileConfiguration.set("$id.look_at_player", npcData.lookAtPlayer)
    saveNPCDataFile()
}

fun reloadNPCData() {
    fileConfiguration = YamlConfiguration.loadConfiguration(npcDataFile)
    saveNPCDataFile()
}

private fun saveNPCDataFile() {
    fileConfiguration.save(npcDataFile)
}