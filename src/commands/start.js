const { SlashCommandBuilder } = require("@discordjs/builders");

module.exports = {
    data: new SlashCommandBuilder()
        .setName("start")
        .setDescription("Start a new server.")
        .addStringOption(option => option
            .setName("mod")
            .setDescription("Choose which mod to use.")
            .setRequired(false)
        ),
    execute: async ({client, interaction}) => {
        if (client.quake) {
            return interaction.reply("There is already a server running, man!");
        }

        await interaction.deferReply();
        let mod = interaction.options.getString("mod");

        return interaction.followUp(`todo`)
    }
}