const { SlashCommandBuilder } = require("@discordjs/builders");
const {EmbedBuilder} = require("discord.js");

module.exports = {
    data: new SlashCommandBuilder()
        .setName("mods")
        .setDescription("Get a list of all the installed mods."),
    execute: async ({client, interaction}) => {
        await interaction.deferReply();
        const maps = client.db.db("quack").collection("mods");
        let results = await maps.find({}).toArray();

        if (results.length > 0) {
            const embed = new EmbedBuilder()
                .setColor(0xFF0000)
                .setTitle('Mods')
                .setDescription(results.map(result => result.name).join(', '))
                .setTimestamp();
            return interaction.followUp({embeds: [embed]});
        }

        return interaction.followUp("No results found.");
    }
}