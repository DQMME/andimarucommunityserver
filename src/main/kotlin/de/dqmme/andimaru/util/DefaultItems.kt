package de.dqmme.andimaru.util

import net.axay.kspigot.items.itemStack
import net.axay.kspigot.items.meta
import net.axay.kspigot.items.setLore
import net.kyori.adventure.text.Component
import org.bukkit.Material
import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.ItemStack
import org.bukkit.inventory.meta.EnchantmentStorageMeta

fun blackGlassItem() = itemStack(Material.BLACK_STAINED_GLASS_PANE) {
    meta {
        displayName(Component.text("§c"))
    }
}

fun enchantedBookItem(enchantment: Enchantment, price: Double, germanEnchantmentName: String) =
    itemStack(Material.ENCHANTED_BOOK) {
        meta<EnchantmentStorageMeta> {
            displayName(Component.text("§6$germanEnchantmentName I"))

            setLore {
                +"§6Preis: $price"
            }

            addStoredEnchant(enchantment, 1, true)
        }
    }

fun newEnchantedBook(oldBook: ItemStack) = itemStack(Material.ENCHANTED_BOOK) {
    meta<EnchantmentStorageMeta> {
        for (enchantment in (oldBook.itemMeta as EnchantmentStorageMeta).storedEnchants) {
            addStoredEnchant(enchantment.key, 1, true)
        }
    }
}
