package de.dqmme.andimaru.command

import de.dqmme.andimaru.manager.message
import de.dqmme.andimaru.manager.reloadCommandSigns
import de.dqmme.andimaru.manager.reloadMessages
import de.dqmme.andimaru.util.reloadCoins
import net.axay.kspigot.main.KSpigotMainInstance
import org.bukkit.command.CommandSender
import org.bukkit.command.defaults.BukkitCommand

class CommunityCommand : BukkitCommand("community") {
    private val server = KSpigotMainInstance.server

    init {
        permission = "community.command.community"
        server.commandMap.register("community", this)
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        if (!sender.hasPermission("community.command.community") && !sender.hasPermission("community.*")) {
            sender.sendMessage(
                message("no_permissions")
                    .replace("\${needed_permission}", "community.command.community")
            )
            return false
        }

        if (args.size != 1) {
            sender.sendMessage(
                message("invalid_usage")
                    .replace("\${command_usage}", "/community <reload>")
            )
            return false
        }

        when (args[0].lowercase()) {
            "reload" -> {
                reloadCoins()
                reloadCommandSigns()
                reloadMessages()

                sender.sendMessage(message("files_reloaded"))
            }
            else -> {
                sender.sendMessage(
                    message("invalid_usage")
                        .replace("\${command_usage}", "/community <reload>")
                )
            }
        }

        return true
    }

    override fun tabComplete(sender: CommandSender, alias: String, args: Array<out String>): MutableList<String> {
        if (args.size == 1) return mutableListOf("reload")

        return mutableListOf()
    }
}