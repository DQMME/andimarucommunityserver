package de.dqmme.andimaru.enchantment

import io.papermc.paper.enchantments.EnchantmentRarity
import net.kyori.adventure.text.Component
import org.bukkit.NamespacedKey
import org.bukkit.enchantments.Enchantment
import org.bukkit.enchantments.EnchantmentTarget
import org.bukkit.entity.EntityCategory
import org.bukkit.inventory.EquipmentSlot
import org.bukkit.inventory.ItemStack

class EnchantmentWrapper(
    namespace: String,
    private val name: String,
    private val maxLevel: Int,
    private val enchantmentTarget: EnchantmentTarget
) :
    Enchantment(NamespacedKey.minecraft(namespace)) {
    override fun translationKey() = ""

    override fun getName() = name

    override fun getMaxLevel() = maxLevel

    override fun getStartLevel() = 0

    override fun getItemTarget() = enchantmentTarget

    override fun isTreasure() = false

    override fun isCursed() = false

    override fun conflictsWith(other: Enchantment) = false

    override fun canEnchantItem(item: ItemStack) = false

    override fun displayName(level: Int): Component = Component.text(name)

    override fun isTradeable() = false

    override fun isDiscoverable() = false

    override fun getRarity() = EnchantmentRarity.VERY_RARE

    override fun getDamageIncrease(level: Int, entityCategory: EntityCategory) = 0.toFloat()

    override fun getActiveSlots(): MutableSet<EquipmentSlot> = mutableSetOf()
}