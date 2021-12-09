package de.dqmme.andimaru.npc

import com.github.juliarn.npc.NPCPool
import com.github.juliarn.npc.event.PlayerNPCInteractEvent
import com.github.juliarn.npc.event.PlayerNPCShowEvent
import com.github.juliarn.npc.modifier.MetadataModifier
import com.github.juliarn.npc.profile.Profile
import de.dqmme.andimaru.npc.impl.*
import com.github.juliarn.npc.NPC as FinalNPC
import net.axay.kspigot.main.KSpigotMainInstance
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import java.util.*

class NPCManager : Listener {
    private val instance = KSpigotMainInstance
    private val npcPool = NPCPool.builder(instance)
        .spawnDistance(100)
        .actionDistance(40)
        .tabListRemoveTicks(5)
        .build()

    companion object {
        val registeredNPCs = arrayListOf<NPC>()
    }

    fun registerNPCs() {
        instance.server.pluginManager.registerEvents(this, instance)
        val npcsToRegister = listOf(
            BookNPC,
            ClaimNPC0,
            ClaimNPC1,
            ClaimNPC2,
            CommonMobNPC,
            FairytaleNPC,
            FutureNPC,
            HeadNPC,
            MiddleAgeNPC,
            RareEnchantmentsNPC,
            RareMobNPC,
            SellFoodNPC,
            SellOresNPC,
            SellRaresNPC
        )

        for (npc in npcsToRegister) {
            val profile = createProfile(npc.displayName)

            val finalNPC = FinalNPC.builder()
                .profile(profile)
                .location(npc.location)
                .imitatePlayer(npc.imitatePlayer)
                .lookAtPlayer(npc.lookAtPlayer)
                .build(npcPool)

            npc.finalNPC = finalNPC

            registeredNPCs.add(npc)
        }
    }

    private fun createProfile(displayName: String): Profile {
        val profile = Profile(displayName)

        profile.uniqueId = UUID.randomUUID()

        profile.complete()

        return profile
    }

    @EventHandler
    fun onNPCInteract(event: PlayerNPCInteractEvent) {
        if(event.useAction != PlayerNPCInteractEvent.EntityUseAction.INTERACT) return

        for(npc in registeredNPCs) {
            if(event.npc != npc.finalNPC) continue

            npc.onInteract(event)
        }
    }

    @EventHandler
    fun onNPCShow(event: PlayerNPCShowEvent) {
        for(npc in registeredNPCs) {
            event.send(
                npc.finalNPC!!.metadata()
                    .queue(MetadataModifier.EntityMetadata.SKIN_LAYERS, true)
            )
        }
    }
}