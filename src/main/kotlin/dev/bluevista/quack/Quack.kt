package dev.bluevista.quack

import com.mongodb.client.MongoDatabase
import dev.bluevista.quack.commands.registerMapCommand
import dev.bluevista.quack.commands.registerMapsCommand
import dev.bluevista.quack.commands.registerModCommand
import dev.bluevista.quack.commands.registerModsCommand
import dev.kord.core.Kord
import io.github.cdimascio.dotenv.dotenv
import org.litote.kmongo.KMongo

val env = dotenv()
val database: MongoDatabase = KMongo.createClient("mongodb://${env["DB_USER"]}:${env["DB_PASS"]}@${env["DB_ADDRESS"]}/${env["DB_NAME"]}").getDatabase(env["DB_NAME"])

suspend fun main() {
    val kord = Kord(token = env["TOKEN"])
    registerMapCommand(kord)
    registerModCommand(kord)
    registerMapsCommand(kord)
    registerModsCommand(kord)
    kord.login()
}
