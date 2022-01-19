package de.dqmme.andimaru.listener

import com.comphenix.protocol.PacketType
import com.comphenix.protocol.ProtocolLibrary
import com.comphenix.protocol.events.PacketAdapter
import com.comphenix.protocol.events.PacketEvent
import de.dqmme.andimaru.manager.playerCountText
import net.axay.kspigot.main.KSpigotMainInstance
import org.bukkit.ChatColor

fun onPing() {
    val instance = KSpigotMainInstance
    ProtocolLibrary.getProtocolManager().addPacketListener(object :
        PacketAdapter(params(instance, PacketType.Status.Server.SERVER_INFO).optionAsync()) {
        override fun onPacketSending(event: PacketEvent) {
            val ping = event.packet.serverPings.read(0)
            val onlinePlayers = instance.server.onlinePlayers.size
            val maxPlayers = instance.server.maxPlayers

            ping.versionProtocol = -1
            ping.versionName =
                ChatColor.translateAlternateColorCodes('&', playerCountText() ?: "§7$onlinePlayers§8/§7$maxPlayers")
        }
    })
}