package com.dam.andreu.entitats

class Carreto private constructor() {

    private val guitarresAlCarreto = mutableListOf<Guitarra>()

    companion object {
        @Volatile
        private var instance: Carreto? = null

        fun getInstance(): Carreto {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = Carreto()
                    }
                }
            }
            return instance!!
        }
    }

    fun afegirGuitarra(guitarra: Guitarra) {
        guitarresAlCarreto.add(guitarra)
    }

    fun getGuitarres(): List<Guitarra> {
        return guitarresAlCarreto
    }
}
