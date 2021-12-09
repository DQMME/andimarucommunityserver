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
import net.axay.kspigot.main.KSpigotMainInstance
import org.bukkit.Location
import org.bukkit.enchantments.Enchantment
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

object BookNPC : NPC() {
    override val id: String = "book_npc"

    private val data = npcData(id)

    override val displayName: String = data.displayName ?: "§cName not set"
    override val location: Location = data.location ?: KSpigotMainInstance.server.worlds[0].spawnLocation
    override val imitatePlayer: Boolean = data.imitatePlayer
    override val lookAtPlayer: Boolean = data.lookAtPlayer
    override val isTeleportNPC: Boolean = false
    override var finalNPC: com.github.juliarn.npc.NPC? = null

    override fun onInteract(event: PlayerNPCInteractEvent) {
        val player = event.player

        player.openGUI(
            kSpigotGUI(GUIType.SIX_BY_NINE) {
                title = "§6Zauberbücher kaufen"

                page(1) {
                    placeholder(Slots.All, blackGlassItem())

                    button(
                        Slots.RowSixSlotTwo,
                        enchantedBookItem(
                            Enchantment.PROTECTION_ENVIRONMENTAL,
                            price("protection_1"),
                            "Schutz"
                        )
                    ) {
                        sell(it.player, price("protection_1"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowSixSlotThree,
                        enchantedBookItem(
                            Enchantment.DURABILITY,
                            price("durability_1"),
                            "Haltbarkeit"
                        )
                    ) {
                        sell(it.player, price("durability_1"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowSixSlotFour,
                        enchantedBookItem(
                            Enchantment.OXYGEN,
                            price("oxygen_1"),
                            "Atmung"
                        )
                    ) {
                        sell(it.player, price("oxygen_1"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowSixSlotFive,
                        enchantedBookItem(
                            Enchantment.PROTECTION_EXPLOSIONS,
                            price("blast_protection_1"),
                            "Explosionsschutz"
                        )
                    ) {
                        sell(it.player, price("blast_protection_1"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowSixSlotSix,
                        enchantedBookItem(
                            Enchantment.PROTECTION_PROJECTILE,
                            price("projectile_protection_1"),
                            "Schusssicher"
                        )
                    ) {
                        sell(it.player, price("projectile_protection_1"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowSixSlotSeven,
                        enchantedBookItem(
                            Enchantment.LOYALTY,
                            price("loyality_1"),
                            "Treue"
                        )
                    ) {
                        sell(it.player, price("loyality_1"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowSixSlotEight,
                        enchantedBookItem(
                            Enchantment.PROTECTION_FIRE,
                            price("fire_protection_1"),
                            "Feuerschutz"
                        )
                    ) {
                        sell(it.player, price("fire_protection_1"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowFiveSlotTwo,
                        enchantedBookItem(
                            Enchantment.THORNS,
                            price("thorns_1"),
                            "Dornen"
                        )
                    ) {
                        sell(it.player, price("thorns_1"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowFiveSlotThree,
                        enchantedBookItem(
                            Enchantment.WATER_WORKER,
                            price("water_affinity_1"),
                            "Wasseraffinität"
                        )
                    ) {
                        sell(it.player, price("water_affinity_1"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowFiveSlotFour,
                        enchantedBookItem(
                            Enchantment.DEPTH_STRIDER,
                            price("depth_strider_1"),
                            "Wasserläufer"
                        )
                    ) {
                        sell(it.player, price("depth_strider_1"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowFiveSlotFive,
                        enchantedBookItem(
                            Enchantment.PROTECTION_FALL,
                            price("feather_falling_1"),
                            "Federfall"
                        )
                    ) {
                        sell(it.player, price("feather_falling_1"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowFiveSlotSix,
                        enchantedBookItem(
                            Enchantment.FROST_WALKER,
                            price("frost_walker_1"),
                            "Eisläufer"
                        )
                    ) {
                        sell(it.player, price("frost_walker_1"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowFiveSlotSeven,
                        enchantedBookItem(
                            Enchantment.DIG_SPEED,
                            price("efficiency_1"),
                            "Effizienz"
                        )
                    ) {
                        sell(it.player, price("efficiency_1"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowFiveSlotEight,
                        enchantedBookItem(
                            Enchantment.LOOT_BONUS_BLOCKS,
                            price("fortune_1"),
                            "Glück"
                        )
                    ) {
                        sell(it.player, price("fortune_1"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowFourSlotTwo,
                        enchantedBookItem(
                            Enchantment.SILK_TOUCH,
                            price("silk_touch_1"),
                            "Behutsamkeit"
                        )
                    ) {
                        sell(it.player, price("silk_touch_1"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowFourSlotThree,
                        enchantedBookItem(
                            Enchantment.MENDING,
                            price("mending_1"),
                            "Reperatur"
                        )
                    ) {
                        sell(it.player, price("mending_1"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowFourSlotFour,
                        enchantedBookItem(
                            Enchantment.DAMAGE_ALL,
                            price("sharpness_1"),
                            "Schärfe"
                        )
                    ) {
                        sell(it.player, price("sharpness_1"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowFourSlotFive,
                        enchantedBookItem(
                            Enchantment.ARROW_KNOCKBACK,
                            price("punch_1"),
                            "Schlag"
                        )
                    ) {
                        sell(it.player, price("punch_1"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowFourSlotSix,
                        enchantedBookItem(
                            Enchantment.FIRE_ASPECT,
                            price("fire_aspect_1"),
                            "Verbrennung"
                        )
                    ) {
                        sell(it.player, price("fire_aspect_1"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowFourSlotSeven,
                        enchantedBookItem(
                            Enchantment.KNOCKBACK,
                            price("knockback_1"),
                            "Rückstoß"
                        )
                    ) {
                        sell(it.player, price("knockback_1"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowFourSlotEight,
                        enchantedBookItem(
                            Enchantment.LOOT_BONUS_MOBS,
                            price("looting_1"),
                            "Plünderung"
                        )
                    ) {
                        sell(it.player, price("looting_1"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowThreeSlotTwo,
                        enchantedBookItem(
                            Enchantment.ARROW_INFINITE,
                            price("infinity_1"),
                            "Unendlichkeit"
                        )
                    ) {
                        sell(it.player, price("infinity_1"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowThreeSlotThree,
                        enchantedBookItem(
                            Enchantment.ARROW_DAMAGE,
                            price("power_1"),
                            "Stärke"
                        )
                    ) {
                        sell(it.player, price("power_1"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowThreeSlotFour,
                        enchantedBookItem(
                            Enchantment.QUICK_CHARGE,
                            price("quick_charge"),
                            "Schnelladen"
                        )
                    ) {
                        sell(it.player, price("quick_charge"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowThreeSlotFive,
                        enchantedBookItem(
                            Enchantment.ARROW_FIRE,
                            price("flame_1"),
                            "Flamme"
                        )
                    ) {
                        sell(it.player, price("flame_1"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowThreeSlotSix,
                        enchantedBookItem(
                            Enchantment.MULTISHOT,
                            price("multi_shot_1"),
                            "Mehrfachschuss"
                        )
                    ) {
                        sell(it.player, price("multi_shot_1"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowThreeSlotSeven,
                        enchantedBookItem(
                            Enchantment.PIERCING,
                            price("piercing_1"),
                            "Durchschuss"
                        )
                    ) {
                        sell(it.player, price("piercing_1"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowThreeSlotEight,
                        enchantedBookItem(
                            Enchantment.LUCK,
                            price("luck_of_the_sea_1"),
                            "Glück des Meeres"
                        )
                    ) {
                        sell(it.player, price("luck_of_the_sea_1"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowTwoSlotTwo,
                        enchantedBookItem(
                            Enchantment.LURE,
                            price("lure_1"),
                            "Köder"
                        )
                    ) {
                        sell(it.player, price("lure_1"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowTwoSlotThree,
                        enchantedBookItem(
                            Enchantment.IMPALING,
                            price("impaling_1"),
                            "Harpune"
                        )
                    ) {
                        sell(it.player, price("impaling_1"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowTwoSlotFour,
                        enchantedBookItem(
                            Enchantment.CHANNELING,
                            price("channeling_1"),
                            "Entladung"
                        )
                    ) {
                        sell(it.player, price("channeling_1"), it.bukkitEvent.currentItem!!)
                    }

                    button(
                        Slots.RowTwoSlotFive,
                        enchantedBookItem(
                            Enchantment.RIPTIDE,
                            price("riptide_1"),
                            "Sog"
                        )
                    ) {
                        sell(it.player, price("riptide_1"), it.bukkitEvent.currentItem!!)
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
                .replace("\${item_name}", currentItem.displayName().textValue())
        )

        if (player.inventory.firstEmpty() != -1) {
            player.inventory.addItem(newEnchantedBook(currentItem))
        } else {
            player.world.dropItemNaturally(player.location, newEnchantedBook(currentItem))
        }
    }
}