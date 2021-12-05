package de.dqmme.andimaru.manager

import de.dqmme.andimaru.dataclass.BuyClaim
import me.ryanhamshire.GriefPrevention.Claim
import net.axay.kspigot.main.KSpigotMainInstance
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import java.io.File

private val buyClaimFile = File(KSpigotMainInstance.dataFolder, "buyclaims.yml")
private lateinit var fileConfiguration: FileConfiguration

fun buyClaimFromClaim(claim: Claim): BuyClaim? {
    fileConfiguration.get("${claim.id}.price") ?: return null

    return BuyClaim(claim.id, fileConfiguration.getDouble("${claim.id}.price"))
}

fun registerBuyClaim(claim: Claim, price: Double) {
    fileConfiguration.set("${claim.id}.price", price)
    saveBuyClaimFile()
}

fun removeBuyClaim(buyClaim: BuyClaim) {
    fileConfiguration.set("${buyClaim.claimId}.price", null)
    fileConfiguration.set("${buyClaim.claimId}", null)
    saveBuyClaimFile()
}

fun reloadBuyClaims() {
    fileConfiguration = YamlConfiguration.loadConfiguration(buyClaimFile)
    saveBuyClaimFile()
}

private fun saveBuyClaimFile() {
    fileConfiguration.save(buyClaimFile)
}