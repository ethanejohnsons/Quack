const {REST} = require("@discordjs/rest");
const { Client, GatewayIntentBits, Collection } = require('discord.js');
const { Routes } = require("discord-api-types/v9");

const {MongoClient} = require('mongodb');

const fs = require("node:fs");
const path = require("node:path");

const { token, clientId } = require('../config.json');

// Create client
const client = new Client({
    intents: [
        GatewayIntentBits.Guilds,
        GatewayIntentBits.GuildMessages,
        GatewayIntentBits.GuildVoiceStates
    ]
});

// Set up commands
const commands = [];
client.commands = new Collection();
const commandsPath = path.join(__dirname, "commands");
const commandFiles = fs.readdirSync(commandsPath).filter(file => file.endsWith(".js"));

for (const file of commandFiles) {
    const filePath = path.join(commandsPath, file);
    const command = require(filePath);
    client.commands.set(command.data.name, command);
    commands.push(command.data.toJSON());
}

// Register commands
client.on("ready", () => {
    const rest = new REST({version: "9"}).setToken(token);
    rest.put(Routes.applicationCommands(clientId), {
        body: commands
    }).catch(console.error)
});

// Handle command execution
client.on("interactionCreate", async interaction => {
    if (!interaction.isCommand()) return;

    const command = client.commands.get(interaction.commandName);
    if(!command) return;

    try {
        await command.execute({client, interaction});
    } catch (err) {
        console.error(err);
        await interaction.reply("An error occurred while executing that command.");
    }
});

client.login(token);

const uri = "mongodb://192.168.122.66/quack?retryWrites=true&w=majority";
client.db = new MongoClient(uri)
client.db.connect();