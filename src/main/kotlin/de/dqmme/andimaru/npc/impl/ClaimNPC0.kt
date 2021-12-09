package de.dqmme.andimaru.npc.impl

import com.github.juliarn.npc.event.PlayerNPCInteractEvent
import de.dqmme.andimaru.manager.price
import de.dqmme.andimaru.npc.NPC
import de.dqmme.andimaru.npc.npcData
import de.dqmme.andimaru.util.*
import me.ryanhamshire.GriefPrevention.GriefPrevention
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

object ClaimNPC0 : NPC() {
    override val id: String = "claim_npc_0"

    private val data = npcData(id)

    override val displayName: String = data.displayName ?: "§cName not set"
    override val location: Location = data.location ?: KSpigotMainInstance.server.worlds[0].spawnLocation.add(2.0, 0.0, 0.0)
    override val imitatePlayer: Boolean = data.imitatePlayer
    override val lookAtPlayer: Boolean = data.lookAtPlayer
    override val isTeleportNPC: Boolean = false
    override var finalNPC: com.github.juliarn.npc.NPC? = null

    override fun onInteract(event: PlayerNPCInteractEvent) {
        val player = event.player

        player.openGUI(
            kSpigotGUI(GUIType.FOUR_BY_NINE) {
                title = "§aClaim Blöcke und Homes kaufen"

                page(1) {
                    placeholder(Slots.All, blackGlassItem())

                    button(Slots.RowThreeSlotThree, itemStack(Material.GOLD_INGOT) {
                        meta {
                            displayName(Component.text("§6Einen Block kaufen"))

                            setLore {
                                +"§7Kaufe §6einen §7weiteren Claim Block."
                                +"§6Preis: ${price("one_claim_block")} Coin(s)"
                            }

                            amount = 1
                        }
                    }) {
                        sell(it.player, price("one_claim_block"), "1 Claim Block") {
                            val bonusClaimBlocks =
                                GriefPrevention.instance.dataStore.getPlayerData(it.player.uniqueId).bonusClaimBlocks

                            GriefPrevention.instance.dataStore.getPlayerData(it.player.uniqueId).bonusClaimBlocks =
                                bonusClaimBlocks + 1
                        }
                    }

                    button(Slots.RowThreeSlotFive, itemStack(Material.GOLD_INGOT) {
                        meta {
                            displayName(Component.text("§6Zehn Blöcke kaufen"))

                            setLore {
                                +"§7Kaufe §6zehn §7weitere Claim Block."
                                +"§6Preis: ${price("ten_claim_blocks")} Coin(s)"
                            }

                            amount = 10
                        }
                    }) {
                        sell(it.player, price("ten_claim_blocks"), "10 Claim Blöcke") {
                            val bonusClaimBlocks =
                                GriefPrevention.instance.dataStore.getPlayerData(it.player.uniqueId).bonusClaimBlocks

                            GriefPrevention.instance.dataStore.getPlayerData(it.player.uniqueId).bonusClaimBlocks =
                                bonusClaimBlocks + 10
                        }
                    }

                    button(Slots.RowThreeSlotSeven, itemStack(Material.GOLD_INGOT) {
                        meta {
                            displayName(Component.text("§6Hundert Blöcke kaufen"))

                            setLore {
                                +"§7Kaufe §6hundert §7weitere Claim Block."
                                +"§6Preis: ${price("hundred_claim_blocks")} Coin(s)"
                            }

                            amount = 64
                        }
                    }) {
                        sell(it.player, price("hundred_claim_blocks"), "100 Claim Blöcke") {
                            val bonusClaimBlocks =
                                GriefPrevention.instance.dataStore.getPlayerData(it.player.uniqueId).bonusClaimBlocks

                            GriefPrevention.instance.dataStore.getPlayerData(it.player.uniqueId).bonusClaimBlocks =
                                bonusClaimBlocks + 100
                        }
                    }

                    button(Slots.RowTwoSlotFour, itemStack(Material.PAPER) {
                        meta {
                            displayName(Component.text("§6Info"))

                            setLore {
                                +"§7Du hast §6${player.availableHomes()} §7 Homes zur Verfügung."
                            }

                            amount = player.availableHomes()
                        }
                    }) {}

                    button(Slots.RowTwoSlotSix, itemStack(Material.GOLD_INGOT) {
                        val availableHomes = player.availableHomes()

                        println(availableHomes)

                        val price = if (availableHomes == 2) {
                            "§6Preis: " + price("third_home") + " Coins"
                        } else if (availableHomes == 3) {
                            "§6Preis: " + price("fourth_home") + " Coins"
                        } else if (availableHomes == 4) {
                            "§6Preis: " + price("fifth_home") + " Coins"
                        } else {
                            "§7Du hast die maximale Anzahl an Homes erreicht."
                        }

                        meta {
                            displayName(Component.text("§6Weiteres Home kaufen"))

                            setLore {
                                +"§7Kaufe ein weiteres §6Home§7."
                                +price
                                +"§7Du hast §6$availableHomes §7von §65 Homes§7."
                            }
                        }
                    }) {
                        val availableHomes = player.availableHomes()
                        val price = if (availableHomes == 2) {
                            price("third_home")
                        } else if (availableHomes == 3) {
                            price("fourth_home")
                        } else if (availableHomes == 4) {
                            price("fifth_home")
                        } else {
                            it.bukkitEvent.isCancelled = true
                            return@button
                        }

                        sell(it.player, price, "1 Home Slot") {
                            player.setAvailableHomes(availableHomes + 1)
                        }
                    }
                }
            }
        )
    }

    private fun sell(player: Player, price: Double, name: String, onBuy: () -> Unit) {
        val playerCoins = player.coins()

        if (price > playerCoins) {
            player.sendMessage(message("not_enough_coins"))
            return
        }

        player.removeCoins(price)

        player.sendMessage(
            message("item_bought")
                .replace("\${price}", price)
                .replace("\${new_coins}", player.coins())
                .replace("\${item_name}", name)
        )

        onBuy()
    }
}