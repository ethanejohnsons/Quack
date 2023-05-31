package dev.bluevista.quack.model

/**
 * Represents a map hazard in Quake III Arena.
 */
data class Q3Hazards(
    val void: Boolean,
    val pit: Boolean,
    val liquid: Boolean
)