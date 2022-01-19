package de.dqmme.andimaru.command

import de.dqmme.andimaru.manager.*
import me.ryanhamshire.GriefPrevention.GriefPrevention
import net.axay.kspigot.main.KSpigotMainInstance
import org.bukkit.command.CommandSender
import org.bukkit.command.defaults.BukkitCommand
import org.bukkit.entity.Player

class AdminClaimCommand : BukkitCommand("adminclaim") {
    init {
        permission = "community.command.adminclaim"
        KSpigotMainInstance.server.commandMap.register("community", this)
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        if(sender !is Player) {
            sender.sendMessage(message("not_a_player"))
            return false
        }

        if (!sender.hasPermission("community.command.adminclaim") && !sender.hasPermission("community.*")) {
            sender.sendMessage(
                message("no_permissions")
                    .replace("\${needed_permission}", "community.command.adminclaim")
            )
            return false
        }

        if (args.size != 2 && args[0] != "set") {
            sender.sendMessage(
                message("invalid_usage")
                    .replace("\${command_usage}", "/adminclaim set <spawn/forest/future/city>")
            )
            return false
        }

        when(args[1].lowercase()) {
            "spawn" -> {
                val claim = GriefPrevention.instance.dataStore.getClaimAt(sender.location, false, null)

                println(claim)

                if(claim == null) {
                    sender.sendMessage(message("claim_not_found"))
                    return false
                }

                setSpawnClaim(claim)
            }

            "forest" -> {
                val claim = GriefPrevention.instance.dataStore.getClaimAt(sender.location, false, null)

                if(claim == null) {
                    sender.sendMessage(message("claim_not_found"))
                    return false
                }

                setForestClaim(claim)
            }

            "future" -> {
                val claim = GriefPrevention.instance.dataStore.getClaimAt(sender.location, false, null)

                if(claim == null) {
                    sender.sendMessage(message("claim_not_found"))
                    return false
                }

                setFutureClaim(claim)
            }

            "city" -> {
                val claim = GriefPrevention.instance.dataStore.getClaimAt(sender.location, false, null)

                if(claim == null) {
                    sender.sendMessage(message("claim_not_found"))
                    return false
                }

                setCityClaim(claim)
            }
        }

        return true
    }

    override fun tabComplete(sender: CommandSender, alias: String, args: Array<out String>): MutableList<String> {
        if(args.size == 1) return mutableListOf("set")

        return mutableListOf()
    }
}