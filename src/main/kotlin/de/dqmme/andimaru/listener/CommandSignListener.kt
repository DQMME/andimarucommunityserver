package de.dqmme.andimaru.listener

import de.dqmme.andimaru.dataclass.CommandSign
import de.dqmme.andimaru.manager.*
import de.dqmme.andimaru.util.isSign
import de.dqmme.andimaru.util.replace
import net.axay.kspigot.event.listen
import org.bukkit.event.block.BlockBreakEvent
import java.util.*

class CommandSignListener {
    init {
        signCreateListener()
        signRemoveListener()
    }

    companion object {
        val createPlayers = hashMapOf<UUID, String>()
        val removePlayers = arrayListOf<UUID>()
    }

    private fun signCreateListener() = listen<BlockBreakEvent> { event ->
        val player = event.player

        if (!createPlayers.containsKey(player.uniqueId)) {
            val commandSign = commandSignByLocation(event.block.location) ?: return@listen

            removeCommandSign(commandSign)

            player.sendMessage(message("commandsign_removed"))
            return@listen
        }

        if (!event.block.type.isSign()) {
            createPlayers.remove(player.uniqueId)
            player.sendMessage(message("not_a_sign"))
            player.sendMessage(message("commandsign_setup_cancelled"))
            return@listen
        }

        val location = event.block.location
        val command = createPlayers[player.uniqueId]!!

        val commandSign = CommandSign(newCommandSignId(), location, command)

        registerCommandSign(commandSign)

        createPlayers.remove(player.uniqueId)

        val x = location.blockX
        val y = location.blockY
        val z = location.blockZ

        player.sendMessage(
            message("commandsign_created")
                .replace("\${command}", command)
                .replace("\${x}", x)
                .replace("\${y}", y)
                .replace("\${z}", z)
        )

        event.isCancelled = true
    }

    private fun signRemoveListener() = listen<BlockBreakEvent> { event ->
        val player = event.player

        if (!removePlayers.contains(player.uniqueId)) return@listen

        if (!event.block.type.isSign()) {
            removePlayers.remove(player.uniqueId)
            player.sendMessage(message("not_a_sign"))
            player.sendMessage(message("commandsign_setup_cancelled"))
            return@listen
        }

        val commandSign = commandSignByLocation(event.block.location)

        if (commandSign == null) {
            player.sendMessage(message("not_a_commandsign"))
            return@listen
        }

        removePlayers.remove(player.uniqueId)
        removeCommandSign(commandSign)
        player.sendMessage(message("commandsign_removed"))
    }
}