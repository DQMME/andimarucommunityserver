package de.dqmme.andimaru.command

import de.dqmme.andimaru.manager.message
import de.dqmme.andimaru.util.*
import net.axay.kspigot.gui.openGUI
import net.axay.kspigot.main.KSpigotMainInstance
import org.bukkit.command.CommandSender
import org.bukkit.command.defaults.BukkitCommand
import org.bukkit.entity.Player

class SetHomeCommand : BukkitCommand("sethome") {
    private val server = KSpigotMainInstance.server

    init {
        server.commandMap.register("sethome", this)
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(message("not_a_player"))
            return false
        }

        if (args.isEmpty()) {
            sender.openGUI(sender.setHomeGUI())
        } else if (args.size == 1) {
            val homeNumber = args[0].toIntOrNull()

            if (homeNumber == null) {
                sender.sendMessage(
                    message("invalid_usage")
                        .replace("\${command_usage}", "/sethome <home>")
                )
                return false
            }

            if (homeNumber > sender.availableHomes()) {
                sender.sendMessage(message("not_enough_home_slots"))
                return false
            }

            val location = sender.location
            val worldName = location.world.name
            val x = location.blockX
            val y = location.blockY
            val z = location.blockZ

            sender.setHome(homeNumber, location)

            sender.sendMessage(
                message("home_set")
                    .replace("\${home_number}", "$homeNumber.")
                    .replace("\${world}", worldName)
                    .replace("\${x}", x)
                    .replace("\${y}", y)
                    .replace("\${z}", z)
            )
        }

        return true
    }

    override fun tabComplete(sender: CommandSender, alias: String, args: Array<out String>): MutableList<String> {
        if (sender !is Player) return mutableListOf()

        if (args.size == 1) {
            val homeSlotList = mutableListOf<String>()

            homeSlotList.add("1")
            homeSlotList.add("2")

            val availableHomes = sender.availableHomes()

            if (availableHomes >= 3) homeSlotList.add("3")
            if (availableHomes >= 4) homeSlotList.add("4")
            if (availableHomes >= 5) homeSlotList.add("5")

            return homeSlotList
        }

        return mutableListOf()
    }
}