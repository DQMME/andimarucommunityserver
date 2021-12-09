package de.dqmme.andimaru.npc.impl

import com.github.juliarn.npc.event.PlayerNPCInteractEvent
import de.dqmme.andimaru.manager.price
import de.dqmme.andimaru.npc.NPC
import de.dqmme.andimaru.npc.npcData
import de.dqmme.andimaru.util.*
import net.axay.kspigot.gui.GUIType
import net.axay.kspigot.gui.Slots
import net.axay.kspigot.gui.kSpigotGUI
import net.axay.kspigot.gui.openGUI
import net.axay.kspigot.items.itemStack
import net.axay.kspigot.items.meta
import net.axay.kspigot.items.setLore
import net.axay.kspigot.main.KSpigotMainInstance
import net.kyori.adventure.text.Component
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object RareMobNPC : NPC() {
    override val id: String = "rare_mob_npc"

    private val data = npcData(id)

    override val displayName: String = data.displayName ?: "§cName not set"
    override val location: Location =
        data.location ?: KSpigotMainInstance.server.worlds[0].spawnLocation.add(18.0, 0.0, 0.0)
    override val imitatePlayer: Boolean = data.imitatePlayer
    override val lookAtPlayer: Boolean = data.lookAtPlayer
    override val isTeleportNPC: Boolean = false
    override var finalNPC: com.github.juliarn.npc.NPC? = null

    override fun onInteract(event: PlayerNPCInteractEvent) {
        val player = event.player

        player.openGUI(
            kSpigotGUI(GUIType.FOUR_BY_NINE) {
                page(1) {
                    placeholder(Slots.All, blackGlassItem())

                    button(Slots.RowThreeSlotTwo, itemStack(Material.DOLPHIN_SPAWN_EGG) {
                        meta {
                            displayName(Component.text("§bDolphin Spawn Egg"))

                            setLore {
                                +"§6Preis: ${price("dolphin_spawn_egg")} Coins"
                            }
                        }
                    }) {
                        val currentItem = it.bukkitEvent.currentItem!!

                        sell(it.player, currentItem.type, price("potato"), currentItem.displayName().textValue())
                    }

                    button(Slots.RowThreeSlotThree, itemStack(Material.POLAR_BEAR_SPAWN_EGG) {
                        meta {
                            displayName(Component.text("§bPolar Bear Spawn Egg"))

                            setLore {
                                +"§6Preis: ${price("polar_bear_spawn_egg")} Coins"
                            }
                        }
                    }) {
                        val currentItem = it.bukkitEvent.currentItem!!

                        sell(it.player, currentItem.type, price("polar_bear_spawn_egg"), currentItem.displayName().textValue())
                    }

                    button(Slots.RowThreeSlotFour, itemStack(Material.PUFFERFISH_SPAWN_EGG) {
                        meta {
                            displayName(Component.text("§ePufferfish Spawn Egg"))

                            setLore {
                                +"§6Preis: ${price("pufferfish_spawn_egg")} Coins"
                            }
                        }
                    }) {
                        val currentItem = it.bukkitEvent.currentItem!!

                        sell(it.player, currentItem.type, price("pufferfish_spawn_egg"), currentItem.displayName().textValue())
                    }

                    button(Slots.RowThreeSlotFive, itemStack(Material.TROPICAL_FISH_SPAWN_EGG) {
                        meta {
                            displayName(Component.text("§eTropical Fish Spawn Egg"))

                            setLore {
                                +"§6Preis: ${price("tropical_fish_spawn_egg")} Coins"
                            }
                        }
                    }) {
                        val currentItem = it.bukkitEvent.currentItem!!

                        sell(it.player, currentItem.type, price("tropical_fish_spawn_egg"), currentItem.displayName().textValue())
                    }

                    button(Slots.RowThreeSlotSix, itemStack(Material.TURTLE_SPAWN_EGG) {
                        meta {
                            displayName(Component.text("§aTurtle Spawn Egg"))

                            setLore {
                                +"§6Preis: ${price("turtle_spawn_egg")} Coins"
                            }
                        }
                    }) {
                        val currentItem = it.bukkitEvent.currentItem!!

                        sell(it.player, currentItem.type, price("turtle_spawn_egg"), currentItem.displayName().textValue())
                    }

                    button(Slots.RowThreeSlotSeven, itemStack(Material.LLAMA_SPAWN_EGG) {
                        meta {
                            displayName(Component.text("§eLlama Spawn Egg"))

                            setLore {
                                +"§6Preis: ${price("llama_spawn_egg")} Coins"
                            }
                        }
                    }) {
                        val currentItem = it.bukkitEvent.currentItem!!

                        sell(it.player, currentItem.type, price("llama_spawn_egg"), currentItem.displayName().textValue())
                    }

                    button(Slots.RowThreeSlotEight, itemStack(Material.PANDA_SPAWN_EGG) {
                        meta {
                            displayName(Component.text("§fPanda Spawn Egg"))

                            setLore {
                                +"§6Preis: ${price("panda_spawn_egg")} Coins"
                            }
                        }
                    }) {
                        val currentItem = it.bukkitEvent.currentItem!!

                        sell(it.player, currentItem.type, price("panda_spawn_egg"), currentItem.displayName().textValue())
                    }

                    button(Slots.RowTwoSlotTwo, itemStack(Material.WOLF_SPAWN_EGG) {
                        meta {
                            displayName(Component.text("§fWolf Spawn Egg"))

                            setLore {
                                +"§6Preis: ${price("wolf_spawn_egg")} Coins"
                            }
                        }
                    }) {
                        val currentItem = it.bukkitEvent.currentItem!!

                        sell(it.player, currentItem.type, price("wolf_spawn_egg"), currentItem.displayName().textValue())
                    }

                    button(Slots.RowTwoSlotThree, itemStack(Material.OCELOT_SPAWN_EGG) {
                        meta {
                            displayName(Component.text("§eOcelot Spawn Egg"))

                            setLore {
                                +"§6Preis: ${price("ocelot_spawn_egg")} Coins"
                            }
                        }
                    }) {
                        val currentItem = it.bukkitEvent.currentItem!!

                        sell(it.player, currentItem.type, price("ocelot_spawn_egg"), currentItem.displayName().textValue())
                    }

                    button(Slots.RowTwoSlotFour, itemStack(Material.FOX_SPAWN_EGG) {
                        meta {
                            displayName(Component.text("§cFox Spawn Egg"))

                            setLore {
                                +"§6Preis: ${price("fox_spawn_egg")} Coins"
                            }
                        }
                    }) {
                        val currentItem = it.bukkitEvent.currentItem!!

                        sell(it.player, currentItem.type, price("fox_spawn_egg"), currentItem.displayName().textValue())
                    }

                    button(Slots.RowTwoSlotFive, itemStack(Material.PARROT_SPAWN_EGG) {
                        meta {
                            displayName(Component.text("§aParrot Spawn Egg"))

                            setLore {
                                +"§6Preis: ${price("parrot_spawn_egg")} Coins"
                            }
                        }
                    }) {
                        val currentItem = it.bukkitEvent.currentItem!!

                        sell(it.player, currentItem.type, price("parrot_spawn_egg"), currentItem.displayName().textValue())
                    }

                    button(Slots.RowTwoSlotSix, itemStack(Material.RABBIT_SPAWN_EGG) {
                        meta {
                            displayName(Component.text("§eRabbit Spawn Egg"))

                            setLore {
                                +"§6Preis: ${price("rabbit_spawn_egg")} Coins"
                            }
                        }
                    }) {
                        val currentItem = it.bukkitEvent.currentItem!!

                        sell(it.player, currentItem.type, price("rabbit_spawn_egg"), currentItem.displayName().textValue())
                    }
                }
            }
        )
    }

    private fun sell(player: Player, material: Material, price: Double, itemName: String) {
        val playerCoins = player.coins()

        if (price > playerCoins) {
            player.sendMessage(message("not_enough_coins"))
            return
        }

        player.removeCoins(price)

        if (player.inventory.firstEmpty() != -1) {
            player.inventory.addItem(ItemStack(material))
        } else {
            player.world.dropItemNaturally(player.location, ItemStack(material))
        }

        player.sendMessage(
            message("item_bought")
                .replace("\${price}", price)
                .replace("\${new_coins}", player.coins())
                .replace("\${item_name}", itemName)
        )
    }
}