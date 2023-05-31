package dev.bluevista.quack.model

/**
 * Represents a map in Quake III Arena.
 */
data class Q3Map(
    val name: String,
    val mode: String,
    val creator: String,
    val notes: String,
    val guns: Q3Guns,
    val armor: Q3Armor,
    val health : Q3Health,
    val hazards : Q3Hazards,
    val powerups : Q3PowerUps
)
