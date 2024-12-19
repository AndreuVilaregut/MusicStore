package com.dam.andreu.singleton

import android.content.Context
import android.util.Log
import com.dam.andreu.entitats.User
import com.dam.andreu.entitats.Guitarra
import com.dam.andreu.utils.CsvUtils
import com.dam.andreu.utils.CsvUtilsGuitarra

class AppSingleton private constructor() {

    private lateinit var user: User

    // Llista de guitarres gestionada pel singleton
    private val guitarres = mutableListOf<Guitarra>()
    private var lastIdGuitarra = 0

    companion object {
        @Volatile
        private var instance: AppSingleton? = null

        // Obtenim la instància singleton
        fun getInstance(): AppSingleton {
            return instance ?: synchronized(this) {
                instance ?: AppSingleton().also { instance = it }
            }
        }
    }

    // --------------------- Gestió d'Usuaris ---------------------

    fun setUser(user: User) {
        this.user = user
    }

    fun getUser(): User {
        if (!::user.isInitialized) {
            throw UninitializedPropertyAccessException("User no està inicialitzat.")
        }
        return user
    }

    fun loadUsersFromCsv(context: Context): List<User> {
        return CsvUtils.loadUsersFromCsv(context)
    }

    fun saveUsersToCsv(context: Context, users: List<User>) {
        CsvUtils.saveUsersToCsv(context, users)
    }

    // --------------------- Gestió de Guitarres ---------------------

    // Afegir una guitarra al singleton
    fun addGuitarra(guitarra: Guitarra, context: Context) {
        val novaGuitarra = guitarra.copy(id = ++lastIdGuitarra)
        guitarres.add(novaGuitarra)
        saveGuitarresToCsv(context)
    }

    fun editGuitarra(updatedGuitarra: Guitarra, context: Context) {
        val guitarres = guitarres.toMutableList()
        val index = guitarres.indexOfFirst { it.id == updatedGuitarra.id }
        if (index != -1) {
            guitarres[index] = updatedGuitarra
            CsvUtilsGuitarra.saveToCsv(context, guitarres) // Guarda totes les guitarres al CSV
            Log.i("AppSingleton", "Guitarra amb ID ${updatedGuitarra.id} actualitzada correctament.")
        } else {
            Log.e("AppSingleton", "No s'ha trobat la guitarra amb ID ${updatedGuitarra.id}.")
        }
    }


    fun deleteGuitarra(id: Int, context: Context) {
        val guitarraToDelete = guitarres.find { it.id == id }
        guitarraToDelete?.let {
            guitarres.remove(it)
            CsvUtilsGuitarra.eliminarGuitarra(context, id)
        }
    }

    // Obtenir totes les guitarres
    fun getAllGuitarres(): List<Guitarra> = guitarres

    // Carregar les guitarres des del fitxer CSV
    fun loadGuitarresFromCsv(context: Context) {
        guitarres.clear()
        guitarres.addAll(CsvUtilsGuitarra.carregarGuitarres(context))
        lastIdGuitarra = guitarres.maxOfOrNull { it.id } ?: 0
    }

    // Emmagatzemar les guitarres al fitxer CSV
    private fun saveGuitarresToCsv(context: Context) {
        CsvUtilsGuitarra.guardarGuitarres(context, guitarres)
    }
}
