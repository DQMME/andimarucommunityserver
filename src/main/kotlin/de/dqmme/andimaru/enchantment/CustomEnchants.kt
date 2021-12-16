package de.dqmme.andimaru.enchantment

import de.dqmme.andimaru.manager.message
import org.bukkit.enchantments.Enchantment
import org.bukkit.enchantments.EnchantmentTarget
import java.lang.reflect.Field

class CustomEnchants {
    companion object {
        val TELEKINESIS = EnchantmentWrapper("telekinesis", "Telekinesis", 1, EnchantmentTarget.TOOL)
        val AUTO_SMELT = EnchantmentWrapper("autosmelt", "Auto-Smelt", 1, EnchantmentTarget.TOOL)

        fun registerEnchantment(enchantment: Enchantment) {
            var registered = true

            try {
                val field: Field = Enchantment::class.java.getDeclaredField("acceptingNew")
                field.isAccessible = true
                field.set(null, true)
                Enchantment.registerEnchantment(enchantment)
            } catch (e: Exception) {
                registered = false
                e.printStackTrace()
            }

            if (registered) {
                println("${message("prefix")} §aEnchantment §e${enchantment.key} §asuccessfully registered.")
            }
        }
    }
}