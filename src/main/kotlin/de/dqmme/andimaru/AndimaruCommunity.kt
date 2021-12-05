package de.dqmme.andimaru

import de.dqmme.andimaru.command.CoinsCommand
import de.dqmme.andimaru.command.CommandSignCommand
import de.dqmme.andimaru.command.CommunityCommand
import de.dqmme.andimaru.command.GamemodeCommand
import de.dqmme.andimaru.listener.*
import de.dqmme.andimaru.manager.EntityManager
import de.dqmme.andimaru.util.messageFile
import de.dqmme.andimaru.util.reloadCoins
import de.dqmme.andimaru.util.reloadCommandSigns
import de.dqmme.andimaru.util.reloadMessages
import net.axay.kspigot.main.KSpigot

class AndimaruCommunity : KSpigot() {
    override fun startup() {
        saveFiles()
        reloadCoins()
        reloadCommandSigns()
        reloadMessages()
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
    }

    private fun registerListeners() {
        onChat()
        onDeath()
        onJoin()
        onQuit()

        val bedListener = BedListener()
        bedListener.onBedEnter()
        bedListener.onBedLeave()

        CommandSignListener()
        EntityManager()
    }
}