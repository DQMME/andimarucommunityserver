package de.dqmme.andimaru

import de.dqmme.andimaru.command.*
import de.dqmme.andimaru.enchantment.CustomEnchants
import de.dqmme.andimaru.enchantment.CustomEnchantsListener
import de.dqmme.andimaru.listener.*
import de.dqmme.andimaru.manager.*
import de.dqmme.andimaru.npc.NPCManager
import de.dqmme.andimaru.npc.reloadNPCData
import de.dqmme.andimaru.util.*
import net.axay.kspigot.main.KSpigot

class AndimaruCommunity : KSpigot() {
    override fun startup() {
        onPing()
        saveFiles()

        reloadAdminClaims()
        reloadBuyClaims()
        reloadCoins()
        reloadConfigFile()
        reloadCommandSigns()
        reloadHomes()
        reloadMessages()
        reloadPlayerData()
        reloadPrices()

        registerCommands()
        registerEnchantments()
        registerListeners()

        reloadNPCData()
        NPCManager().registerNPCs()

        AdminClaimManager()
        ClaimManager()

        refreshPlayerTablist()
        sendPlayerCoins()
    }

    override fun shutdown() {
    }

    private fun saveFiles() {
        if (!configFile.exists()) {
            saveResource("config.yml", false)
        }

        if (!priceFile.exists()) {
            saveResource("prices.yml", false)
        }

        if (!messageFile.exists()) {
            saveResource("messages.yml", false)
        }
    }

    private fun registerCommands() {
        AdminClaimCommand()
        BuyClaimCommand()
        CoinsCommand()
        CommandSignCommand()
        CommunityCommand()
        GamemodeCommand()
        HomeCommand()
        NPCCommand()
        PayCommand()
        SetHomeCommand()
        SetSpawnCommand()
        SpawnCommand()
        WorldManagerCommand()
    }

    private fun registerEnchantments() {
        CustomEnchants.registerEnchantment(CustomEnchants.AUTO_SMELT)
        CustomEnchants.registerEnchantment(CustomEnchants.TELEKINESIS)
    }

    private fun registerListeners() {
        onChat()
        onDeath()
        onJoin()
        onQuit()
        onRespawn()

        val bedListener = BedListener()
        bedListener.onBedEnter()
        bedListener.onBedLeave()

        CommandSignListener()
        CustomEnchantsListener()
        EntityManager()
    }
}