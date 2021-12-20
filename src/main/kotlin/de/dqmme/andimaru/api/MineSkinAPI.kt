package de.dqmme.andimaru.api

import com.google.gson.Gson
import com.google.gson.JsonObject
import de.dqmme.andimaru.dataclass.Skin
import okhttp3.*
import java.io.IOException

fun fetchSkin(mineskinId: String, callback: (Skin?) -> Unit) {
    val client = OkHttpClient()

    val request = Request.Builder()
        .url("https://api.mineskin.org/get/id/$mineskinId")
        .build()

    val call = client.newCall(request)

    call.enqueue(object: Callback {
        override fun onResponse(call: Call, response: Response) {
            val responseString = response.body!!.string()

            val jsonObject= Gson().fromJson(responseString, JsonObject::class.java)
            val textures = jsonObject["data"].asJsonObject["texture"].asJsonObject
            val value = textures["value"].asString
            val signature = textures["signature"].asString

            callback.invoke(Skin(signature, value))
        }

        override fun onFailure(call: Call, e: IOException) {
            callback.invoke(null)
        }
    })
}