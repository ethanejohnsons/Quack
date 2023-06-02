package dev.bluevista.quack.commands

import dev.bluevista.quack.getMod
import dev.bluevista.quack.model.Q3Mod
import dev.kord.common.Color
import dev.kord.core.Kord
import dev.kord.core.behavior.interaction.response.respond
import dev.kord.core.entity.interaction.GuildChatInputCommandInteraction
import dev.kord.core.event.interaction.GuildChatInputCommandInteractionCreateEvent
import dev.kord.core.on
import dev.kord.rest.builder.interaction.string
import dev.kord.rest.builder.message.EmbedBuilder
import org.litote.kmongo.and
import org.litote.kmongo.eq


suspend fun registerModCommand(kord: Kord) {
    kord.createGlobalChatInputCommand("mod", "Provides information about the given Quake III mod.") {
        string("name", "The name of the mod.") {
            required = true
        }
    }
    kord.on<GuildChatInputCommandInteractionCreateEvent> {
        if (interaction.command.rootName == "map") {
            handle(interaction)
        }
    }
}

private suspend fun handle(interaction: GuildChatInputCommandInteraction) {
    val response = interaction.deferEphemeralResponse()
    val name = interaction.command.strings["name"]
        val mod = getMod(and(Q3Mod::name eq name, Q3Mod::enabled eq true))

    if (mod == null) {
        response.respond { content = "Mod: $name not found." }
    } else {
        val embed = EmbedBuilder()
        embed.title = "Quack - ${mod.name} - ${mod.creator}"
        embed.description = mod.description
        embed.description = mod.features.joinToString("\n* ")
        embed.color = Color(255, 255, 0)

        response.respond {
            embeds = mutableListOf(embed)
        }
    }
}
