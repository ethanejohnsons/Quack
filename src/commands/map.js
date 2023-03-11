const { SlashCommandBuilder } = require("@discordjs/builders");

module.exports = {
    data: new SlashCommandBuilder()
        .setName("map")
        .setDescription("Change maps.")
        .addStringOption(option => option
            .setName("map")
            .setDescription("Choose which map to change to.")
            .setRequired(false)
        ),
    execute: async ({client, interaction}) => {
        if (!client.quake) {
            return interaction.reply("The server isn't running, man!");
        }

        await interaction.deferReply();
        let map = interaction.options.getString("map");

        return interaction.followUp(`todo`)
    }
}