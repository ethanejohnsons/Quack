package dev.bluevista.quack.model

/**
 * Represents a gun drop in Quake III Arena.
 */
data class Q3Guns(
    val shotgun: Boolean,
    val grenadelauncher: Boolean,
    val rocketlauncher: Boolean,
    val lightninggun: Boolean,
    val railgun: Boolean,
    val plasmagun: Boolean,
    val bfg: Boolean
)
