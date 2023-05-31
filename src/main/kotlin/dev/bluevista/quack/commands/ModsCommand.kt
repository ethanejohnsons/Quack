package dev.bluevista.quack.commands

import com.mongodb.client.MongoDatabase
import dev.bluevista.quack.model.Q3Mod
import dev.kord.core.Kord
import dev.kord.core.behavior.interaction.response.respond
import dev.kord.core.entity.interaction.GuildChatInputCommandInteraction
import dev.kord.core.event.interaction.GuildChatInputCommandInteractionCreateEvent
import dev.kord.core.on
import org.litote.kmongo.eq
import org.litote.kmongo.getCollection

suspend fun registerModsCommand(kord: Kord, database: MongoDatabase) {
    kord.createGlobalChatInputCommand("mods", "Provides a list of available Quake III mods.")
    kord.on<GuildChatInputCommandInteractionCreateEvent> {
        if (interaction.command.rootName == "mods") {
            handle(database, interaction)
        }
    }
}

private suspend fun handle(database: MongoDatabase, interaction: GuildChatInputCommandInteraction) {
    val response = interaction.deferPublicResponse()
    val collection = database.getCollection<Q3Mod>("q3mods")
    val result = collection.find(Q3Mod::enabled eq true).toList()
    val names = result.joinToString(separator = "\n") { it.name }
    response.respond { content = names }
}