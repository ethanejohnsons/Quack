const { SlashCommandBuilder } = require("@discordjs/builders");

module.exports = {
    data: new SlashCommandBuilder()
        .setName("maps")
        .setDescription("Get a list of all the installed maps."),
    execute: async ({client, interaction}) => {
        return interaction.reply("idk");
    }
}