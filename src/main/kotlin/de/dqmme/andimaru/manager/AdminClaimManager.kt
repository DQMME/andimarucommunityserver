package de.dqmme.andimaru.manager

import de.dqmme.andimaru.npc.impl.FairytaleNPC
import de.dqmme.andimaru.npc.impl.FutureNPC
import de.dqmme.andimaru.npc.impl.MiddleAgeNPC
import me.ryanhamshire.GriefPrevention.Claim
import me.ryanhamshire.GriefPrevention.GriefPrevention
import net.axay.kspigot.main.KSpigotMainInstance
import net.axay.kspigot.runnables.task
import net.kyori.adventure.text.Component
import net.kyori.adventure.title.Title
import org.bukkit.ChatColor
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File
import java.util.*

private val adminClaimFile = File(KSpigotMainInstance.dataFolder, "adminclaims.yml")
private lateinit var fileConfiguration: FileConfiguration

fun setSpawnClaim(claim: Claim) {
    fileConfiguration.set("spawn", claim.id)
    saveAdminClaims()
}

fun setForestClaim(claim: Claim) {
    fileConfiguration.set("forest", claim.id)
    saveAdminClaims()
}

fun setFutureClaim(claim: Claim) {
    fileConfiguration.set("future", claim.id)
    saveAdminClaims()
}

fun setCityClaim(claim: Claim) {
    fileConfiguration.set("city", claim.id)
    saveAdminClaims()
}

fun spawnClaimId() = fileConfiguration.getLong("spawn")

fun forestClaimId() = fileConfiguration.getLong("forest")

fun futureClaimId() = fileConfiguration.getLong("future")

fun cityClaimId() = fileConfiguration.getLong("city")

fun reloadAdminClaims() {
    fileConfiguration = YamlConfiguration.loadConfiguration(adminClaimFile)
    saveAdminClaims()
}

private fun saveAdminClaims() {
    fileConfiguration.save(adminClaimFile)
}

class AdminClaimManager {
    init {
        checkPlayers()
    }

    private val inSpawn = arrayListOf<UUID>()
    private val inForest = arrayListOf<UUID>()
    private val inFuture = arrayListOf<UUID>()
    private val inCity = arrayListOf<UUID>()

    private fun checkPlayers() {
        val server = KSpigotMainInstance.server

        task(true, 0, 20) {
            for (player in server.onlinePlayers) {
                val uuid = player.uniqueId
                val claim = GriefPrevention.instance.dataStore.getClaimAt(player.location, false, null)

                if (claim != null && claim.id == spawnClaimId()) {
                    if (!inSpawn.contains(uuid)) inSpawn.add(uuid)
                }

                if (inSpawn.contains(uuid)) {
                    if (claim == null || claim.id != spawnClaimId()) {
                        if (claim == null) {
                            player.showTitle(Title.title(Component.text("§aBauwelt"), Component.text("")))
                        } else if (claim.id != forestClaimId()
                            && claim.id != futureClaimId()
                            && claim.id != cityClaimId()
                        ) {
                            player.showTitle(Title.title(Component.text("§aBauwelt"), Component.text("")))
                        }
                        inSpawn.remove(uuid)
                        continue
                    }
                }

                if (claim == null) {
                    inForest.remove(uuid)
                    inFuture.remove(uuid)
                    inCity.remove(uuid)
                    continue
                }

                if (claim.id == forestClaimId()) {
                    if (!inForest.contains(uuid)) {
                        player.showTitle(
                            Title.title(
                                Component.text(
                                    ChatColor.translateAlternateColorCodes(
                                        '&',
                                        FairytaleNPC.data.title ?: "§cTitle not set"
                                    )
                                ),
                                Component.text("")
                            )
                        )
                        inForest.add(uuid)
                    }
                } else {
                    inForest.remove(uuid)
                }

                if (claim.id == futureClaimId()) {
                    if (!inFuture.contains(uuid)) {
                        player.showTitle(
                            Title.title(
                                Component.text(
                                    ChatColor.translateAlternateColorCodes(
                                        '&',
                                        FutureNPC.data.title ?: "§cTitle not set"
                                    )
                                ),
                                Component.text("")
                            )
                        )
                        inFuture.add(uuid)
                    }
                } else {
                    inFuture.remove(uuid)
                }

                if (claim.id == cityClaimId()) {
                    if (!inCity.contains(uuid)) {
                        player.showTitle(
                            Title.title(
                                Component.text(
                                    ChatColor.translateAlternateColorCodes(
                                        '&',
                                        MiddleAgeNPC.data.title ?: "§cTitle not set"
                                    )
                                ),
                                Component.text("")
                            )
                        )
                        inCity.add(uuid)
                    }
                } else {
                    inCity.remove(uuid)
                }
            }
        }
    }
}