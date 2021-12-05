package de.dqmme.andimaru.manager

import net.axay.kspigot.event.listen
import net.axay.kspigot.main.KSpigotMainInstance
import net.axay.kspigot.runnables.task
import org.bukkit.NamespacedKey
import org.bukkit.entity.EntityType
import org.bukkit.entity.LivingEntity
import org.bukkit.event.entity.CreatureSpawnEvent
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason
import org.bukkit.event.entity.EntityDeathEvent
import org.bukkit.event.world.ChunkLoadEvent
import org.bukkit.persistence.PersistentDataType

class EntityManager {
    init {
        killNearEntities()
        registerListeners()
    }

    private val allowedSpawnReasons = listOf(
        SpawnReason.BUILD_SNOWMAN,
        SpawnReason.BUILD_WITHER,
        SpawnReason.BREEDING,
        SpawnReason.DISPENSE_EGG,
        SpawnReason.EGG,
        SpawnReason.SPAWNER_EGG
    )

    private val allowedEntityTypes = listOf(
        EntityType.DROPPED_ITEM,
        EntityType.ITEM_FRAME,
        EntityType.ARMOR_STAND,
        EntityType.ARROW,
        EntityType.BOAT,
        EntityType.FISHING_HOOK,
        EntityType.MINECART,
        EntityType.MINECART_CHEST,
        EntityType.MINECART_FURNACE,
        EntityType.MINECART_HOPPER,
        EntityType.MINECART_TNT,
        EntityType.PAINTING,
        EntityType.PLAYER,
        EntityType.PRIMED_TNT
    )

    private fun onChunkLoad() = listen<ChunkLoadEvent> { event ->
        val instance = KSpigotMainInstance
        val server = instance.server
        val world = server.getWorld("world") ?: return@listen

        if (event.world != world) return@listen

        for (entity in event.chunk.entities) {
            val entityDataContainer = entity.persistentDataContainer

            if (entityDataContainer.has(NamespacedKey(instance, "dontkill"), PersistentDataType.STRING)) continue

            if (allowedEntityTypes.contains(entity.type)) continue

            if (entity !is LivingEntity) continue

            val livingEntityDataContainer = entity.persistentDataContainer
            livingEntityDataContainer.set(NamespacedKey(instance, "dontdrop"), PersistentDataType.STRING, "x")

            entity.health = 0.0
        }
    }

    private fun onCreatureSpawn() = listen<CreatureSpawnEvent> { event ->
        val instance = KSpigotMainInstance
        val server = instance.server
        val world = server.getWorld("world") ?: return@listen

        if (event.entity.world != world) return@listen

        if (allowedSpawnReasons.contains(event.spawnReason)) {
            val entityDataContainer = event.entity.persistentDataContainer
            entityDataContainer.set(NamespacedKey(instance, "dontkill"), PersistentDataType.STRING, "x")

            return@listen
        }

        event.isCancelled = true
    }

    private fun onEntityDeath() = listen<EntityDeathEvent> { event ->
        val instance = KSpigotMainInstance

        val entityDataContainer = event.entity.persistentDataContainer

        if (!entityDataContainer.has(NamespacedKey(instance, "dontdrop"), PersistentDataType.STRING)) return@listen

        event.drops.clear()
    }

    private fun killNearEntities() = task(true, 0, 10 * 20) {
        val instance = KSpigotMainInstance
        val server = instance.server
        val world = server.getWorld("world") ?: return@task

        for (onlinePlayer in server.onlinePlayers) {
            if (onlinePlayer.world != world) return@task

            world.pvp = false

            val chunk = onlinePlayer.chunk

            for (entity in chunk.entities) {
                val entityDataContainer = entity.persistentDataContainer

                if (entityDataContainer.has(NamespacedKey(instance, "dontkill"), PersistentDataType.STRING)) continue

                if (allowedEntityTypes.contains(entity.type)) continue

                if (entity !is LivingEntity) continue

                entityDataContainer.set(NamespacedKey(instance, "dontdrop"), PersistentDataType.STRING, "x")

                entity.health = 0.0
            }
        }
    }

    private fun registerListeners() {
        onChunkLoad()
        onCreatureSpawn()
        onEntityDeath()
    }
}