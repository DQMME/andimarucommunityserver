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

fun Material.isTool(): Boolean {
    val tools = listOf(
        Material.WOODEN_AXE,
        Material.WOODEN_HOE,
        Material.WOODEN_PICKAXE,
        Material.WOODEN_SHOVEL,
        Material.STONE_AXE,
        Material.STONE_HOE,
        Material.STONE_PICKAXE,
        Material.STONE_SHOVEL,
        Material.GOLDEN_AXE,
        Material.GOLDEN_HOE,
        Material.GOLDEN_PICKAXE,
        Material.GOLDEN_SHOVEL,
        Material.IRON_AXE,
        Material.IRON_HOE,
        Material.IRON_PICKAXE,
        Material.IRON_SHOVEL,
        Material.DIAMOND_AXE,
        Material.DIAMOND_HOE,
        Material.DIAMOND_PICKAXE,
        Material.DIAMOND_SHOVEL,
        Material.NETHERITE_AXE,
        Material.NETHERITE_HOE,
        Material.NETHERITE_PICKAXE,
        Material.NETHERITE_SHOVEL
    )

    return tools.contains(this)
}