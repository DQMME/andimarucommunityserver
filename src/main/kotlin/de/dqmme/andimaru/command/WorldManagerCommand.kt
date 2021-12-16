package de.dqmme.andimaru.command

import de.dqmme.andimaru.enums.WorldType
import de.dqmme.andimaru.util.message
import net.axay.kspigot.main.KSpigotMainInstance
import org.bukkit.WorldCreator
import org.bukkit.command.CommandSender
import org.bukkit.command.defaults.BukkitCommand
import org.bukkit.entity.Player
import java.io.File
import java.nio.file.Files
import java.nio.file.Path

class WorldManagerCommand : BukkitCommand("worldmanager") {
    private val server = KSpigotMainInstance.server

    init {
        aliases = listOf("wm")
        permission = "community.command.worldmanager"
        server.commandMap.register("worldmanager", this)
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        if (!sender.hasPermission("community.command.worldmanager") && !sender.hasPermission("community.*")) {
            sender.sendMessage(
                message("no_permissions")
                    .replace("\${needed_permission}", "community.command.worldmanager")
            )
            return false
        }

        if (args.size > 3 || args.size < 2) {
            sender.sendMessage(
                message("invalid_usage")
                    .replace(
                        "\${command_usage}",
                        "/worldmanager <create/remove/teleport/load> <worldname> <normal/end/nether/flat>"
                    )
            )
            return false
        }

        when (args[0].lowercase()) {
            "teleport", "tp" -> {
                if (sender !is Player) {
                    sender.sendMessage(message("not_a_player"))
                    return false
                }

                if (args.size != 2) {
                    sender.sendMessage(
                        message("invalid_usage")
                            .replace("\${command_usage}", "/worldmanager teleport <worldname>")
                    )
                    return false
                }

                val world = server.getWorld(args[1])

                if (world == null) {
                    sender.sendMessage(
                        message("world_not_found")
                            .replace("\${world_name}", args[1])
                    )
                    return false
                }

                sender.teleportAsync(world.spawnLocation)

                sender.sendMessage(
                    message("teleported_to_world")
                        .replace("\${world_name}", world.name)
                )
            }

            "remove", "delete" -> {
                if (args.size != 2) {
                    sender.sendMessage(
                        message("invalid_usage")
                            .replace("\${command_usage}", "/worldmanager remove <worldname>")
                    )
                    return false
                }

                val world = server.getWorld(args[1])

                if (world == null) {
                    sender.sendMessage(
                        message("world_not_found")
                            .replace("\${world_name}", args[1])
                    )
                    return false
                }

                Files.walk(world.worldFolder.toPath())
                    .sorted()
                    .map(Path::toFile)
                    .forEach(File::delete)

                sender.sendMessage(
                    message("world_removed")
                        .replace("\${world_name}", world.name)
                )
            }

            "create" -> {
                if (args.size != 3) {
                    sender.sendMessage(
                        message("invalid_usage")
                            .replace("\${command_usage}", "/worldmanager create <worldname> <normal/nether/end/flat>")
                    )
                }

                val worldName = args[1]

                val worldType = WorldType.parse(args[2])

                val worldCreator = WorldCreator(worldName)

                worldCreator.environment(worldType.environment)

                if (worldType == WorldType.FLAT) {
                    worldCreator.type(org.bukkit.WorldType.FLAT)
                }

                server.createWorld(worldCreator)

                sender.sendMessage(
                    message("world_created")
                        .replace("\${world_name}", worldName)
                )
            }

            "load" -> {
                if (args.size != 2) {
                    sender.sendMessage(
                        message("invalid_usage")
                            .replace("\${command_usage}", "/worldmanager load <worldname>")
                    )
                }

                val worldName = WorldCreator(args[1]).createWorld()?.name ?: return false

                sender.sendMessage(
                    message("world_loaded")
                        .replace("\${world_name}", worldName)
                )
            }

            else -> {
                sender.sendMessage(
                    message("invalid_usage")
                        .replace(
                            "\${command_usage}",
                            "/worldmanager <create/remove/teleport/load> <worldname> <normal/end/nether/flat>"
                        )
                )
            }
        }

        return true
    }

    override fun tabComplete(sender: CommandSender, alias: String, args: Array<out String>): MutableList<String> {
        if (args.size == 1) {
            return mutableListOf("create", "delete", "remove", "teleport", "tp", "load")
        }

        if (args.size == 2) {
            val worlds = server.worlds
            val worldNames = mutableListOf<String>()

            for (world in worlds) {
                worldNames.add(world.name)
            }

            return when (args[0].lowercase()) {
                "delete", "remove", "teleport", "tp" -> {
                    worldNames
                }

                else -> {
                    mutableListOf()
                }
            }
        }

        if (args.size == 3) {
            return when (args[0].lowercase()) {
                "create" -> {
                    mutableListOf("normal", "end", "nether", "flat")
                }

                else -> {
                    mutableListOf()
                }
            }
        }

        return mutableListOf()
    }
}