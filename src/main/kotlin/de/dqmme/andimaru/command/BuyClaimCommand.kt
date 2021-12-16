package de.dqmme.andimaru.command

import de.dqmme.andimaru.manager.buyClaimFromClaim
import de.dqmme.andimaru.manager.registerBuyClaim
import de.dqmme.andimaru.manager.removeBuyClaim
import de.dqmme.andimaru.util.coins
import de.dqmme.andimaru.manager.message
import de.dqmme.andimaru.util.removeCoins
import de.dqmme.andimaru.util.replace
import me.ryanhamshire.GriefPrevention.GriefPrevention
import net.axay.kspigot.main.KSpigotMainInstance
import org.bukkit.command.CommandSender
import org.bukkit.command.defaults.BukkitCommand
import org.bukkit.entity.Player

class BuyClaimCommand : BukkitCommand("buyclaim") {
    private val server = KSpigotMainInstance.server

    init {
        server.commandMap.register("buyclaim", this)
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(message("not_a_player"))
            return false
        }

        if (args.isEmpty()) {
            val claim = GriefPrevention.instance.dataStore.getClaimAt(sender.location, true, null)

            if (claim == null) {
                sender.sendMessage(message("claim_not_found"))
                return false
            }

            val buyClaim = buyClaimFromClaim(claim)

            if (buyClaim == null) {
                sender.sendMessage(message("buyclaim_not_found"))
                return false
            }

            val price = buyClaim.price

            if (sender.coins() < price) {
                sender.sendMessage(message("not_enough_coins"))
                return false
            }

            sender.removeCoins(price)

            GriefPrevention.instance.dataStore.changeClaimOwner(claim, sender.uniqueId)

            removeBuyClaim(buyClaim)

            sender.sendMessage(
                message("buyclaim_bought")
                    .replace("\${price}", price)
                    .replace("\${player_coins}", sender.coins())
            )
        } else if (args.size == 1) {
            if (!sender.hasPermission("community.command.buyclaim") && !sender.hasPermission("community.*")) {
                sender.sendMessage(
                    message("no_permissions")
                        .replace("\${needed_permission}", "community.command.buyclaim")
                )
                return false
            }

            if (args[0].lowercase() != "remove") {
                sender.sendMessage(
                    message("invalid_usage")
                        .replace("\${command_usage}", "/buyclaim remove")
                )
                return false
            }

            val claim = GriefPrevention.instance.dataStore.getClaimAt(sender.location, true, null)

            if (claim == null) {
                sender.sendMessage(message("claim_not_found"))
                return false
            }

            val buyClaim = buyClaimFromClaim(claim)

            if (buyClaim == null) {
                sender.sendMessage(message("buyclaim_not_found"))
                return false
            }

            removeBuyClaim(buyClaim)

            sender.sendMessage(message("buyclaim_removed"))
        } else if (args.size == 2) {
            if (!sender.hasPermission("community.command.buyclaim") && !sender.hasPermission("community.*")) {
                sender.sendMessage(
                    message("no_permissions")
                        .replace("\${needed_permission}", "community.command.buyclaim")
                )
                return false
            }

            if (args[0].lowercase() == "create") {
                sender.sendMessage(
                    message("invalid_usage")
                        .replace("\${command_usage}", "/buyclaim create <price>")
                )
                return false
            }

            val claim = GriefPrevention.instance.dataStore.getClaimAt(sender.location, true, null)

            if (claim == null) {
                sender.sendMessage(message("claim_not_found"))
            }

            val price = args[1].toDoubleOrNull()

            if (price == null) {
                sender.sendMessage(
                    message("invalid_usage")
                        .replace("\${command_usage}", "/buyclaim create <price>")
                )
                return false
            }

            registerBuyClaim(claim, price)

            sender.sendMessage(
                message("buyclaim_registered")
                    .replace("\${price}", price)
            )
        }
        return true
    }

    override fun tabComplete(sender: CommandSender, alias: String, args: Array<out String>): MutableList<String> {
        if (!sender.hasPermission("community.command.buyclaim") || !sender.hasPermission("community.*")) {
            return mutableListOf()
        }

        if (args.size == 1) {
            return mutableListOf("create", "remove")
        }

        return mutableListOf()
    }
}