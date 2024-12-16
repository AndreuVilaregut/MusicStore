package com.dam.andreu.utils

import android.content.Context
import android.util.Log
import com.dam.andreu.entitats.User
import com.dam.andreu.entitats.TipusUsuari
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileReader
import java.io.FileWriter

object CsvUtils {

    private const val CSV_FILE_NAME = "users.csv"

    fun saveUsersToCsv(context: Context, users: List<User>) {
        val file = File(context.filesDir, CSV_FILE_NAME)

        try {
            if (!file.exists()) {
                try {
                    file.createNewFile()
                    Log.i("CsvUtils", "CSV file created at: ${file.absolutePath}")
                } catch (e: Exception) {
                    Log.e("CsvUtils", "Error creating CSV file: ${e.message}")
                    return
                }
            } else {
                Log.i("CsvUtils", "CSV file exists at: ${file.absolutePath}")
            }

            val existingUsers = loadUsersFromCsv(context).toMutableList()
            existingUsers.addAll(users)

            BufferedWriter(FileWriter(file)).use { writer ->
                existingUsers.forEach { user ->
                    writer.write(formatUserToCsv(user))
                    writer.newLine()
                }
                Log.i("CsvUtils", "Saved ${existingUsers.size} users to CSV")
            }
        } catch (e: Exception) {
            Log.e("CsvUtils", "Error saving to CSV: ${e.message}")
        }
    }

    fun loadUsersFromCsv(context: Context): List<User> {
        val users = mutableListOf<User>()
        val file = File(context.filesDir, CSV_FILE_NAME)

        if (!file.exists()) {
            Log.i("CsvUtils", "CSV file does not exist, returning empty list")
            return users
        }

        try {
            BufferedReader(FileReader(file)).use { reader ->
                reader.forEachLine { line ->
                    parseCsvToUser(line)?.let { user ->
                        users.add(user)
                    }
                }
            }
            Log.i("CsvUtils", "Loaded ${users.size} users from CSV")
        } catch (e: Exception) {
            Log.e("CsvUtils", "Error loading from CSV: ${e.message}")
        }
        return users
    }

    private fun formatUserToCsv(user: User): String {
        return "${user.id}," +
                "${user.nom}," +
                "${user.contrasenya}," +
                "${user.tipus}"
    }

    private fun parseCsvToUser(line: String): User? {
        val fields = line.split(",")
        return if (fields.size == 4) {
            try {
                User(
                    id = fields[0].toInt(),
                    nom = fields[1],
                    contrasenya = fields[2],
                    tipus = TipusUsuari.valueOf(fields[3])
                )
            } catch (e: Exception) {
                Log.e("CsvUtils", "Error parsing line: $line. Error: ${e.message}")
                null
            }
        } else {
            Log.e("CsvUtils", "Invalid CSV line: $line")
            null
        }
    }
}
