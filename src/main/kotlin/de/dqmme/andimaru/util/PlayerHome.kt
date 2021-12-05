package de.dqmme.andimaru.util

import de.dqmme.andimaru.dataclass.Home
import net.axay.kspigot.gui.GUIType
import net.axay.kspigot.gui.Slots
import net.axay.kspigot.gui.kSpigotGUI
import net.axay.kspigot.items.itemStack
import net.axay.kspigot.items.meta
import net.axay.kspigot.items.setLore
import net.axay.kspigot.main.KSpigotMainInstance
import net.kyori.adventure.text.Component
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.configuration.file.FileConfiguration
import org.bukkit.configuration.file.YamlConfiguration
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.io.File

private val file = File(KSpigotMainInstance.dataFolder, "homes.yml")
private lateinit var homeConfiguration: FileConfiguration

fun Player.home(home: Int): Home? {
    val location = homeConfiguration.getLocation("$uniqueId.$home.location") ?: return null

    return Home(home, location)
}

fun Player.setHome(home: Int, location: Location) {
    homeConfiguration.set("$uniqueId.$home.location", location)
    saveHomeFile()
}

fun Player.homes(): ArrayList<Home> {
    val homeList = arrayListOf<Home>()

    val firstHome = home(1)
    val secondHome = home(2)
    val thirdHome = home(3)
    val fourthHome = home(4)
    val fifthHome = home(5)

    if (firstHome != null) homeList.add(firstHome)
    if (secondHome != null) homeList.add(secondHome)
    if (thirdHome != null) homeList.add(thirdHome)
    if (fourthHome != null) homeList.add(fourthHome)
    if (fifthHome != null) homeList.add(fifthHome)

    return homeList
}

fun Player.homeGUI() = kSpigotGUI(GUIType.ONE_BY_NINE) {
    page(1) {
        val compound = createRectCompound<ItemStack>(
            Slots.RowOneSlotOne, Slots.RowOneSlotNine,
            iconGenerator = {
                it
            },
            onClick = { clickEvent, element ->
                clickEvent.bukkitEvent.isCancelled = true
                val player = clickEvent.player
                val displayName = element.displayName().textValue()

                val homeNumber: String

                val home: Home = if (displayName.contains("1")) {
                    homeNumber = "1."
                    player.home(1) ?: return@createRectCompound
                } else if (displayName.contains("2")) {
                    homeNumber = "2."
                    player.home(2) ?: return@createRectCompound
                } else if (displayName.contains("3")) {
                    homeNumber = "3."
                    player.home(3) ?: return@createRectCompound
                } else if (displayName.contains("4")) {
                    homeNumber = "4."
                    player.home(4) ?: return@createRectCompound
                } else {
                    homeNumber = "5."
                    player.home(5) ?: return@createRectCompound
                }

                player.closeInventory()

                player.teleportAsync(home.location)

                player.sendMessage(
                    message("teleported_to_home")
                        .replace("\${home_number}", homeNumber)
                )
            }
        )

        for (home in homes()) {
            val location = home.location
            val worldName = location.world.name
            val x = location.blockX
            val y = location.blockY
            val z = location.blockZ

            val homeItemStack = itemStack(Material.GRASS_BLOCK) {
                meta {
                    displayName(Component.text("§a${home.number}. Home"))

                    setLore {
                        +"§7Click this to go to your §a${home.number}. home§7."
                        +"§a§lLocation:"
                        +"§a$worldName§7, §a$x§7, §a$y§7, §a$z"
                    }
                }
            }

            compound.addContent(homeItemStack)
        }
    }
}

fun Player.setHomeGUI() = kSpigotGUI(GUIType.ONE_BY_NINE) {
    page(1) {
        val compound = createRectCompound<ItemStack>(
            Slots.RowOneSlotOne, Slots.RowOneSlotNine,
            iconGenerator = {
                it
            },
            onClick = { clickEvent, element ->
                clickEvent.bukkitEvent.isCancelled = true
                val player = clickEvent.player
                val displayName = element.displayName().textValue()

                val homeNumber: String
                val location = player.location
                val worldName = location.world.name
                val x = location.blockX
                val y = location.blockY
                val z = location.blockZ

                if (displayName.contains("1")) {
                    player.setHome(1, player.location)
                    homeNumber = "1."
                } else if (displayName.contains("2")) {
                    player.setHome(2, player.location)
                    homeNumber = "2."
                } else if (displayName.contains("3")) {
                    player.setHome(3, player.location)
                    homeNumber = "3."
                } else if (displayName.contains("4")) {
                    player.setHome(4, player.location)
                    homeNumber = "4."
                } else {
                    player.setHome(5, player.location)
                    homeNumber = "5."
                }

                player.closeInventory()

                player.sendMessage(
                    message("home_set")
                        .replace("\${home_number}", homeNumber)
                        .replace("\${world}", worldName)
                        .replace("\${x}", x)
                        .replace("\${y}", y)
                        .replace("\${z}", z)
                )
            }
        )

        val availableHomes = availableHomes()

        compound.addContent(
            itemStack(Material.GRASS_BLOCK) {
                meta {
                    displayName(Component.text("§a1. Home"))

                    setLore {
                        +"§7Click this to set your §a1. home§7."
                    }
                }
            }
        )

        compound.addContent(
            itemStack(Material.GRASS_BLOCK) {
                meta {
                    displayName(Component.text("§a2. Home"))

                    setLore {
                        +"§7Click this to set your §a2. home§7."
                    }
                }
            }
        )

        if (availableHomes >= 3) {
            compound.addContent(
                itemStack(Material.GRASS_BLOCK) {
                    meta {
                        displayName(Component.text("§a3. Home"))

                        setLore {
                            +"§7Click this to set your §a3. home§7."
                        }
                    }
                }
            )
        }

        if (availableHomes >= 4) {
            compound.addContent(
                itemStack(Material.GRASS_BLOCK) {
                    meta {
                        displayName(Component.text("§a4. Home"))

                        setLore {
                            +"§7Click this to set your §a4. home§7."
                        }
                    }
                }
            )
        }

        if (availableHomes >= 5) {
            compound.addContent(
                itemStack(Material.GRASS_BLOCK) {
                    meta {
                        displayName(Component.text("§a5. Home"))

                        setLore {
                            +"§7Click this to set your §a5. home§7."
                        }
                    }
                }
            )
        }
    }
}

fun reloadHomes() {
    homeConfiguration = YamlConfiguration.loadConfiguration(file)
    saveHomeFile()
}

private fun saveHomeFile() {
    homeConfiguration.save(file)
}