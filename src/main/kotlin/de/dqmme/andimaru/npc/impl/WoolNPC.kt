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

object WoolNPC : NPC() {
    override val id: String = "wool_npc"

    private val data = npcData(id)

    override val displayName: String = data.displayName ?: "§cName not set"
    override val location: Location =
        data.location ?: KSpigotMainInstance.server.worlds[0].spawnLocation.add(28.0, 0.0, 0.0)
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

                    button(
                        Slots.RowThreeSlotOne,
                        sellableItem(Material.WHITE_WOOL, price("white_wool"))
                    ) {
                        sell(it.player, price("white_wool"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowThreeSlotTwo,
                        sellableItem(Material.ORANGE_WOOL, price("orange_wool"))
                    ) {
                        sell(it.player, price("orange_wool"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowThreeSlotThree,
                        sellableItem(Material.MAGENTA_WOOL, price("magenta_wool"))
                    ) {
                        sell(it.player, price("magenta_wool"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowThreeSlotFour,
                        sellableItem(Material.LIGHT_BLUE_WOOL, price("light_blue_wool"))
                    ) {
                        sell(it.player, price("light_blue_wool"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowThreeSlotFive,
                        sellableItem(Material.YELLOW_WOOL, price("yellow_wool"))
                    ) {
                        sell(it.player, price("yellow_wool"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowThreeSlotSix,
                        sellableItem(Material.LIME_WOOL, price("lime_wool"))
                    ) {
                        sell(it.player, price("lime_wool"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowThreeSlotSeven,
                        sellableItem(Material.PINK_WOOL, price("pink_wool"))
                    ) {
                        sell(it.player, price("pink_wool"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowThreeSlotEight,
                        sellableItem(Material.GRAY_WOOL, price("gray_wool"))
                    ) {
                        sell(it.player, price("gray_wool"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowThreeSlotNine,
                        sellableItem(Material.LIGHT_GRAY_WOOL, price("light_gray_wool"))
                    ) {
                        sell(it.player, price("light_gray_wool"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowTwoSlotOne,
                        sellableItem(Material.CYAN_WOOL, price("cyan_wool"))
                    ) {
                        sell(it.player, price("cyan_wool"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowTwoSlotTwo,
                        sellableItem(Material.PURPLE_WOOL, price("purple_wool"))
                    ) {
                        sell(it.player, price("purple_wool"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowTwoSlotThree,
                        sellableItem(Material.BLUE_WOOL, price("blue_wool"))
                    ) {
                        sell(it.player, price("blue_wool"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowTwoSlotFour,
                        sellableItem(Material.BROWN_WOOL, price("brown_wool"))
                    ) {
                        sell(it.player, price("brown_wool"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowTwoSlotFive,
                        sellableItem(Material.GREEN_WOOL, price("green_wool"))
                    ) {
                        sell(it.player, price("green_wool"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowTwoSlotSix,
                        sellableItem(Material.RED_WOOL, price("red_wool"))
                    ) {
                        sell(it.player, price("red_wool"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowTwoSlotSeven,
                        sellableItem(Material.BLACK_WOOL, price("black_wool"))
                    ) {
                        sell(it.player, price("black_wool"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowTwoSlotEight,
                        sellableItem(Material.SHEEP_SPAWN_EGG, price("sheep_spawn_egg"))
                    ) {
                        sell(it.player, price("sheep_spawn_egg"), it.bukkitEvent.currentItem!!)
                    }
                }
            }
        )
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