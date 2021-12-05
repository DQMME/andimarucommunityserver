@file:Suppress("DEPRECATION")

package de.dqmme.andimaru.listener

import de.dqmme.andimaru.util.message
import de.dqmme.andimaru.util.rank
import net.axay.kspigot.event.listen
import org.bukkit.event.player.AsyncPlayerChatEvent

fun onChat() = listen<AsyncPlayerChatEvent> { event ->
    val player = event.player

    event.format = message("chat_format")
        .replace("\${player_name}", player.name)
        .replace("\${player_color}", player.rank().color)
        .replace("\${message}", event.message)
}