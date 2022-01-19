package de.dqmme.andimaru.command

import de.dqmme.andimaru.manager.message
import de.dqmme.andimaru.util.replace
import de.dqmme.andimaru.manager.setSpawnLocation
import net.axay.kspigot.main.KSpigotMainInstance
import org.bukkit.command.CommandSender
import org.bukkit.command.defaults.BukkitCommand
import org.bukkit.entity.Player

class SetSpawnCommand : BukkitCommand("setspawn") {
    private val server = KSpigotMainInstance.server

    init {
        server.commandMap.register("community", this)
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        if (!sender.hasPermission("community.command.setspawn") && !sender.hasPermission("community.*")) {
            sender.sendMessage(
                message("no_permissions")
                    .replace("\${needed_permission}", "community.command.setspawn")
            )
            return false
        }

        if (sender !is Player) {
            sender.sendMessage(message("not_a_player"))
            return false
        }

        val location = sender.location
        val worldName = location.world.name
        val x = location.blockX
        val y = location.blockY
        val z = location.blockZ

        setSpawnLocation(location)

        sender.sendMessage(
            message("spawn_set")
                .replace("\${world}", worldName)
                .replace("\${x}", x)
                .replace("\${y}", y)
                .replace("\${z}", z)
        )
        return true
    }
}