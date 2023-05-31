package dev.bluevista.quack

import dev.kord.core.Kord
import io.github.cdimascio.dotenv.dotenv
import org.litote.kmongo.KMongo

suspend fun main() {
    val kord = Kord(token = dotenv()["TOKEN"])
    val database = KMongo.createClient("mongodb://bluevista.dev:27017").getDatabase("quack")

    registerCommands(kord, database)
    kord.login()
}
