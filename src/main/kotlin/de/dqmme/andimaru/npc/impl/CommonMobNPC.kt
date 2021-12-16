package de.dqmme.andimaru.npc.impl

import com.github.juliarn.npc.event.PlayerNPCInteractEvent
import de.dqmme.andimaru.manager.message
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

object CommonMobNPC : NPC() {
    override val id: String = "common_mob_npc"

    private val data = npcData(id)

    override val displayName: String = data.displayName ?: "§cName not set"
    override val location: Location =
        data.location ?: KSpigotMainInstance.server.worlds[0].spawnLocation.add(16.0, 0.0, 0.0)
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

                    button(Slots.RowThreeSlotTwo, itemStack(Material.PIG_SPAWN_EGG) {
                        meta {
                            displayName(Component.text("§dPig Spawn Egg"))

                            setLore {
                                +"§6Preis: ${price("pig_spawn_egg")} Coins"
                            }
                        }
                    }) {
                        val currentItem = it.bukkitEvent.currentItem!!

                        sell(it.player, currentItem.type, price("pig_spawn_egg"), currentItem.displayName().textValue())
                    }

                    button(Slots.RowThreeSlotThree, itemStack(Material.COW_SPAWN_EGG) {
                        meta {
                            displayName(Component.text("§7Cow Spawn Egg"))

                            setLore {
                                +"§6Preis: ${price("cow_spawn_egg")} Coins"
                            }
                        }
                    }) {
                        val currentItem = it.bukkitEvent.currentItem!!

                        sell(it.player, currentItem.type, price("cow_spawn_egg"), currentItem.displayName().textValue())
                    }

                    button(Slots.RowThreeSlotFour, itemStack(Material.SALMON_SPAWN_EGG) {
                        meta {
                            displayName(Component.text("§7Salmon Spawn Egg"))

                            setLore {
                                +"§6Preis: ${price("salmon_spawn_egg")} Coins"
                            }
                        }
                    }) {
                        val currentItem = it.bukkitEvent.currentItem!!

                        sell(
                            it.player,
                            currentItem.type,
                            price("salmon_spawn_egg"),
                            currentItem.displayName().textValue()
                        )
                    }

                    button(Slots.RowThreeSlotFive, itemStack(Material.COD_SPAWN_EGG) {
                        meta {
                            displayName(Component.text("§7Cod Spawn Egg"))

                            setLore {
                                +"§6Preis: ${price("cod_spawn_egg")} Coins"
                            }
                        }
                    }) {
                        val currentItem = it.bukkitEvent.currentItem!!

                        sell(it.player, currentItem.type, price("cod_spawn_egg"), currentItem.displayName().textValue())
                    }

                    button(Slots.RowThreeSlotSix, itemStack(Material.DONKEY_SPAWN_EGG) {
                        meta {
                            displayName(Component.text("§eDonkey Spawn Egg"))

                            setLore {
                                +"§6Preis: ${price("donkey_spawn_egg")} Coins"
                            }
                        }
                    }) {
                        val currentItem = it.bukkitEvent.currentItem!!

                        sell(
                            it.player,
                            currentItem.type,
                            price("donkey_spawn_egg"),
                            currentItem.displayName().textValue()
                        )
                    }

                    button(Slots.RowThreeSlotSeven, itemStack(Material.HORSE_SPAWN_EGG) {
                        meta {
                            displayName(Component.text("§eHorse Spawn Egg"))

                            setLore {
                                +"§6Preis: ${price("horse_spawn_egg")} Coins"
                            }
                        }
                    }) {
                        val currentItem = it.bukkitEvent.currentItem!!

                        sell(
                            it.player,
                            currentItem.type,
                            price("horse_spawn_egg"),
                            currentItem.displayName().textValue()
                        )
                    }

                    button(Slots.RowThreeSlotEight, itemStack(Material.MOOSHROOM_SPAWN_EGG) {
                        meta {
                            displayName(Component.text("§cMooshroom Cow Spawn Egg"))

                            setLore {
                                +"§6Preis: ${price("mooshroom_cow_spawn_egg")} Coins"
                            }
                        }
                    }) {
                        val currentItem = it.bukkitEvent.currentItem!!

                        sell(
                            it.player,
                            currentItem.type,
                            price("mooshroom_cow_spawn_egg"),
                            currentItem.displayName().textValue()
                        )
                    }

                    button(Slots.RowTwoSlotTwo, itemStack(Material.MOOSHROOM_SPAWN_EGG) {
                        meta {
                            displayName(Component.text("§cMooshroom Cow Spawn Egg"))

                            setLore {
                                +"§6Preis: ${price("mooshroom_cow_spawn_egg")} Coins"
                            }
                        }
                    }) {
                        val currentItem = it.bukkitEvent.currentItem!!

                        sell(
                            it.player,
                            currentItem.type,
                            price("mooshroom_cow_spawn_egg"),
                            currentItem.displayName().textValue()
                        )
                    }

                    button(Slots.RowTwoSlotThree, itemStack(Material.SADDLE) {
                        meta {
                            displayName(Component.text("§6Sattel"))

                            setLore {
                                +"§6Preis: ${price("saddle")} Coins"
                            }
                        }
                    }) {
                        val currentItem = it.bukkitEvent.currentItem!!

                        sell(it.player, currentItem.type, price("saddle"), currentItem.displayName().textValue())
                    }

                    button(Slots.RowTwoSlotFour, itemStack(Material.COOKED_BEEF) {
                        meta {
                            displayName(Component.text("§6Gebratenes Fleisch"))

                            setLore {
                                +"§6Preis: ${price("cooked_beef")} Coins"
                            }
                        }
                    }) {
                        val currentItem = it.bukkitEvent.currentItem!!

                        sell(it.player, currentItem.type, price("cooked_beef"), currentItem.displayName().textValue())
                    }

                    button(Slots.RowTwoSlotFive, itemStack(Material.CARROT) {
                        meta {
                            displayName(Component.text("§6Karotte"))

                            setLore {
                                +"§6Preis: ${price("carrot")} Coins"
                            }
                        }
                    }) {
                        val currentItem = it.bukkitEvent.currentItem!!

                        sell(it.player, currentItem.type, price("carrot"), currentItem.displayName().textValue())
                    }

                    button(Slots.RowTwoSlotSix, itemStack(Material.POTATO) {
                        meta {
                            displayName(Component.text("§eKartoffel"))

                            setLore {
                                +"§6Preis: ${price("potato")} Coins"
                            }
                        }
                    }) {
                        val currentItem = it.bukkitEvent.currentItem!!

                        sell(it.player, currentItem.type, price("potato"), currentItem.displayName().textValue())
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