package dev.bluevista.quack

import dev.bluevista.quack.model.Q3Map
import dev.bluevista.quack.model.Q3Mod
import org.bson.conversions.Bson
import org.litote.kmongo.findOne
import org.litote.kmongo.getCollection

val q3maps = database.getCollection<Q3Map>("q3maps")
val q3mods = database.getCollection<Q3Mod>("q3mods")

fun getMap(filter: Bson): Q3Map? {
    return q3maps.findOne(filter)
}

fun getMaps(filter: Bson): List<Q3Map> {
    return q3maps.find(filter).toList()
}

fun getMaps(): List<Q3Map> {
    return q3maps.find().toList()
}

fun getMod(filter: Bson): Q3Mod? {
    return q3mods.findOne(filter)
}

fun getMods(filter: Bson): List<Q3Mod> {
    return q3mods.find(filter).toList()
}

fun getMods(): List<Q3Mod> {
    return q3mods.find().toList()
}