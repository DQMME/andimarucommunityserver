package de.dqmme.andimaru.npc

import com.github.juliarn.npc.NPCPool
import com.github.juliarn.npc.event.PlayerNPCInteractEvent
import com.github.juliarn.npc.profile.Profile
import com.github.juliarn.npc.NPC as FinalNPC
import net.axay.kspigot.main.KSpigotMainInstance
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener

class NPCManager : Listener {
    private val instance = KSpigotMainInstance
    private val npcPool = NPCPool.builder(instance)
        .spawnDistance(60)
        .actionDistance(30)
        .tabListRemoveTicks(20)
        .build()

    private val registeredNPCs = arrayListOf<NPC>()

    fun registerNPCs() {
        instance.server.pluginManager.registerEvents(this, instance)
        val npcsToRegister = listOf<NPC>()

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
        profile.complete()
        return profile
    }

    @EventHandler
    fun onNPCInteract(event: PlayerNPCInteractEvent) {
        if(event.useAction != PlayerNPCInteractEvent.EntityUseAction.INTERACT_AT) return

        for(npc in registeredNPCs) {
            if(event.npc != npc.finalNPC) return

            npc.onInteract(event)
        }
    }
}