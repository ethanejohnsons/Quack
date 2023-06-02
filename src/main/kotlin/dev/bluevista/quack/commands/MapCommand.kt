package dev.bluevista.quack.commands

import dev.bluevista.quack.getMap
import dev.bluevista.quack.getMaps
import dev.bluevista.quack.model.Q3Map
import dev.kord.common.Color
import dev.kord.core.Kord
import dev.kord.core.behavior.interaction.response.respond
import dev.kord.core.entity.interaction.GuildChatInputCommandInteraction
import dev.kord.core.event.interaction.AutoCompleteInteractionCreateEvent
import dev.kord.core.event.interaction.GuildChatInputCommandInteractionCreateEvent
import dev.kord.core.on
import dev.kord.rest.builder.interaction.string
import dev.kord.rest.builder.message.EmbedBuilder
import org.litote.kmongo.eq
import org.litote.kmongo.`in`


suspend fun registerMapCommand(kord: Kord) {
    kord.createGlobalChatInputCommand("map", "Provides information about the given Quake III map.") {
        string("name", "The name of the map.") {
            required = true
            autocomplete = true
        }
    }
    kord.on<GuildChatInputCommandInteractionCreateEvent> {
        if (interaction.command.rootName == "map") {
            handle(interaction)
        }
    }
    kord.on<AutoCompleteInteractionCreateEvent> {
        val focused = interaction.focusedOption.value
        val maps = getMaps(Q3Map::name `in` focused)
        for (map in maps) {
            if (maps.indexOf(map) == 25) break

            val name: String
            if (map.name.length > 25) {
                name = map.name.dropLast(map.name.length - 26).lowercase().replace(" ", "")
            } else {
                name = map.name.lowercase().replace(" ", "")
            }
            choice(name, name)
        }
    }
}

private suspend fun handle(interaction: GuildChatInputCommandInteraction) {
    val response = interaction.deferEphemeralResponse()
    val name = interaction.command.strings["name"]
    val map = getMap(Q3Map::name eq name)

    if (map == null) {
        response.respond { content = "Map: $name not found." }
    } else {
        val embed = EmbedBuilder()
        embed.title = "Quack - ${map.name} - ${map.creator}"
        embed.description = map.notes
        embed.color = Color(255, 255, 0)

        response.respond {
            embeds = mutableListOf(embed)
        }
    }
}
