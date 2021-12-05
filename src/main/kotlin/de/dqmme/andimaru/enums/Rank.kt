package de.dqmme.andimaru.enums

enum class Rank(val prefix: String, val permission: String?, val color: String) {
    DEFAULT("§7Spieler | ", null, "§7"),
    SUB("§bSub §7| ", "community.rank.sub", "§b"),
    ADMIN("§cAdmin §7| ", "community.rank.admin", "§c")
}