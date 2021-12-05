package de.dqmme.andimaru

import de.dqmme.andimaru.command.*
import de.dqmme.andimaru.listener.*
import de.dqmme.andimaru.manager.EntityManager
import de.dqmme.andimaru.util.*
import net.axay.kspigot.main.KSpigot

class AndimaruCommunity : KSpigot() {
    override fun startup() {
        saveFiles()
        reloadCoins()
        reloadCommandSigns()
        reloadHomes()
        reloadMessages()
        reloadPlayerData()
        registerCommands()
        registerListeners()
    }

    override fun shutdown() {
    }

    private fun saveFiles() {
        if (!messageFile.exists()) {
            saveResource("messages.yml", false)
        }
    }

    private fun registerCommands() {
        CoinsCommand()
        CommandSignCommand()
        CommunityCommand()
        GamemodeCommand()
        HomeCommand()
        SetHomeCommand()
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
        EntityManager()
    }
}