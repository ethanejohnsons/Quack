package dev.bluevista.quack.model

/**
 * Represents a power up drop in Quake III Arena.
 */
data class Q3PowerUps(
    val quaddamage: Boolean,
    val regeneration: Boolean,
    val battlesuit: Boolean,
    val haste: Boolean,
    val invisibility: Boolean,
    val flight: Boolean,
    val personalteleporter: Boolean
)