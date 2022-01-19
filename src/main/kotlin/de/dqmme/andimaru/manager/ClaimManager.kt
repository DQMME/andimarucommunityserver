package de.dqmme.andimaru.manager

import de.dqmme.andimaru.util.hasFlyingEnabled
import me.ryanhamshire.GriefPrevention.Claim
import me.ryanhamshire.GriefPrevention.GriefPrevention
import net.axay.kspigot.main.KSpigotMainInstance
import net.axay.kspigot.runnables.task
import org.bukkit.GameMode
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

fun Claim.builders(): List<String> {
    val claimFile = File(GriefPrevention.instance.dataFolder, "/ClaimData/$id")
    val claimConf = YamlConfiguration.loadConfiguration(claimFile)
    return claimConf.getStringList("Builders")
}

class ClaimManager {
    init {
        check()
    }

    private fun check() = task(true, 0, 20) {
        for (player in KSpigotMainInstance.server.onlinePlayers) {
            if (player.gameMode != GameMode.SURVIVAL) continue

            if (!player.hasFlyingEnabled()) continue

            val claim = GriefPrevention.instance.dataStore.getClaimAt(player.location, false, null)

            if (claim == null) {
                player.allowFlight = false
                player.isFlying = false
            } else {
                println(claim.builders())
                if (!claim.managers.contains(player.uniqueId.toString()) && claim.ownerID != player.uniqueId && !claim.builders()
                        .contains(player.uniqueId.toString())
                ) continue

                player.allowFlight = true
                player.isFlying = true
            }
        }
    }
}