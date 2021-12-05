package de.dqmme.andimaru.command

import de.dqmme.andimaru.util.home
import de.dqmme.andimaru.util.homeGUI
import de.dqmme.andimaru.util.message
import net.axay.kspigot.gui.openGUI
import net.axay.kspigot.main.KSpigotMainInstance
import org.bukkit.command.CommandSender
import org.bukkit.command.defaults.BukkitCommand
import org.bukkit.entity.Player

class HomeCommand : BukkitCommand("home") {
    private val server = KSpigotMainInstance.server

    init {
        server.commandMap.register("home", this)
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(message("not_a_player"))
            return false
        }

        if (args.isEmpty()) {
            sender.openGUI(sender.homeGUI())
        } else if(args.size == 1) {
            val homeNumber = args[0].toIntOrNull()

            if(homeNumber == null) {
                sender.sendMessage(
                    message("invalid_usage")
                        .replace("\${command_usage}", "/home <home>")
                )
                return false
            }

            val home = sender.home(homeNumber)

            if(home == null) {
                sender.sendMessage(message("home_not_found"))
                return false
            }

            sender.teleportAsync(home.location)

            sender.sendMessage(
                message("teleported_to_home")
                    .replace("\${home_number}", "$homeNumber.")
            )
        } else {
            sender.sendMessage(
                message("invalid_usage")
                    .replace("\${command_usage}", "/home <home>")
            )
        }

        return true
    }

    override fun tabComplete(sender: CommandSender, alias: String, args: Array<out String>): MutableList<String> {
        if (sender !is Player) return mutableListOf()

        if (args.size == 1) {
            val homes = mutableListOf<String>()

            if (sender.home(1) != null) homes.add("1")
            if (sender.home(2) != null) homes.add("2")
            if (sender.home(3) != null) homes.add("3")
            if (sender.home(4) != null) homes.add("4")
            if (sender.home(5) != null) homes.add("5")

            return homes
        }

        return mutableListOf()
    }
}