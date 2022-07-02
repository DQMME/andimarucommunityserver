package de.dqmme.andimaru.command

import de.dqmme.andimaru.api.fetchSkin
import de.dqmme.andimaru.manager.message
import de.dqmme.andimaru.npc.*
import de.dqmme.andimaru.util.blackGlassItem
import de.dqmme.andimaru.util.textValue
import net.axay.kspigot.gui.*
import net.axay.kspigot.items.itemStack
import net.axay.kspigot.items.meta
import net.axay.kspigot.items.setLore
import net.axay.kspigot.main.KSpigotMainInstance
import net.kyori.adventure.text.Component
import net.wesjd.anvilgui.AnvilGUI
import org.bukkit.Material
import org.bukkit.command.CommandSender
import org.bukkit.command.defaults.BukkitCommand
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack

class NPCCommand : BukkitCommand("npc") {
    private val server = KSpigotMainInstance.server

    init {
        permission = "community.command.npc"
        server.commandMap.register("community", this)
    }

    override fun execute(sender: CommandSender, commandLabel: String, args: Array<out String>): Boolean {
        if (sender !is Player) {
            sender.sendMessage(message("not_a_player"))
            return false
        }

        if (!sender.hasPermission("community.command.npc")) {
            sender.sendMessage(
                message("no_permissions")
                    .replace("\${needed_permission}", "community.command.npc")
            )
            return false
        }

        if (args.isNotEmpty()) {
            sender.sendMessage(
                message("invalid_usage")
                    .replace("\${command_usage}", "/npc")
            )
            return false
        }

        sender.openGUI(
            kSpigotGUI(GUIType.THREE_BY_NINE) {
                page(1) {
                    val compound =
                        createRectCompound<ItemStack>(
                            Slots.RowOneSlotOne, Slots.RowThreeSlotNine,
                            iconGenerator = {
                                ItemStack(it)
                            },
                            onClick = { it, _ ->
                                val currentItem = it.bukkitEvent.currentItem

                                if (currentItem == null) {
                                    it.bukkitEvent.isCancelled = true
                                    return@createRectCompound
                                }

                                val displayName = currentItem.displayName().textValue().replace("§e", "")

                                val npc = npcById(displayName)

                                if (npc == null) {
                                    it.bukkitEvent.isCancelled = true
                                    return@createRectCompound
                                }

                                it.player.openGUI(
                                    kSpigotGUI(GUIType.THREE_BY_NINE) {
                                        page(1) {
                                            transitionFrom = PageChangeEffect.SLIDE_HORIZONTALLY
                                            transitionTo = PageChangeEffect.SLIDE_HORIZONTALLY

                                            placeholder(Slots.All, blackGlassItem())

                                            if (npc.isTeleportNPC) {
                                                button(Slots.RowTwoSlotTwo, itemStack(Material.NAME_TAG) {
                                                    meta {
                                                        displayName(Component.text("§aDisplay-Name"))

                                                        val data = npcData(npc.id)

                                                        setLore {
                                                            +"§aSet the displayed Name of the §eNPC"
                                                            +"§aCurrently: §e${if (data.displayName == null) data.displayName else "not set"} "
                                                        }
                                                    }
                                                }) {
                                                    AnvilGUI.Builder()
                                                        .onClose { obj: Player -> obj.closeInventory() }
                                                        .onComplete { _, text ->
                                                            val data = npcData(npc.id)

                                                            data.displayName = text

                                                            setNPCData(npc.id, data)

                                                            AnvilGUI.Response.close()
                                                        }
                                                        .text("Display-Name")
                                                        .itemLeft(
                                                            itemStack(Material.PAPER) {
                                                                meta {
                                                                    val data = npcData(npc.id)
                                                                    setLore {
                                                                        +"§aThe name to be displayed."
                                                                        +"§aCurrently: §e${if (data.displayName == null) data.displayName else "not set"} "
                                                                    }
                                                                }
                                                            }
                                                        )
                                                        .title("§aDisplay-Name")
                                                        .plugin(KSpigotMainInstance)
                                                        .open(sender)
                                                }

                                                button(Slots.RowTwoSlotThree, itemStack(Material.GRASS_BLOCK) {
                                                    meta {
                                                        displayName(Component.text("§aLocation"))

                                                        val data = npcData(npc.id)
                                                        val location = data.location

                                                        setLore {
                                                            +"§aThe Location where the §eNPC §aappends"
                                                            +"§aIt will be set to your current Location"
                                                            +"§aCurrently: §e${if (location == null) "§enot set" else "§e${location.world.name}§7, §e${location.blockX}§7, §e${location.blockY}§7, §e${location.blockZ}"}"
                                                        }
                                                    }
                                                }) { event ->
                                                    val data = npcData(npc.id)

                                                    data.location = event.player.location

                                                    setNPCData(npc.id, data)

                                                    event.player.closeInventory()
                                                }

                                                button(Slots.RowTwoSlotFour, itemStack(Material.CREEPER_HEAD) {
                                                    meta {
                                                        displayName(Component.text("§aLook at the Player"))

                                                        val data = npcData(npc.id)

                                                        setLore {
                                                            +"§aThe §eNPC §awill look at the players."
                                                            +"§aCurrently: §e${data.lookAtPlayer}"
                                                        }
                                                    }
                                                }) { event ->
                                                    val data = npcData(npc.id)

                                                    data.lookAtPlayer = !data.lookAtPlayer

                                                    setNPCData(npc.id, data)

                                                    event.player.closeInventory()
                                                }

                                                button(Slots.RowTwoSlotFive, itemStack(Material.ZOMBIE_HEAD) {
                                                    meta {
                                                        displayName(Component.text("§aImitate at the Player"))

                                                        val data = npcData(npc.id)

                                                        setLore {
                                                            +"§aThe §eNPC §awill imitate players actions like hits."
                                                            +"§aCurrently: §e${data.imitatePlayer}"
                                                        }
                                                    }
                                                }) { event ->
                                                    val data = npcData(npc.id)

                                                    data.imitatePlayer = !data.imitatePlayer

                                                    setNPCData(npc.id, data)

                                                    event.player.closeInventory()
                                                }

                                                button(Slots.RowTwoSlotSeven, itemStack(Material.DIRT) {
                                                    meta {
                                                        displayName(Component.text("§aTeleport Location"))

                                                        val data = npcData(npc.id)
                                                        val location = data.location

                                                        setLore {
                                                            +"§aPlayers who interact with this NPC, will be teleported to this Location."
                                                            +"§aIt will be set to your current Location"
                                                            +"§aCurrently: §e${if (location == null) "§enot set" else "§e${location.world.name}§7, §e${location.blockX}§7, §e${location.blockY}§7, §e${location.blockZ}"}"
                                                        }
                                                    }
                                                }) { event ->
                                                    val data = teleportNPCData(npc.id)

                                                    data.teleportLocation = event.player.location

                                                    setTeleportNPCData(npc.id, data)

                                                    event.player.closeInventory()
                                                }

                                                button(Slots.RowTwoSlotEight, itemStack(Material.NAME_TAG) {
                                                    meta {
                                                        displayName(Component.text("§aTitle"))

                                                        val data = teleportNPCData(npc.id)

                                                        setLore {
                                                            +"§aThis will be displayed on screen at interaction."
                                                            +"§aCurrently: §e${if (data.title == null) data.title else "not set"} "
                                                        }
                                                    }
                                                }) {
                                                    AnvilGUI.Builder()
                                                        .onClose { obj: Player -> obj.closeInventory() }
                                                        .onComplete { _, text ->
                                                            val data = teleportNPCData(npc.id)

                                                            data.title = text

                                                            setTeleportNPCData(npc.id, data)

                                                            AnvilGUI.Response.close()
                                                        }
                                                        .text("Title")
                                                        .itemLeft(
                                                            itemStack(Material.PAPER) {
                                                                meta {
                                                                    val data = teleportNPCData(npc.id)
                                                                    setLore {
                                                                        +"§aThis will be displayed on screen at interaction."
                                                                        +"§aCurrently: §e${if (data.title == null) data.title else "not set"} "
                                                                    }
                                                                }
                                                            }
                                                        )
                                                        .title("§aTitle")
                                                        .plugin(KSpigotMainInstance)
                                                        .open(sender)
                                                }

                                                button(Slots.RowTwoSlotNine, itemStack(Material.CREEPER_HEAD) {
                                                    meta {
                                                        displayName(Component.text("§aSkin"))

                                                        setLore {
                                                            +"§aSet the Skin"
                                                        }
                                                    }
                                                }) { event ->
                                                    AnvilGUI.Builder()
                                                        .onClose { obj: Player -> obj.closeInventory() }
                                                        .onComplete { _, text ->
                                                            fetchSkin(text) {
                                                                if (it == null) {
                                                                    event.player.closeInventory()
                                                                    return@fetchSkin
                                                                }

                                                                val data = npcData(npc.id)

                                                                data.skin = it

                                                                setNPCData(npc.id, data)
                                                            }

                                                            AnvilGUI.Response.close()
                                                        }
                                                        .text("Skin-ID")
                                                        .itemLeft(
                                                            itemStack(Material.PAPER) {
                                                                meta {
                                                                    setLore {
                                                                        +"§aSet the Skin"
                                                                    }
                                                                }
                                                            }
                                                        )
                                                        .title("§aSkin-ID")
                                                        .plugin(KSpigotMainInstance)
                                                        .open(sender)
                                                }
                                            } else {
                                                button(Slots.RowTwoSlotTwo, itemStack(Material.NAME_TAG) {
                                                    meta {
                                                        displayName(Component.text("§aDisplay-Name"))

                                                        val data = npcData(npc.id)

                                                        setLore {
                                                            +"§aSet the displayed Name of the §eNPC"
                                                            +"§aCurrently: §e${if (data.displayName == null) data.displayName else "not set"} "
                                                        }
                                                    }
                                                }) {
                                                    AnvilGUI.Builder()
                                                        .onClose { obj: Player -> obj.closeInventory() }
                                                        .onComplete { _, text ->
                                                            val data = npcData(npc.id)

                                                            data.displayName = text

                                                            setNPCData(npc.id, data)

                                                            AnvilGUI.Response.close()
                                                        }
                                                        .text("Display-Name")
                                                        .itemLeft(
                                                            itemStack(Material.PAPER) {
                                                                meta {
                                                                    val data = npcData(npc.id)
                                                                    setLore {
                                                                        +"§aThe name to be displayed."
                                                                        +"§aCurrently: §e${if (data.displayName == null) data.displayName else "not set"} "
                                                                    }
                                                                }
                                                            }
                                                        )
                                                        .title("§aDisplay-Name")
                                                        .plugin(KSpigotMainInstance)
                                                        .open(sender)
                                                }

                                                button(Slots.RowTwoSlotFour, itemStack(Material.GRASS_BLOCK) {
                                                    meta {
                                                        displayName(Component.text("§aLocation"))

                                                        val data = npcData(npc.id)
                                                        val location = data.location

                                                        setLore {
                                                            +"§aThe Location where the §eNPC §aappends"
                                                            +"§aIt will be set to your current Location"
                                                            +"§aCurrently: §e${if (location == null) "§enot set" else "§e${location.world.name}§7, §e${location.blockX}§7, §e${location.blockY}§7, §e${location.blockZ}"}"
                                                        }
                                                    }
                                                }) { event ->
                                                    val data = npcData(npc.id)

                                                    data.location = event.player.location

                                                    setNPCData(npc.id, data)

                                                    event.player.closeInventory()
                                                }

                                                button(Slots.RowTwoSlotSix, itemStack(Material.CREEPER_HEAD) {
                                                    meta {
                                                        displayName(Component.text("§aLook at the Player"))

                                                        val data = npcData(npc.id)

                                                        setLore {
                                                            +"§aThe §eNPC §awill look at the players."
                                                            +"§aCurrently: §e${data.lookAtPlayer}"
                                                        }
                                                    }
                                                }) { event ->
                                                    val data = npcData(npc.id)

                                                    data.lookAtPlayer = !data.lookAtPlayer

                                                    setNPCData(npc.id, data)

                                                    event.player.closeInventory()
                                                }

                                                button(Slots.RowTwoSlotEight, itemStack(Material.ZOMBIE_HEAD) {
                                                    meta {
                                                        displayName(Component.text("§aImitate at the Player"))

                                                        val data = npcData(npc.id)

                                                        setLore {
                                                            +"§aThe §eNPC §awill imitate players actions like hits."
                                                            +"§aCurrently: §e${data.imitatePlayer}"
                                                        }
                                                    }
                                                }) { event ->
                                                    val data = npcData(npc.id)

                                                    data.imitatePlayer = !data.imitatePlayer

                                                    setNPCData(npc.id, data)

                                                    event.player.closeInventory()
                                                }

                                                button(Slots.RowTwoSlotNine, itemStack(Material.CREEPER_HEAD) {
                                                    meta {
                                                        displayName(Component.text("§aSkin"))

                                                        setLore {
                                                            +"§aSet the Skin"
                                                        }
                                                    }
                                                }) { event ->
                                                    AnvilGUI.Builder()
                                                        .onClose { obj: Player -> obj.closeInventory() }
                                                        .onComplete { _, text ->
                                                            fetchSkin(text) {
                                                                if (it == null) {
                                                                    event.player.closeInventory()
                                                                    return@fetchSkin
                                                                }

                                                                val data = npcData(npc.id)

                                                                data.skin = it

                                                                setNPCData(npc.id, data)
                                                            }

                                                            AnvilGUI.Response.close()
                                                        }
                                                        .text("Skin-ID")
                                                        .itemLeft(
                                                            itemStack(Material.PAPER) {
                                                                meta {
                                                                    setLore {
                                                                        +"§aSet the Skin"
                                                                    }
                                                                }
                                                            }
                                                        )
                                                        .title("§aSkin-ID")
                                                        .plugin(KSpigotMainInstance)
                                                        .open(sender)
                                                }
                                            }
                                        }
                                    }
                                )
                            })

                    for (npc in NPCManager.registeredNPCs) {
                        compound.addContent(itemStack(Material.PLAYER_HEAD) {
                            meta {
                                displayName(Component.text("§e${npc.id}"))

                                setLore {
                                    +"§aEdit all information about this §eNPC§a."
                                }
                            }
                        })
                    }
                }
            }
        )

        return true
    }

    private fun npcById(id: String): NPC? {
        for (npc in NPCManager.registeredNPCs) {
            if (npc.id == id) return npc
        }

        return null
    }
}