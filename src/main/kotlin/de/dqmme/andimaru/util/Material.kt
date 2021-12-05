package de.dqmme.andimaru.util

import org.bukkit.Material

fun Material.isSign(): Boolean {
    val signMaterials = listOf(
        Material.ACACIA_SIGN,
        Material.ACACIA_WALL_SIGN,
        Material.BIRCH_SIGN,
        Material.BIRCH_WALL_SIGN,
        Material.CRIMSON_SIGN,
        Material.CRIMSON_WALL_SIGN,
        Material.DARK_OAK_SIGN,
        Material.DARK_OAK_WALL_SIGN,
        Material.JUNGLE_SIGN,
        Material.JUNGLE_WALL_SIGN,
        Material.OAK_SIGN,
        Material.OAK_WALL_SIGN,
        Material.SPRUCE_SIGN,
        Material.SPRUCE_WALL_SIGN,
        Material.WARPED_SIGN,
        Material.WARPED_WALL_SIGN
    )

    return signMaterials.contains(this)
}