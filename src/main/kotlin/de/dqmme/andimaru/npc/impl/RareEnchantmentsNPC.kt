package de.dqmme.andimaru.npc.impl

import com.github.juliarn.npc.event.PlayerNPCInteractEvent
import de.dqmme.andimaru.enchantment.CustomEnchants
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
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.EnchantmentStorageMeta

object RareEnchantmentsNPC : NPC() {
    override val id: String = "rare_enchantments_npc"

    val data = npcData(id)

    override val displayName: String = data.displayName ?: "§cName not set"
    override val location: Location =
        data.location ?: KSpigotMainInstance.server.worlds[0].spawnLocation.add(20.0, 0.0, 0.0)
    override val imitatePlayer: Boolean = data.imitatePlayer
    override val lookAtPlayer: Boolean = data.lookAtPlayer
    override val isTeleportNPC: Boolean = false
    override var finalNPC: com.github.juliarn.npc.NPC? = null

    override fun onInteract(event: PlayerNPCInteractEvent) {
        val player = event.player

        player.openGUI(
            kSpigotGUI(GUIType.THREE_BY_NINE) {
                page(1) {
                    transitionFrom = PageChangeEffect.SLIDE_HORIZONTALLY
                    transitionTo = PageChangeEffect.SLIDE_HORIZONTALLY

                    placeholder(Slots.All, blackGlassItem())

                    pageChanger(Slots.RowTwoSlotTwo, itemStack(Material.ENCHANTED_BOOK) {
                        meta<EnchantmentStorageMeta> {
                            addStoredEnchant(Enchantment.DIG_SPEED, 6, true)

                            displayName(Component.text("§6Efficiency VI"))

                            setLore {
                                +"§6§lEine Effizienz Stufe höher als möglich"
                                +"§6Preis: ${price("efficiency_6")} Coins"
                            }
                        }
                    }, 2, null) {}

                    pageChanger(Slots.RowTwoSlotThree, itemStack(Material.ENCHANTED_BOOK) {
                        meta<EnchantmentStorageMeta> {
                            addStoredEnchant(CustomEnchants.TELEKINESIS, 1, true)

                            displayName(Component.text("§6Telekinesis I"))

                            setLore {
                                +"§7Telekinesis I"
                                +"§6§lAbgebaute Blöcke werden direkt zum Inventar hinzugefügt"
                                +"§6Preis: ${price("telekinesis_1")} Coins"
                            }
                        }
                    }, 3, null) {}

                    pageChanger(Slots.RowTwoSlotFour, itemStack(Material.ENCHANTED_BOOK) {
                        meta<EnchantmentStorageMeta> {
                            addStoredEnchant(CustomEnchants.AUTO_SMELT, 1, true)

                            displayName(Component.text("§6Auto-Smelt I"))

                            setLore {
                                +"§7Auto-Smelt I"
                                +"§6§lAbgebaute Erze werden direkt geschmolzen"
                                +"§6Preis: ${price("auto_smelt_1")} Coins"
                            }
                        }
                    }, 4, null) {}
                }

                page(2) {
                    transitionFrom = PageChangeEffect.SLIDE_HORIZONTALLY
                    transitionTo = PageChangeEffect.SLIDE_HORIZONTALLY

                    placeholder(Slots.All, blackGlassItem())

                    val price = price("efficiency_6")

                    freeSlot(Slots.RowTwoSlotFive)

                    button(Slots.RowOneSlotFive, itemStack(Material.ANVIL) {
                        meta {
                            displayName(Component.text("§6Item verzaubern"))

                            setLore {
                                +"§7Verzaubere das Item."
                                +"§6Preis: $price Coins"
                            }
                        }
                    }) {
                        if (it.bukkitEvent.slot == 13) {
                            it.bukkitEvent.isCancelled = false
                            return@button
                        }

                        val inventory = it.bukkitEvent.inventory
                        val item = inventory.getItem(13)

                        if (item == null) {
                            it.bukkitEvent.isCancelled = true
                            return@button
                        }

                        if(!item.type.isTool()) {
                            it.bukkitEvent.isCancelled = true
                            return@button
                        }

                        if(item.itemMeta.hasEnchant(Enchantment.DIG_SPEED, 6)) {
                            it.bukkitEvent.isCancelled = true
                            return@button
                        }

                        val newItem = itemStack(item.type) {
                            meta {
                                for(enchantment in item.enchantments) {
                                    addEnchant(enchantment.key, enchantment.value, true)
                                }

                                addEnchant(Enchantment.DIG_SPEED, 6, true)

                                val oldDisplayName = item.displayName().textValueNullable()

                                if(oldDisplayName != null) {
                                    displayName(Component.text(oldDisplayName))
                                }

                                val lore = mutableListOf<Component>()

                                if(item.lore() != null) {
                                    item.lore()!!.forEach { component ->
                                        lore.add(component)
                                    }
                                }

                                lore(lore)
                            }
                        }

                        sell(it.player, price, newItem, "Efficiency VI")
                    }
                }

                page(3) {
                    transitionFrom = PageChangeEffect.SLIDE_HORIZONTALLY
                    transitionTo = PageChangeEffect.SLIDE_HORIZONTALLY

                    placeholder(Slots.All, blackGlassItem())

                    val price = price("telekinesis_1")

                    freeSlot(Slots.RowTwoSlotFive)

                    button(Slots.RowOneSlotFive, itemStack(Material.ANVIL) {
                        meta {
                            displayName(Component.text("§6Item verzaubern"))

                            setLore {
                                +"§7Verzaubere das Item."
                                +"§6Preis: $price Coins"
                            }
                        }
                    }) {
                        if (it.bukkitEvent.slot == 13) {
                            it.bukkitEvent.isCancelled = false
                            return@button
                        }

                        val inventory = it.bukkitEvent.inventory
                        val item = inventory.getItem(13)

                        if (item == null) {
                            it.bukkitEvent.isCancelled = true
                            return@button
                        }

                        if(!item.type.isTool()) {
                            it.bukkitEvent.isCancelled = true
                            return@button
                        }

                        if(item.itemMeta.hasEnchant(CustomEnchants.TELEKINESIS)) {
                            it.bukkitEvent.isCancelled = true
                            return@button
                        }

                        val newItem = itemStack(item.type) {
                            meta {
                                for(enchantment in item.enchantments) {
                                    addEnchant(enchantment.key, enchantment.value, true)
                                }

                                addEnchant(CustomEnchants.TELEKINESIS, 1, true)

                                val oldDisplayName = item.displayName().textValueNullable()

                                if(oldDisplayName != null) {
                                    displayName(Component.text(oldDisplayName))
                                }

                                val lore = mutableListOf<Component>()

                                lore.add(Component.text("§7Telekinesis I"))

                                if(item.lore() != null) {
                                    item.lore()!!.forEach { component ->
                                        lore.add(component)
                                    }
                                }

                                lore(lore)
                            }
                        }

                        sell(it.player, price, newItem, "Telekinesis I")
                    }
                }

                page(4) {
                    transitionFrom = PageChangeEffect.SLIDE_HORIZONTALLY
                    transitionTo = PageChangeEffect.SLIDE_HORIZONTALLY

                    placeholder(Slots.All, blackGlassItem())

                    val price = price("auto_smelt_1")

                    freeSlot(Slots.RowTwoSlotFive)

                    button(Slots.RowOneSlotFive, itemStack(Material.ANVIL) {
                        meta {
                            displayName(Component.text("§6Item verzaubern"))

                            setLore {
                                +"§7Verzaubere das Item."
                                +"§6Preis: $price Coins"
                            }
                        }
                    }) {
                        if (it.bukkitEvent.slot == 13) {
                            it.bukkitEvent.isCancelled = false
                            return@button
                        }

                        val inventory = it.bukkitEvent.inventory
                        val item = inventory.getItem(13)

                        if (item == null) {
                            it.bukkitEvent.isCancelled = true
                            return@button
                        }

                        if(!item.type.isTool()) {
                            it.bukkitEvent.isCancelled = true
                            return@button
                        }

                        if(item.itemMeta.hasEnchant(CustomEnchants.AUTO_SMELT)) {
                            it.bukkitEvent.isCancelled = true
                            return@button
                        }

                        val newItem = itemStack(item.type) {
                            meta {
                                for(enchantment in item.enchantments) {
                                    addEnchant(enchantment.key, enchantment.value, true)
                                }

                                addEnchant(CustomEnchants.AUTO_SMELT, 1, true)

                                val oldDisplayName = item.displayName().textValueNullable()

                                if(oldDisplayName != null) {
                                    displayName(Component.text(oldDisplayName))
                                }

                                val lore = mutableListOf<Component>()

                                lore.add(Component.text("§7Auto-Smelt I"))

                                if(item.lore() != null) {
                                    item.lore()!!.forEach { component ->
                                        lore.add(component)
                                    }
                                }

                                lore(lore)
                            }
                        }

                        sell(it.player, price, newItem, "Auto-Smelt I")
                    }
                }
            }
        )
    }

    private fun sell(player: Player, price: Double, item: ItemStack, name: String) {
        val playerCoins = player.coins()

        if (price > playerCoins) {
            player.sendMessage(message("not_enough_coins"))
            return
        }

        player.removeCoins(price)

        if (player.inventory.firstEmpty() != -1) {
            player.inventory.addItem(item)
        } else {
            player.world.dropItemNaturally(player.location, item)
        }

        player.sendMessage(
            message("item_bought")
                .replace("\${price}", price)
                .replace("\${new_coins}", player.coins())
                .replace("\${item_name}", name)
        )

        player.closeInventory()
    }
}