package de.dqmme.andimaru.command

import de.dqmme.andimaru.manager.message
import net.axay.kspigot.main.KSpigotMainInstance
import org.bukkit.GameMode
import org.bukkit.command.CommandSender
import org.bukkit.command.defaults.BukkitCommand
import org.bukkit.entity.Player

class GamemodeCommand : BukkitCommand("gamemode") {
    private val server = KSpigotMainInstance.server

    init {
        aliases = listOf("gm")
        permission = "community.command.gamemode"
        server.commandMap.register("community", this)
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        if (!sender.hasPermission("community.command.gamemode") && !sender.hasPermission("community.*")) {
            sender.sendMessage(
                message("no_permissions")
                    .replace("\${needed_permission}", "community.command.gamemode")
            )
            return false
        }

        if (args.size == 1) {
            if (sender !is Player) {
                sender.sendMessage(message("not_a_player"))
                return false
            }

            when (args[0].lowercase()) {
                "0", "s", "survival" -> {
                    sender.gameMode = GameMode.SURVIVAL

                    sender.sendMessage(
                        message("your_gamemode_set")
                            .replace("\${gamemode}", "Survival")
                    )
                }

                "1", "c", "creative" -> {
                    sender.gameMode = GameMode.CREATIVE

                    sender.sendMessage(
                        message("your_gamemode_set")
                            .replace("\${gamemode}", "Creative")
                    )
                }

                "2", "a", "adventure" -> {
                    sender.gameMode = GameMode.ADVENTURE

                    sender.sendMessage(
                        message("your_gamemode_set")
                            .replace("\${gamemode}", "Adventure")
                    )
                }

                "3", "spec", "spectator" -> {
                    sender.gameMode = GameMode.SPECTATOR

                    sender.sendMessage(
                        message("your_gamemode_set")
                            .replace("\${gamemode}", "Spectator")
                    )
                }

                else -> {
                    sender.sendMessage(
                        message("invalid_usage")
                            .replace(
                                "\${command_usage}",
                                "/gamemode <0/s/survival, 1/c/creative, 2/a/adventure, 3/spec/spectator>"
                            )
                    )
                }
            }
        } else if (args.size == 2) {
            val target = server.getPlayer(args[1])

            if (target == null) {
                sender.sendMessage(
                    message("player_not_found")
                        .replace("\${player_name}", args[1])
                )
                return false
            }

            when (args[0].lowercase()) {
                "0", "s", "survival" -> {
                    target.gameMode = GameMode.SURVIVAL

                    target.sendMessage(
                        message("your_gamemode_set")
                            .replace("\${gamemode}", "Survival")
                    )

                    sender.sendMessage(
                        message("gamemode_set")
                            .replace("\${player_name}", target.name)
                            .replace("\${gamemode}", "Survival")
                    )
                }

                "1", "c", "creative" -> {
                    target.gameMode = GameMode.CREATIVE

                    target.sendMessage(
                        message("your_gamemode_set")
                            .replace("\${gamemode}", "Creative")
                    )

                    sender.sendMessage(
                        message("gamemode_set")
                            .replace("\${player_name}", target.name)
                            .replace("\${gamemode}", "Survival")
                    )
                }

                "2", "a", "adventure" -> {
                    target.gameMode = GameMode.ADVENTURE

                    target.sendMessage(
                        message("your_gamemode_set")
                            .replace("\${gamemode}", "Adventure")
                    )

                    sender.sendMessage(
                        message("gamemode_set")
                            .replace("\${player_name}", target.name)
                            .replace("\${gamemode}", "Survival")
                    )
                }

                "3", "spec", "spectator" -> {
                    target.gameMode = GameMode.SPECTATOR

                    target.sendMessage(
                        message("your_gamemode_set")
                            .replace("\${gamemode}", "Spectator")
                    )

                    sender.sendMessage(
                        message("gamemode_set")
                            .replace("\${player_name}", target.name)
                            .replace("\${gamemode}", "Survival")
                    )
                }

                else -> {
                    sender.sendMessage(
                        message("invalid_usage")
                            .replace(
                                "\${command_usage}",
                                "/gamemode <0/s/survival, 1/c/creative, 2/a/adventure, 3/spec/spectator>"
                            )
                    )
                }
            }
        }
        return true
    }

    override fun tabComplete(sender: CommandSender, alias: String, args: Array<out String>): MutableList<String> {
        if (args.size == 1) return mutableListOf(
            "0",
            "s",
            "survival",
            "1",
            "c",
            "creative",
            "2",
            "a",
            "adventure",
            "3",
            "spec",
            "spectator"
        )

        if (args.size == 2) {
            val players = mutableListOf<String>()
            server.onlinePlayers.forEach {
                players.add(it.name)
            }

            return players
        }

        return mutableListOf()
    }
}