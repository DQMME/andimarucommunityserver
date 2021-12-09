package de.dqmme.andimaru.util

import com.google.gson.Gson
import com.google.gson.JsonObject
import net.axay.kspigot.main.KSpigotMainInstance
import okhttp3.*
import org.bukkit.OfflinePlayer
import java.io.IOException
import java.util.*

private val client = OkHttpClient()

fun offlinePlayerByName(name: String, callback: (OfflinePlayer?) -> Unit) {
    val request = Request.Builder()
        .url("https://api.mojang.com/users/profiles/minecraft/${name.lowercase()}")
        .build()

    val call = client.newCall(request)

    call.enqueue(object : Callback {
        override fun onResponse(call: Call, response: Response) {
            val responseString = response.body!!.string()

            val jsonObject = Gson().fromJson(responseString, JsonObject::class.java)

            val id = jsonObject["id"]?.asString

            if (id == null) {
                callback.invoke(null)
                return
            }

            val uuidString = id.replaceFirst(
                "([0-9a-fA-F]{8})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]{4})([0-9a-fA-F]+)".toRegex(),
                "$1-$2-$3-$4-$5"
            )

            println(id)
            println(uuidString)

            callback.invoke(
                KSpigotMainInstance.server.getOfflinePlayer(
                    UUID.fromString(uuidString)
                )
            )
        }

        override fun onFailure(call: Call, e: IOException) {
            callback.invoke(null)
            return
        }
    })
}