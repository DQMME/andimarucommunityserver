package de.dqmme.andimaru.npc.impl

import com.github.juliarn.npc.event.PlayerNPCInteractEvent
import de.dqmme.andimaru.npc.NPC
import de.dqmme.andimaru.npc.teleportNPCData
import de.dqmme.andimaru.manager.spawnLocation
import net.axay.kspigot.main.KSpigotMainInstance
import net.kyori.adventure.text.Component
import net.kyori.adventure.title.Title
import org.bukkit.ChatColor
import org.bukkit.Location
import org.bukkit.Sound

object MiddleAgeNPC : NPC() {
    override val id: String = "middle_age_npc"

    private val data = teleportNPCData(id)

    override val displayName: String = data.displayName ?: "§cName not set"
    override val location: Location =
        data.location ?: KSpigotMainInstance.server.worlds[0].spawnLocation.add(14.0, 0.0, 0.0)
    override val imitatePlayer: Boolean = data.imitatePlayer
    override val lookAtPlayer: Boolean = data.lookAtPlayer
    override val isTeleportNPC: Boolean = true
    override var finalNPC: com.github.juliarn.npc.NPC? = null

    override fun onInteract(event: PlayerNPCInteractEvent) {
        val player = event.player

        player.teleportAsync(data.teleportLocation ?: spawnLocation()!!)
        player.playSound(player.location, Sound.ENTITY_ENDERMAN_TELEPORT, 20.toFloat(), 1.toFloat())

        val title = FairytaleNPC.data.title ?: "§cData not set"

        player.showTitle(Title.title(Component.text(ChatColor.translateAlternateColorCodes('&', title)), Component.text("")))
    }
}