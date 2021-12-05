package de.dqmme.andimaru.command

import de.dqmme.andimaru.util.*
import net.axay.kspigot.main.KSpigotMainInstance
import org.bukkit.command.CommandSender
import org.bukkit.command.defaults.BukkitCommand
import org.bukkit.entity.Player

class PayCommand : BukkitCommand("pay") {
    private val server = KSpigotMainInstance.server

    init {
        server.commandMap.register("pay", this)
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(message("not_a_player"))
            return false
        }

        if (args.size != 2) {
            val target = server.getPlayer(args[0])

            if (target == null) {
                sender.sendMessage(
                    message("player_not_found")
                        .replace("\${player_name}", args[0])
                )
                return false
            }

            val coins = args[1].toIntOrNull()?.toDouble()

            if (coins == null) {
                sender.sendMessage(
                    message("invalid_usage")
                        .replace("\${command_usage}", "/pay <player> <coins>")
                )
                return false
            }

            val playerCoins = sender.coins()

            if (playerCoins < coins) {
                sender.sendMessage(message("not_enough_coins"))
                return false
            }

            sender.removeCoins(coins)

            sender.sendMessage(
                message("coins_sent")
                    .replace("\${coins}", coins)
                    .replace("\${new_coins}", sender.coins())
                    .replace("\${player_name}", target.name)
            )

            target.addCoins(coins)

            target.sendMessage(
                message("coins_received")
                    .replace("\${coins}", coins)
                    .replace("\${new_coins}", target.coins())
                    .replace("\${player_name}", sender.name)
            )
        }
        return true
    }

    override fun tabComplete(sender: CommandSender, alias: String, args: Array<out String>): MutableList<String> {
        if (args.size == 1) {
            val players = mutableListOf<String>()
            server.onlinePlayers.forEach {
                players.add(it.name)
            }

            return players
        }

        return mutableListOf()
    }
}