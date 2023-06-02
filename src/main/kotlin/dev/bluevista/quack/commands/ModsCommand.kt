package dev.bluevista.quack.commands

import dev.bluevista.quack.getMods
import dev.bluevista.quack.model.Q3Mod
import dev.kord.common.Color
import dev.kord.core.Kord
import dev.kord.core.behavior.interaction.response.respond
import dev.kord.core.entity.interaction.GuildChatInputCommandInteraction
import dev.kord.core.event.interaction.GuildChatInputCommandInteractionCreateEvent
import dev.kord.core.on
import dev.kord.rest.builder.message.EmbedBuilder
import org.litote.kmongo.eq

var mods = mutableListOf<Q3Mod>()

suspend fun registerModsCommand(kord: Kord) {
    kord.createGlobalChatInputCommand("mods", "Provides a list of available Quake III mods.")
    kord.on<GuildChatInputCommandInteractionCreateEvent> {
        if (interaction.command.rootName == "mods") {
            handle(interaction)
        }
    }
}

private suspend fun handle(interaction: GuildChatInputCommandInteraction) {
    // Defer the response for later
    val response = interaction.deferEphemeralResponse()

    // Get all enabled mods
    mods.clear()
    mods.addAll(getMods(Q3Mod::enabled eq true))

    // Create the embed to display the mod names
    val embed = EmbedBuilder()
    embed.title = "Quack - Mods"
    embed.description = mods.joinToString("\n") { it.name }
    embed.color = Color(255, 255, 0)

    // Respond
    response.respond {
        embeds = mutableListOf(embed)
    }
}
