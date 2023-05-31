package dev.bluevista.quack.model

/**
 * Represents a mod for Quake III Arena.
 */
data class Q3Mod(
    val name: String,
    val version: String,
    val creator: String,
    val releaseDate: String,
    val description: String,
    val features: List<String>,
    val readme: String,
    val pk3Size: String,
    val enabled: Boolean
)