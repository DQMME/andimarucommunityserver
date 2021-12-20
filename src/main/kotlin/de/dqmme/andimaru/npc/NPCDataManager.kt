package de.dqmme.andimaru.npc

import de.dqmme.andimaru.dataclass.NPCData
import de.dqmme.andimaru.dataclass.Skin
import de.dqmme.andimaru.dataclass.TeleportNPCData
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
    val skinSignature = fileConfiguration.getString("$id.skin_signature") ?: ""
    val skinValue = fileConfiguration.getString("$id.skin_value") ?: ""

    return NPCData(displayName, location, imitatePlayer, lookAtPlayer, Skin(skinSignature, skinValue))
}

fun setNPCData(id: String, npcData: NPCData) {
    fileConfiguration.set("$id.display_name", npcData.displayName)
    fileConfiguration.set("$id.location", npcData.location)
    fileConfiguration.set("$id.imitate_player", npcData.imitatePlayer)
    fileConfiguration.set("$id.look_at_player", npcData.lookAtPlayer)
    fileConfiguration.set("$id.skin_signature", npcData.skin.signature)
    fileConfiguration.set("$id.skin_value", npcData.skin.value)
    saveNPCDataFile()
}

fun teleportNPCData(id: String): TeleportNPCData {
    val displayName = fileConfiguration.getString("$id.display_name")
    val location = fileConfiguration.getLocation("$id.location")
    val imitatePlayer = fileConfiguration.getBoolean("$id.imitate_player")
    val lookAtPlayer = fileConfiguration.getBoolean("$id.look_at_player")
    val teleportLocation = fileConfiguration.getLocation("$id.teleport_location")
    val title = fileConfiguration.getString("$id.title")
    val skinSignature = fileConfiguration.getString("$id.skin_signature") ?: ""
    val skinValue = fileConfiguration.getString("$id.skin_value") ?: ""

    return TeleportNPCData(displayName, location, imitatePlayer, lookAtPlayer, teleportLocation, title, Skin(skinSignature, skinValue))
}

fun setTeleportNPCData(id: String, teleportNPCData: TeleportNPCData) {
    fileConfiguration.set("$id.display_name", teleportNPCData.displayName)
    fileConfiguration.set("$id.location", teleportNPCData.location)
    fileConfiguration.set("$id.imitate_player", teleportNPCData.imitatePlayer)
    fileConfiguration.set("$id.look_at_player", teleportNPCData.lookAtPlayer)
    fileConfiguration.set("$id.teleport_location", teleportNPCData.teleportLocation)
    fileConfiguration.set("$id.title", teleportNPCData.title)
    fileConfiguration.set("$id.skin_signature", teleportNPCData.skin.signature)
    fileConfiguration.set("$id.skin_value", teleportNPCData.skin.value)
    saveNPCDataFile()
}

fun reloadNPCData() {
    fileConfiguration = YamlConfiguration.loadConfiguration(npcDataFile)
    saveNPCDataFile()
}

private fun saveNPCDataFile() {
    fileConfiguration.save(npcDataFile)
}