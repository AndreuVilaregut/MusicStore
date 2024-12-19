package com.dam.andreu.entitats

data class Guitarra(
    val id: Int,
    val marca: String,
    val model: String,
    val anyFabricacio: Int,
    val tipus: String,
    val preu: Double,
    val color: String,
    val numeroCordes: Int,
    val unitatsEstoc: Int,
    val descripcio: String,
    val imageUrl: String,
    var qrImagePath: String?
)

