package de.dqmme.andimaru.util

import com.google.gson.Gson
import com.google.gson.JsonObject
import net.kyori.adventure.text.Component

fun Component.textValue(): String {
    val component = toString()
        .replace("TranslatableComponentImpl", "")
        .replace("TextComponentImpl", "")
        .replace("StyleImpl", "")
        .replace("HoverEvent", "")
        .replace("NamedTextColor", "")
        .replace("KeyImpl", "")
        .replace("ShowItem", "")

    val jsonObject = Gson().fromJson(component, JsonObject::class.java)

    val argsObject = jsonObject["args"].asJsonArray[0].asJsonObject
    val childrenObject = argsObject["children"].asJsonArray[0].asJsonObject

    return childrenObject["content"].asString
}

fun Component.textValueNullable(): String? {
    val component = toString()
        .replace("TranslatableComponentImpl", "")
        .replace("TextComponentImpl", "")
        .replace("StyleImpl", "")
        .replace("HoverEvent", "")
        .replace("NamedTextColor", "")
        .replace("KeyImpl", "")
        .replace("ShowItem", "")

    val jsonObject = Gson().fromJson(component, JsonObject::class.java)

    val argsObject = jsonObject["args"].asJsonArray[0].asJsonObject
    val childrenObject = argsObject["children"].asJsonArray[0].asJsonObject

    return childrenObject["content"]?.asString ?: return null
}