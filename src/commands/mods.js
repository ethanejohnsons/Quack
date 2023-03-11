const { SlashCommandBuilder } = require("@discordjs/builders");

module.exports = {
    data: new SlashCommandBuilder()
        .setName("mods")
        .setDescription("Get a list of all the installed mods."),
    execute: async ({client, interaction}) => {
        return interaction.reply("idk");
    }
}