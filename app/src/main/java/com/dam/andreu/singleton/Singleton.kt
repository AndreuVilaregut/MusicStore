package com.dam.andreu.singleton

import android.content.Context
import com.dam.andreu.entitats.User
import com.dam.andreu.entitats.Guitarra
import com.dam.andreu.entitats.Carreto
import com.dam.andreu.utils.CsvUtils
import com.dam.andreu.utils.CsvUtilsGuitarra

class AppSingleton private constructor() {

    private lateinit var user: User

    private val guitarres = mutableListOf<Guitarra>()
    private var lastIdGuitarra = 0

    private val carreto = Carreto.getInstance()

    companion object {
        @Volatile
        private var instance: AppSingleton? = null

        fun getInstance(): AppSingleton {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = AppSingleton()
                    }
                }
            }
            return instance!!
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

    fun addGuitarra(guitarra: Guitarra, context: Context) {
        val novaGuitarra = guitarra.copy(id = ++lastIdGuitarra)
        guitarres.add(novaGuitarra)
        saveGuitarresToCsv(context)
    }

    fun editGuitarra(guitarra: Guitarra, context: Context) {
        val index = guitarres.indexOfFirst { it.id == guitarra.id }
        if (index != -1) {
            guitarres[index] = guitarra
            saveGuitarresToCsv(context)
        }
    }

    fun deleteGuitarra(id: Int, context: Context) {
        guitarres.removeIf { it.id == id }
        saveGuitarresToCsv(context)
    }

    fun getAllGuitarres(): List<Guitarra> = guitarres

    fun loadGuitarresFromCsv(context: Context) {
        guitarres.clear()
        guitarres.addAll(CsvUtilsGuitarra.carregarGuitarres(context))
        lastIdGuitarra = guitarres.maxOfOrNull { it.id } ?: 0
    }

    private fun saveGuitarresToCsv(context: Context) {
        CsvUtilsGuitarra.guardarGuitarres(context, guitarres)
    }

    // --------------------- Gestió del Carreto ---------------------

    fun afegirAlCarreto(guitarra: Guitarra) {
        carreto.afegirGuitarra(guitarra)
    }

    fun obtenirCarreto(): List<Guitarra> {
        return carreto.getGuitarres()
    }

}
