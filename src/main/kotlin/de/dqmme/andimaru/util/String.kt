package de.dqmme.andimaru.util

fun String.replace(replacement: String, newValue: Int): String {
    return replace(replacement, newValue.toString())
}

fun String.replace(replacement: String, newValue: Double): String {
    return replace(replacement, newValue.toString())
}