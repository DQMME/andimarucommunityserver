package de.dqmme.andimaru.command

import de.dqmme.andimaru.listener.CommandSignListener
import de.dqmme.andimaru.manager.message
import net.axay.kspigot.main.KSpigotMainInstance
import org.bukkit.command.CommandSender
import org.bukkit.command.defaults.BukkitCommand
import org.bukkit.entity.Player


class CommandSignCommand : BukkitCommand("commandsign") {
    private val server = KSpigotMainInstance.server

    init {
        permission = "community.command.commandsign"
        server.commandMap.register("commandsign", this)
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(message("not_a_player"))
            return false
        }

        if (!sender.hasPermission("community.commandsign") && !sender.hasPermission("community.*")) {
            sender.sendMessage(
                message("no_permissions")
                    .replace("\${needed_permission}", "community.commandsign")
            )
            return false
        }

        if (args.isEmpty()) {
            sender.sendMessage(
                message("invalid_usage")
                    .replace("\${command_usage}", "/commandsign <create/remove> <command>")
            )

            return false
        }

        when (args[0].lowercase()) {
            "create" -> {
                if (args.size < 2) {
                    sender.sendMessage(
                        message("invalid_usage")
                            .replace("\${command_usage}", "/commandsign add <command>")
                    )
                    return false
                }

                val commandArgs = args.copyOfRange(1, args.size)

                var command = ""

                for (arg in commandArgs) {
                    command += arg + ""
                }

                command = command.dropLast(1)

                CommandSignListener.createPlayers[sender.uniqueId] = command
                sender.sendMessage(message("commandsign_setup_started"))
            }

            "remove" -> {
                if (args.size != 1) {
                    sender.sendMessage(
                        message("invalid_usage")
                            .replace("\${command_usage}", "/commandsign remove")
                    )
                    return false
                }

                CommandSignListener.removePlayers.add(sender.uniqueId)
                sender.sendMessage(message("commandsign_setup_started"))
            }

            else -> {
                sender.sendMessage(
                    message("invalid_usage")
                        .replace("\${command_usage}", "/commandsign <create/remove> <command>")
                )
            }
        }
        return true
    }

    override fun tabComplete(sender: CommandSender, alias: String, args: Array<out String>): MutableList<String> {
        if (args.size == 1) return mutableListOf("create", "remove")

        return mutableListOf()
    }
}