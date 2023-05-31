package dev.bluevista.quack.commands

import com.mongodb.client.MongoDatabase
import dev.bluevista.quack.model.*
import dev.kord.common.Color
import dev.kord.common.entity.ButtonStyle
import dev.kord.core.Kord
import dev.kord.core.behavior.interaction.response.edit
import dev.kord.core.behavior.interaction.response.respond
import dev.kord.core.entity.interaction.GuildChatInputCommandInteraction
import dev.kord.core.event.interaction.ButtonInteractionCreateEvent
import dev.kord.core.event.interaction.GuildChatInputCommandInteractionCreateEvent
import dev.kord.core.on
import dev.kord.rest.builder.component.ActionRowBuilder
import dev.kord.rest.builder.interaction.boolean
import dev.kord.rest.builder.message.EmbedBuilder
import dev.kord.rest.builder.message.modify.InteractionResponseModifyBuilder
import dev.kord.rest.builder.message.modify.MessageModifyBuilder
import org.litote.kmongo.and
import org.litote.kmongo.div
import org.litote.kmongo.eq
import org.litote.kmongo.getCollection
import kotlin.math.min

var maps = mutableListOf<Q3Map>()
var index = 0
const val groupSize = 20

suspend fun registerMapsCommand(kord: Kord, database: MongoDatabase) {
    kord.createGlobalChatInputCommand("maps", "Provides a list of available Quake III maps.") {
        // Guns
        boolean("shotgun", "Whether the map has a Shotgun spawn.") { required = false }
        boolean("grenadelauncher", "Whether the map has a Grenade Launcher spawn.") { required = false }
        boolean("rocketlauncher", "Whether the map has a Rocket Launcher spawn.") { required = false }
        boolean("lightninggun", "Whether the map has a Lightning Gun spawn.") { required = false }
        boolean("railgun", "Whether the map has a Rail Gun spawn.") { required = false }
        boolean("plasmagun", "Whether the map has a Plasma Gun spawn.") { required = false }
        boolean("bfg", "Whether the map has a BFG spawn.") { required = false }

        // Armor
        boolean("heavyarmor", "Whether the map has a Heavy Armor spawn.") { required = false }
        boolean("lightarmor", "Whether the map has a Light Armor spawn.") { required = false }

        // Health
        boolean("megahealth", "Whether the map has a Mega Health spawn.") { required = false }
        boolean("personalmedkit", "Whether the map has a Personal Medkit spawn.") { required = false }

        // Hazards
        boolean("void", "Whether the map has a Void hazard.") { required = false }
        boolean("pit", "Whether the map has a Pit hazard.") { required = false }
        boolean("liquid", "Whether the map has a Liquid hazard.") { required = false }

        // Power Ups
        boolean("quaddamage", "Whether the map has a Quad Damage spawn.") { required = false }
        boolean("regeneration", "Whether the map has a Regeneration spawn.") { required = false }
        boolean("battlesuit", "Whether the map has a Battle Suit spawn.") { required = false }
        boolean("haste", "Whether the map has a Haste spawn.") { required = false }
        boolean("invisibility", "Whether the map has an Invisibility spawn.") { required = false }
        boolean("flight", "Whether the map has a Flight spawn.") { required = false }
        boolean("personalteleporter", "Whether the map has a Personal Teleporter spawn.") { required = false }
    }

    kord.on<GuildChatInputCommandInteractionCreateEvent> {
        if (interaction.command.rootName == "maps") {
            handle(database, interaction)
        }
    }

    kord.on<ButtonInteractionCreateEvent> {
        when (interaction.component.customId) {
            "previous-button" -> {
                val response = interaction.deferPublicMessageUpdate()
                index -= groupSize
                if (index < 0) index = 0
                response.edit(goToPage(index))
            }
            "next-button" -> {
                val response = interaction.deferPublicMessageUpdate()
                index += groupSize
                if (index > maps.size - 1) index = maps.size - 1
                response.edit(goToPage(index))
            }
        }
    }
}

private suspend fun handle(database: MongoDatabase, interaction: GuildChatInputCommandInteraction) {
    val response = interaction.deferPublicResponse()
    val collection = database.getCollection<Q3Map>("q3maps")
    val booleans = interaction.command.booleans

    // Guns
    val shotgun = booleans["shotgun"]
    val grenadelauncher = booleans["grenadelauncher"]
    val rocketlauncher = booleans["rocketlauncher"]
    val lightninggun = booleans["lightninggun"]
    val railgun = booleans["railgun"]
    val plasmagun = booleans["plasmagun"]
    val bfg = booleans["bfg"]

    // Armor
    val heavyarmor = booleans["heavyarmor"]
    val lightarmor = booleans["lightarmor"]

    // Health
    val megahealth = booleans["megahealth"]
    val personalmedkit = booleans["personalmedkit"]

    // Hazards
    val void = booleans["void"]
    val pit = booleans["pit"]
    val liquid = booleans["liquid"]

    // Power Ups
    val quaddamage = booleans["quaddamage"]
    val regeneration = booleans["regeneration"]
    val battlesuit = booleans["battlesuit"]
    val haste = booleans["haste"]
    val invisibility = booleans["invisibility"]
    val flight = booleans["flight"]
    val personalteleporter = booleans["personalteleporter"]

    maps.clear()
    maps.addAll(collection.find(
        and(
            // Guns
            if (shotgun != null) Q3Map::guns / Q3Guns::shotgun eq shotgun else null,
            if (grenadelauncher != null) Q3Map::guns / Q3Guns::grenadelauncher eq grenadelauncher else null,
            if (rocketlauncher != null) Q3Map::guns / Q3Guns::rocketlauncher eq rocketlauncher else null,
            if (lightninggun != null) Q3Map::guns / Q3Guns::lightninggun eq lightninggun else null,
            if (railgun != null) Q3Map::guns / Q3Guns::railgun eq railgun else null,
            if (plasmagun != null) Q3Map::guns / Q3Guns::plasmagun eq plasmagun else null,
            if (bfg != null) Q3Map::guns / Q3Guns::bfg eq bfg else null,

            // Armor
            if (heavyarmor != null) Q3Map::armor / Q3Armor::heavyarmor eq heavyarmor else null,
            if (lightarmor != null) Q3Map::armor / Q3Armor::lightarmor eq lightarmor else null,

            // Health
            if (megahealth != null) Q3Map::health / Q3Health::megahealth eq megahealth else null,
            if (personalmedkit != null) Q3Map::health / Q3Health::personalmedkit eq personalmedkit else null,

            // Hazards
            if (void != null) Q3Map::hazards / Q3Hazards::void eq void else null,
            if (pit != null) Q3Map::hazards / Q3Hazards::pit eq pit else null,
            if (liquid != null) Q3Map::hazards / Q3Hazards::liquid eq liquid else null,

            // Power Ups
            if (quaddamage != null) Q3Map::powerups / Q3PowerUps::quaddamage eq quaddamage else null,
            if (regeneration != null) Q3Map::powerups / Q3PowerUps::regeneration eq regeneration else null,
            if (battlesuit != null) Q3Map::powerups / Q3PowerUps::battlesuit eq battlesuit else null,
            if (haste != null) Q3Map::powerups / Q3PowerUps::haste eq haste else null,
            if (invisibility != null) Q3Map::powerups / Q3PowerUps::invisibility eq invisibility else null,
            if (flight != null) Q3Map::powerups / Q3PowerUps::flight eq flight else null,
            if (personalteleporter != null) Q3Map::powerups / Q3PowerUps::personalteleporter eq personalteleporter else null,
        )
    ))

    response.respond(goToPage(1))
}

private fun goToPage(index: Int) : InteractionResponseModifyBuilder.() -> Unit {
    val embed = EmbedBuilder()
    embed.title = "Quack - Maps (${index / groupSize}/${maps.size / groupSize})"
    embed.description = maps.slice(index..min(index + groupSize, maps.size - 1)).joinToString("\n") { it.name }
    embed.color = Color(255, 255, 0)

    val actionRow = ActionRowBuilder()
    actionRow.interactionButton(ButtonStyle.Secondary, "previous-button") {
        label = "⬅"
    }
    actionRow.interactionButton(ButtonStyle.Secondary, "next-button") {
        label = "➡"
    }

    return {
        embeds = mutableListOf(embed)
        components = mutableListOf(actionRow)
    }
}