plugins {
    kotlin("jvm") version "1.8.21"
    application
}

group = "dev.bluevista"
version = "1.0"

repositories {
    mavenCentral()
}

dependencies {
    // Kotlinx Coroutines
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.3.9")

    // Dotenv
    implementation("io.github.cdimascio:dotenv-kotlin:6.4.1")

    // Kord
    implementation("dev.kord:kord-core:0.9.0")
    implementation("dev.kord:kord-common:0.9.0")

    // KMongo
    implementation("org.litote.kmongo:kmongo:4.9.0")
}

kotlin {
    jvmToolchain(17)
}

application {
    mainClass.set("MainKt")
}