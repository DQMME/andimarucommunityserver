package de.dqmme.andimaru.command

import de.dqmme.andimaru.manager.message
import de.dqmme.andimaru.manager.spawnLocation
import net.axay.kspigot.main.KSpigotMainInstance
import org.bukkit.command.CommandSender
import org.bukkit.command.defaults.BukkitCommand
import org.bukkit.entity.Player

class SpawnCommand : BukkitCommand("spawn") {
    private val server = KSpigotMainInstance.server

    init {
        server.commandMap.register("spawn", this)
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(message("not_a_player"))
            return false
        }

        val spawn = spawnLocation()

        if (spawn == null) {
            sender.sendMessage(message("spawn_not_set"))
            return false
        }

        sender.teleportAsync(spawn)
        sender.sendMessage(message("teleported_to_spawn"))
        return true
    }
}