package de.dqmme.andimaru.npc.impl

import com.github.juliarn.npc.event.PlayerNPCInteractEvent
import de.dqmme.andimaru.manager.message
import de.dqmme.andimaru.manager.price
import de.dqmme.andimaru.npc.NPC
import de.dqmme.andimaru.npc.npcData
import de.dqmme.andimaru.util.*
import net.axay.kspigot.gui.*
import net.axay.kspigot.items.itemStack
import net.axay.kspigot.items.meta
import net.axay.kspigot.items.setLore
import net.axay.kspigot.main.KSpigotMainInstance
import net.kyori.adventure.text.Component
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

object SellFoodNPC : NPC() {
    override val id: String = "sell_food_npc"

    val data = npcData(id)

    override val displayName: String = data.displayName ?: "§cName not set"
    override val location: Location =
        data.location ?: KSpigotMainInstance.server.worlds[0].spawnLocation.add(22.0, 0.0, 0.0)
    override val imitatePlayer: Boolean = data.imitatePlayer
    override val lookAtPlayer: Boolean = data.lookAtPlayer
    override val isTeleportNPC: Boolean = false
    override var finalNPC: com.github.juliarn.npc.NPC? = null

    private val sellableFood = hashMapOf<Material, Double>()

    init {
        sellableFood[Material.PORKCHOP] = price("raw_porkchop")
        sellableFood[Material.APPLE] = price("apple")
        sellableFood[Material.GOLDEN_APPLE] = price("golden_apple")
        sellableFood[Material.BAKED_POTATO] = price("baked_potato")
    }

    override fun onInteract(event: PlayerNPCInteractEvent) {
        val player = event.player

        player.openGUI(
            kSpigotGUI(GUIType.SIX_BY_NINE) {
                page(1) {
                    transitionFrom = PageChangeEffect.SLIDE_HORIZONTALLY
                    transitionTo = PageChangeEffect.SLIDE_HORIZONTALLY

                    placeholder(Slots.Border, blackGlassItem())

                    pageChanger(Slots.RowOneSlotFive, itemStack(Material.LIME_DYE) {
                        meta {
                            displayName(Component.text("§aAlle Items verkaufen"))

                            setLore {
                                +"§aVerkaufe §lall §adeine Items auf einmal"
                            }
                        }
                    }, 2, null) {}

                    pageChanger(Slots.RowOneSlotNine, itemStack(Material.PAPER) {
                        meta {
                            displayName(Component.text("§6Preise"))

                            setLore {
                                +"§aErfahre welche §eItems§a, du für wie viele §6Coins §averkaufen kannst"
                            }
                        }
                    }, 3, null) {}
                }

                page(2) {
                    transitionFrom = PageChangeEffect.SLIDE_HORIZONTALLY
                    transitionTo = PageChangeEffect.SLIDE_HORIZONTALLY

                    placeholder(Slots.All, blackGlassItem())

                    button(Slots.RowFourSlotFour, itemStack(Material.GREEN_CONCRETE) {
                        meta {
                            displayName(Component.text("§aBestätigen"))

                            setLore {
                                +"§aVerkaufe alle verkaufbaren Items aus deinem Inventar"
                            }
                        }
                    }) {
                        val inventory = it.player.inventory
                        val content = arrayListOf<ItemStack>()

                        for (item in inventory.contents!!) {
                            if (item == null) continue

                            content.add(item)
                        }

                        content.remove(inventory.helmet)
                        content.remove(inventory.chestplate)
                        content.remove(inventory.leggings)
                        content.remove(inventory.boots)

                        for (item in content) {
                            for (material in sellableFood.keys) {
                                if (item.type == material) {
                                    val price = item.amount * sellableFood[material]!!

                                    player.inventory.remove(item)

                                    it.player.addCoins(price)

                                    player.sendMessage(
                                        message("item_sold")
                                            .replace("\${price}", price)
                                            .replace("\${new_coins}", it.player.coins())
                                            .replace("\${item_name}", material.name)
                                            .replace("\${amount}", item.amount)
                                    )
                                }
                            }
                        }
                    }

                    button(Slots.RowFourSlotSix, itemStack(Material.RED_CONCRETE) {
                        meta {
                            displayName(Component.text("§cAbbrechen"))

                            setLore {
                                +"§cBreche den Vorgang ab"
                            }
                        }
                    }) {
                        it.player.closeInventory()
                    }
                }

                page(3) {
                    transitionFrom = PageChangeEffect.SLIDE_HORIZONTALLY
                    transitionTo = PageChangeEffect.SLIDE_HORIZONTALLY

                    placeholder(Slots.All, blackGlassItem())

                    button(Slots.RowSixSlotOne, itemStack(Material.PORKCHOP) {
                        meta {
                            displayName(Component.text("§6Schweinefleisch (roh)"))

                            setLore {
                                +"§6Verkaufspreis: ${price("raw_porkchop")} Coins"
                            }
                        }
                    }) {}

                    button(Slots.RowSixSlotTwo, itemStack(Material.APPLE) {
                        meta {
                            displayName(Component.text("§cÄpfel"))

                            setLore {
                                +"§6Verkaufspreis: ${price("apple")} Coins"
                            }
                        }
                    }) {}

                    button(Slots.RowSixSlotThree, itemStack(Material.GOLDEN_APPLE) {
                        meta {
                            displayName(Component.text("§eGoldäpfel"))

                            setLore {
                                +"§6Verkaufspreis: ${price("golden_apple")} Coins"
                            }
                        }
                    }) {}

                    button(Slots.RowSixSlotThree, itemStack(Material.BAKED_POTATO) {
                        meta {
                            displayName(Component.text("§6Kartoffeln (gebraten)"))

                            setLore {
                                +"§6Verkaufspreis: ${price("baked_potato")} Coins"
                            }
                        }
                    }) {}

                    pageChanger(Slots.RowOneSlotOne, itemStack(Material.PAPER) {
                        meta {
                            displayName(Component.text("§aZurück"))

                            setLore {
                                +"§aNavigiere zurück zur Hauptseite"
                            }
                        }
                    }, 1, null) {}
                }
            }
        )
    }
}