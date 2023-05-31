package dev.bluevista.quack.model

/**
 * Represents a health drop in Quake III Arena.
 */
data class Q3Health(
    val megahealth: Boolean,
    val personalmedkit: Boolean
)