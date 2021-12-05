package de.dqmme.andimaru.command

import de.dqmme.andimaru.util.*
import net.axay.kspigot.main.KSpigotMainInstance
import org.bukkit.command.CommandSender
import org.bukkit.command.defaults.BukkitCommand
import org.bukkit.entity.Player

class CoinsCommand : BukkitCommand("coins") {
    private val server = KSpigotMainInstance.server

    init {
        server.commandMap.register("coins", this)
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        if (args.isEmpty()) {
            if (sender !is Player) {
                sender.sendMessage(message("not_a_player"))
                return false
            }

            val coins = sender.coins()

            sender.sendMessage(
                message("your_coins")
                    .replace("\${player_coins}", coins)
            )
        } else if (args.size == 1) {
            val player = server.getPlayer(args[0])

            if (player == null) {
                sender.sendMessage(
                    message("coins")
                        .replace("\${player_name}", args[0])
                )
                return false
            }

            sender.sendMessage(
                message("coins")
                    .replace("\${player_name}", player.name)
                    .replace("\${player_coins}", player.coins())
            )
        } else if (args.size == 3) {
            val player = server.getPlayer(args[0])

            if (player == null) {
                sender.sendMessage(
                    message("coins")
                        .replace("\${player_name}", args[0])
                )
                return false
            }

            val coins = args[2].toDoubleOrNull()

            if (coins == null) {
                sender.sendMessage(
                    message("invalid_usage")
                        .replace("\${command_usage}", "/coins <player> <set/add/remove> <coins>")
                )
                return false
            }

            when (args[1].lowercase()) {
                "set" -> {
                    player.setCoins(coins)

                    val newCoins = player.coins()

                    player.sendMessage(
                        message("your_coins_set")
                            .replace("\${new_coins}", newCoins)
                    )

                    sender.sendMessage(
                        message("coins_set")
                            .replace("\${player_name}", player.name)
                            .replace("\${new_coins}", newCoins)
                    )
                }

                "add" -> {
                    player.addCoins(coins)

                    val newCoins = player.coins()

                    player.sendMessage(
                        message("your_coins_added")
                            .replace("\${added_coins}", coins)
                            .replace("\${new_coins}", newCoins)
                    )

                    sender.sendMessage(
                        message("coins_added")
                            .replace("\${player_name}", player.name)
                            .replace("\${added_coins}", coins)
                            .replace("\${new_coins}", newCoins)
                    )
                }

                "remove" -> {
                    player.removeCoins(coins)

                    val newCoins = player.coins()

                    player.sendMessage(
                        message("your_coins_removed")
                            .replace("\${removed_coins}", coins)
                            .replace("\${new_coins}", newCoins)
                    )

                    sender.sendMessage(
                        message("coins_removed")
                            .replace("\${player_name}", player.name)
                            .replace("\${removed_coins}", coins)
                            .replace("\${new_coins}", newCoins)
                    )
                }

                else -> {
                    sender.sendMessage(
                        message("invalid_usage")
                            .replace("\${command_usage}", "/coins <player> <set/add/remove> <coins>")
                    )
                    return false
                }
            }
        } else {
            sender.sendMessage(
                message("invalid_usage")
                    .replace("\${command_usage}", "/coins <player> <set/add/remove> <coins>")
            )
        }
        return true
    }

    override fun tabComplete(sender: CommandSender, alias: String, args: Array<out String>): MutableList<String> {
        val players = mutableListOf<String>()
        server.onlinePlayers.forEach {
            players.add(it.name)
        }

        if (!sender.hasPermission("communtiy.command.coins.admin") && !sender.hasPermission("community.*")) {
            if (args.size == 1) return players

            return mutableListOf()
        }

        if (args.size == 1) return players

        if (args.size == 2) return mutableListOf("add", "set", "remove")

        return mutableListOf()
    }
}