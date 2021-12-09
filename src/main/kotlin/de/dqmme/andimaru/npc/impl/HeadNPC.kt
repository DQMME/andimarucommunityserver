package de.dqmme.andimaru.npc.impl

import com.github.juliarn.npc.event.PlayerNPCInteractEvent
import de.dqmme.andimaru.manager.price
import de.dqmme.andimaru.npc.NPC
import de.dqmme.andimaru.npc.npcData
import de.dqmme.andimaru.util.*
import net.axay.kspigot.items.itemStack
import net.axay.kspigot.items.meta
import net.axay.kspigot.items.setLore
import net.axay.kspigot.main.KSpigotMainInstance
import net.kyori.adventure.text.Component
import net.wesjd.anvilgui.AnvilGUI
import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.SkullMeta


object HeadNPC : NPC() {
    override val id: String = "head_npc"

    val data = npcData(id)

    override val displayName: String = data.displayName ?: "§cName not set"
    override val location: Location =
        data.location ?: KSpigotMainInstance.server.worlds[0].spawnLocation.add(12.0, 0.0, 0.0)
    override val imitatePlayer: Boolean = data.imitatePlayer
    override val lookAtPlayer: Boolean = data.lookAtPlayer
    override val isTeleportNPC: Boolean = false
    override var finalNPC: com.github.juliarn.npc.NPC? = null

    override fun onInteract(event: PlayerNPCInteractEvent) {
        val price = price("player_skull")

        AnvilGUI.Builder()
            .onClose { obj: Player -> obj.closeInventory() }
            .onComplete { player, text ->
                offlinePlayerByName(text) {
                    if (it == null) {
                        AnvilGUI.Response.close()
                        return@offlinePlayerByName
                    }

                    val skull = itemStack(Material.PLAYER_HEAD) {
                        meta<SkullMeta> {
                            displayName(Component.text("§e${it.name}´s Kopf"))

                            owningPlayer = it
                        }
                    }

                    sell(player, skull, price)
                }

                AnvilGUI.Response.close()
            }
            .text("Name")
            .itemLeft(
                itemStack(Material.PAPER) {
                    meta {
                        setLore {
                            +"§7Kaufe dir einen §aSpielerkopf§7."
                            +"§6Preis: $price Coins"
                        }
                    }
                }
            )
            .title("§aSpielername")
            .plugin(KSpigotMainInstance)
            .open(event.player)
    }

    private fun sell(player: Player, itemStack: ItemStack, price: Double) {
        val playerCoins = player.coins()

        if (price > playerCoins) {
            player.sendMessage(message("not_enough_coins"))
            return
        }

        player.removeCoins(price)

        if (player.inventory.firstEmpty() != -1) {
            player.inventory.addItem(itemStack)
        } else {
            player.world.dropItemNaturally(player.location, itemStack)
        }

        player.sendMessage(
            message("item_bought")
                .replace("\${price}", price)
                .replace("\${new_coins}", player.coins())
                .replace("\${item_name}", itemStack.displayName().textValue())
        )
    }
}