package de.dqmme.andimaru.util

import org.bukkit.enchantments.Enchantment
import org.bukkit.inventory.meta.ItemMeta

fun ItemMeta.hasEnchant(enchantment: Enchantment, level: Int): Boolean {
    if (hasEnchant(enchantment) && getEnchantLevel(enchantment) == level) return true

    return false
}