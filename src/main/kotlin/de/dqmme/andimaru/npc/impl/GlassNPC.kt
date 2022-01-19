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
import net.axay.kspigot.main.KSpigotMainInstance
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object GlassNPC : NPC() {
    override val id: String = "glass_npc"

    private val data = npcData(id)

    override val displayName: String = data.displayName ?: "§cName not set"
    override val location: Location =
        data.location ?: KSpigotMainInstance.server.worlds[0].spawnLocation.add(32.0, 0.0, 0.0)
    override val imitatePlayer: Boolean = data.imitatePlayer
    override val lookAtPlayer: Boolean = data.lookAtPlayer
    override val isTeleportNPC: Boolean = false
    override var finalNPC: com.github.juliarn.npc.NPC? = null

    override fun onInteract(event: PlayerNPCInteractEvent) {
        val player = event.player

        player.openGUI(kSpigotGUI(GUIType.FOUR_BY_NINE) {
            page(1) {
                placeholder(Slots.All, blackGlassItem())

                button(Slots.RowThreeSlotOne, sellableItem(Material.GLASS, price("glass"))) {
                    sell(it.player, price("glass"), it.bukkitEvent.currentItem!!)
                }

                button(Slots.RowThreeSlotTwo, sellableItem(Material.TINTED_GLASS, price("tinted_glass"))) {
                    sell(it.player, price("tinted_glass"), it.bukkitEvent.currentItem!!)
                }

                button(Slots.RowThreeSlotThree, sellableItem(Material.WHITE_STAINED_GLASS, price("white_glass"))) {
                    sell(it.player, price("white_glass"), it.bukkitEvent.currentItem!!)
                }

                button(Slots.RowThreeSlotFour, sellableItem(Material.ORANGE_STAINED_GLASS, price("orange_glass"))) {
                    sell(it.player, price("orange_glass"), it.bukkitEvent.currentItem!!)
                }

                button(Slots.RowThreeSlotFive, sellableItem(Material.MAGENTA_STAINED_GLASS, price("magenta_glass"))) {
                    sell(it.player, price("magenta_glass"), it.bukkitEvent.currentItem!!)
                }

                button(Slots.RowThreeSlotSix, sellableItem(Material.LIGHT_BLUE_STAINED_GLASS, price("light_blue_glass"))) {
                    sell(it.player, price("light_blue_glass"), it.bukkitEvent.currentItem!!)
                }

                button(Slots.RowThreeSlotSeven, sellableItem(Material.YELLOW_STAINED_GLASS, price("yellow_glass"))) {
                    sell(it.player, price("yellow_glass"), it.bukkitEvent.currentItem!!)
                }

                button(Slots.RowThreeSlotEight, sellableItem(Material.LIME_STAINED_GLASS, price("lime_glass"))) {
                    sell(it.player, price("lime_glass"), it.bukkitEvent.currentItem!!)
                }

                button(Slots.RowThreeSlotNine, sellableItem(Material.PINK_STAINED_GLASS, price("pink_glass"))) {
                    sell(it.player, price("pink_glass"), it.bukkitEvent.currentItem!!)
                }

                button(Slots.RowTwoSlotOne, sellableItem(Material.GRAY_STAINED_GLASS, price("gray_glass"))) {
                    sell(it.player, price("gray_glass"), it.bukkitEvent.currentItem!!)
                }

                button(Slots.RowTwoSlotTwo, sellableItem(Material.LIGHT_GRAY_STAINED_GLASS, price("light_gray_glass"))) {
                    sell(it.player, price("light_gray_glass"), it.bukkitEvent.currentItem!!)
                }

                button(Slots.RowTwoSlotThree, sellableItem(Material.CYAN_STAINED_GLASS, price("cyan_glass"))) {
                    sell(it.player, price("cyan_glass"), it.bukkitEvent.currentItem!!)
                }

                button(Slots.RowTwoSlotFour, sellableItem(Material.PURPLE_STAINED_GLASS, price("purple_glass"))) {
                    sell(it.player, price("purple_glass"), it.bukkitEvent.currentItem!!)
                }

                button(Slots.RowTwoSlotFive, sellableItem(Material.BLUE_STAINED_GLASS, price("blue_glass"))) {
                    sell(it.player, price("blue_glass"), it.bukkitEvent.currentItem!!)
                }

                button(Slots.RowTwoSlotSix, sellableItem(Material.BROWN_STAINED_GLASS, price("brown_glass"))) {
                    sell(it.player, price("brown_glass"), it.bukkitEvent.currentItem!!)
                }

                button(Slots.RowTwoSlotSeven, sellableItem(Material.GREEN_STAINED_GLASS, price("green_glass"))) {
                    sell(it.player, price("green_glass"), it.bukkitEvent.currentItem!!)
                }

                button(Slots.RowTwoSlotEight, sellableItem(Material.RED_STAINED_GLASS, price("red_glass"))) {
                    sell(it.player, price("red_glass"), it.bukkitEvent.currentItem!!)
                }

                button(Slots.RowTwoSlotNine, sellableItem(Material.BLACK_STAINED_GLASS, price("black_glass"))) {
                    sell(it.player, price("black_glass"), it.bukkitEvent.currentItem!!)
                }
            }
        })
    }

    private fun sell(player: Player, price: Double, currentItem: ItemStack) {
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
                .replace("\${item_name}", currentItem.type.name)
        )

        if (player.inventory.firstEmpty() != -1) {
            player.inventory.addItem(ItemStack(currentItem.type))
        } else {
            player.world.dropItemNaturally(player.location, ItemStack(currentItem.type))
        }
    }
}