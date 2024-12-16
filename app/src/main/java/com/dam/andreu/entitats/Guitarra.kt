package com.dam.andreu.entitats

data class Guitarra(
    val id: Int, // Identificador únic
    var marca: String, // Marca de la guitarra
    var model: String, // Model de la guitarra
    var anyFabricacio: Int, // Any de fabricació
    var tipus: String, // Tipus: "Acústica", "Elèctrica", etc.
    var preu: Double, // Preu per unitat
    var color: String, // Color de la guitarra
    var numeroCordes: Int, // Nombre de cordes
    var unitatsEstoc: Int, // Unitats disponibles en estoc
    var descripcio: String,
    var imageUrl: String
)
