package de.dqmme.andimaru.enchantment

import net.axay.kspigot.event.listen
import org.bukkit.Material
import org.bukkit.event.block.BlockBreakEvent
import org.bukkit.inventory.ItemStack

class CustomEnchantsListener {
    init {
        onBlockBreak()
    }

    private fun onBlockBreak() = listen<BlockBreakEvent> { event ->
        val player = event.player
        val itemInMainHand = player.inventory.itemInMainHand

        val drops = event.block.drops

        if (itemInMainHand.itemMeta.hasEnchant(CustomEnchants.AUTO_SMELT)) {
            when(event.block.type) {
                Material.RAW_IRON_BLOCK, Material.DEEPSLATE_IRON_ORE, Material.IRON_ORE -> {
                    drops.clear()
                    drops.add(ItemStack(Material.IRON_INGOT))
                    event.isDropItems = false
                }

                Material.RAW_COPPER_BLOCK, Material.DEEPSLATE_COPPER_ORE, Material.COPPER_ORE -> {
                    drops.clear()
                    drops.add(ItemStack(Material.COPPER_INGOT))
                    event.isDropItems = false
                }

                Material.RAW_GOLD_BLOCK, Material.DEEPSLATE_GOLD_ORE, Material.GOLD_ORE -> {
                    drops.clear()
                    drops.add(ItemStack(Material.GOLD_INGOT))
                    event.isDropItems = false
                }
                else -> {}
            }
        }

        if (itemInMainHand.itemMeta.hasEnchant(CustomEnchants.TELEKINESIS)) {
            for (drop in drops) {
                if (player.inventory.firstEmpty() != -1) {
                    player.inventory.addItem(drop)
                } else {
                    player.world.dropItemNaturally(player.location, drop)
                }
            }
        }
    }
}