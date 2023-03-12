const { SlashCommandBuilder } = require("@discordjs/builders");
const { EmbedBuilder } = require('discord.js');

module.exports = {
    data: new SlashCommandBuilder()
        .setName("maps")
        .setDescription("Get a list of available maps.")

        // Weapons
        .addBooleanOption(option => option
            .setName("shotgun")
            .setDescription("Whether the map has a Shotgun.")
            .setRequired(false)
        )
        .addBooleanOption(option => option
            .setName("grenadelauncher")
            .setDescription("Whether the map has a grenade launcher.")
            .setRequired(false)
        )
        .addBooleanOption(option => option
            .setName("rocketlauncher")
            .setDescription("Whether the map has a Rocket Launcher.")
            .setRequired(false)
        )
        .addBooleanOption(option => option
            .setName("lightninggun")
            .setDescription("Whether the map has a Lightning Gun.")
            .setRequired(false)
        )
        .addBooleanOption(option => option
            .setName("railgun")
            .setDescription("Whether the map has a Rail Gun.")
            .setRequired(false)
        )
        .addBooleanOption(option => option
            .setName("plasmagun")
            .setDescription("Whether the map has a Plasma Gun.")
            .setRequired(false)
        )
        .addBooleanOption(option => option
            .setName("bfg")
            .setDescription("Whether the map has a BFG.")
            .setRequired(false)
        )

        // Armor
        .addBooleanOption(option => option
            .setName("lightarmor")
            .setDescription("Whether the map has Light Armor.")
            .setRequired(false)
        )
        .addBooleanOption(option => option
            .setName("heavyarmor")
            .setDescription("Whether the map has Heavy Armor.")
            .setRequired(false)
        )

        // Health
        .addBooleanOption(option => option
            .setName("megahealth")
            .setDescription("Whether the map has a Mega Health.")
            .setRequired(false)
        )
        .addBooleanOption(option => option
            .setName("personalmedkit")
            .setDescription("Whether the map has a Personal Medkit.")
            .setRequired(false)
        )

        // Hazards
        .addBooleanOption(option => option
            .setName("void")
            .setDescription("Whether the map has a void hazard.")
            .setRequired(false)
        )
        .addBooleanOption(option => option
            .setName("pit")
            .setDescription("Whether the map has a pit hazard.")
            .setRequired(false)
        )
        .addBooleanOption(option => option
            .setName("liquid")
            .setDescription("Whether the map has a lava or slime hazard.")
            .setRequired(false)
        )

        // Power Ups
        .addBooleanOption(option => option
            .setName("quaddamage")
            .setDescription("Whether the map has a Quad Damage power up.")
            .setRequired(false)
        )
        .addBooleanOption(option => option
            .setName("regeneration")
            .setDescription("Whether the map has a Regeneration power up.")
            .setRequired(false)
        )
        .addBooleanOption(option => option
            .setName("battlesuit")
            .setDescription("Whether the map has a Battle Suit power up.")
            .setRequired(false)
        )
        .addBooleanOption(option => option
            .setName("haste")
            .setDescription("Whether the map has a Haste power up.")
            .setRequired(false)
        )
        .addBooleanOption(option => option
            .setName("invisibility")
            .setDescription("Whether the map has an Invisibility power up.")
            .setRequired(false)
        )
        .addBooleanOption(option => option
            .setName("flight")
            .setDescription("Whether the map has a Flight power up.")
            .setRequired(false)
        )
        .addBooleanOption(option => option
            .setName("personalteleporter")
            .setDescription("Whether the map has a Personal Teleporter.")
            .setRequired(false)
        )
        .addStringOption(option => option
            .setName("mode")
            .setDescription("The mode the map is intended to be played on.")
            .setRequired(false)
            .addChoices(
                { name: "FFA", value: "ffa" },
                { name: "CTF", value: "ctf" },
                { name: "JB", value: "jb" }
            )
        )
        .addStringOption(option => option
            .setName("creator")
            .setDescription("The creator of the map.")
            .setRequired(false)
            .addChoices(
                { name: "Id Software", value: "Id Software" }
            )
        ),
    execute: async ({client, interaction}) => {
        await interaction.deferReply();
        const maps = client.db.db("quack").collection("maps");

        let query = {};
        let queryDisplay = "";

        const build = (prefix, name, displayName) => {
            const has = interaction.options.getBoolean(name);
            if (has !== null) {
                query[prefix + "." + name] = has;
                queryDisplay += (has ? "" : "No ") + displayName + ", ";
            }
        };

        // Weapons
        build('guns', 'shotgun', 'Shotgun');
        build('guns', 'grenadelauncher', 'Grenade Launcher');
        build('guns', 'rocketlauncher', 'Rocket Launcher');
        build('guns', 'lightninggun', 'Lightning Gun');
        build('guns', 'railgun', 'Rail Gun');
        build('guns', 'plasmagun', 'Plasma Gun');
        build('guns', 'bfg', 'BFG');

        // Armor
        build('armor', 'lightarmor', "Light Armor");
        build('armor', 'heavyarmor', "Heavy Armor");

        // Health
        build('health', 'megahealth', "Mega Health");
        build('health', 'personalmedkit', "Personal Medkit");

        // Hazards
        build('hazards', 'void', "Void Hazard");
        build('hazards', 'pit', "Pit Hazard");
        build('hazards', 'liquid', "Lava/Slime Hazard");

        // Power Ups
        build('powerups', 'quaddamage', "Quad Damage");
        build('powerups', 'regeneration', "Regeneration");
        build('powerups', 'battlesuit', "Battle Suit");
        build('powerups', 'haste', "Haste");
        build('powerups', 'invisibility', "Invisibility");
        build('powerups', 'flight', "Flight");
        build('powerups', 'personalteleporter', "Personal Teleporter");

        // Mode
        const mode = interaction.options.getString('mode');
        if (mode !== null) {
            query['mode'] = mode;
            queryDisplay += mode.toUpperCase() + " Mode, ";
        }

        // Creator
        const creator = interaction.options.getString('creator');
        if (creator !== null) {
            query['creator'] = creator;
            queryDisplay += "Created by " + creator + ", ";
        }

        let results = await maps.find(query).toArray();
        if (results.length > 0) {
            const embed = new EmbedBuilder()
                .setColor(0xFF0000)
                .setTitle('Maps')
                .setDescription(results.map(result => result.name).join('\n'));
            if (Object.keys(query).length > 0) {
                embed.setFooter({ text: queryDisplay.slice(0, -2) });
            }
            return interaction.followUp({embeds: [embed]});
        }
        return interaction.followUp("No results found.");
    }
}