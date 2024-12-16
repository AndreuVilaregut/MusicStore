package com.dam.andreu.entitats

enum class TipusUsuari {
    TREBALLADOR, CLIENT
}

class User(
    val id: Int,
    val nom: String,
    val contrasenya: String,
    val tipus: TipusUsuari
)
