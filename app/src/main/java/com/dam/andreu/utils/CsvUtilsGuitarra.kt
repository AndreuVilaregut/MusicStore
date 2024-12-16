package com.dam.andreu.utils

import android.content.Context
import android.util.Log
import com.dam.andreu.entitats.Guitarra
import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileReader
import java.io.FileWriter

object CsvUtilsGuitarra {

    private const val CSV_FILE_NAME = "guitarres.csv"

    fun guardarGuitarres(context: Context, guitarres: List<Guitarra>) {
        val file = File(context.filesDir, CSV_FILE_NAME)

        try {
            if (!file.exists()) {
                file.createNewFile()
                Log.i("CsvUtilsGuitarra", "CSV file created at: ${file.absolutePath}")
            }

            BufferedWriter(FileWriter(file)).use { writer ->
                guitarres.forEach { guitarra ->
                    writer.write(formatGuitarraToCsv(guitarra))
                    writer.newLine()
                }
                Log.i("CsvUtilsGuitarra", "Saved ${guitarres.size} guitars to CSV")
            }
        } catch (e: Exception) {
            Log.e("CsvUtilsGuitarra", "Error saving to CSV: ${e.message}")
        }
    }

    fun carregarGuitarres(context: Context): List<Guitarra> {
        val guitarres = mutableListOf<Guitarra>()
        val file = File(context.filesDir, CSV_FILE_NAME)

        if (!file.exists()) {
            Log.i("CsvUtilsGuitarra", "CSV file does not exist, returning empty list")
            return guitarres
        }

        try {
            BufferedReader(FileReader(file)).use { reader ->
                reader.forEachLine { line ->
                    parseCsvToGuitarra(line)?.let { guitarra ->
                        guitarres.add(guitarra)
                    }
                }
            }
            Log.i("CsvUtilsGuitarra", "Loaded ${guitarres.size} guitars from CSV")
        } catch (e: Exception) {
            Log.e("CsvUtilsGuitarra", "Error loading from CSV: ${e.message}")
        }
        return guitarres
    }

    private fun formatGuitarraToCsv(guitarra: Guitarra): String {
        return "${guitarra.id}," +
                "${guitarra.marca}," +
                "${guitarra.model}," +
                "${guitarra.anyFabricacio}," +
                "${guitarra.tipus}," +
                "${guitarra.preu}," +
                "${guitarra.color}," +
                "${guitarra.numeroCordes}," +
                "${guitarra.unitatsEstoc}," +
                "${guitarra.descripcio}" +
                "${guitarra.imageUrl}"

    }

    private fun parseCsvToGuitarra(line: String): Guitarra? {
        val fields = line.split(",")
        return if (fields.size == 11) { // Ara estem esperant 11 camps, no 10
            try {
                Guitarra(
                    id = fields[0].toInt(),
                    marca = fields[1],
                    model = fields[2],
                    anyFabricacio = fields[3].toInt(),
                    tipus = fields[4],
                    preu = fields[5].toDouble(),
                    color = fields[6],
                    numeroCordes = fields[7].toInt(),
                    unitatsEstoc = fields[8].toInt(),
                    descripcio = fields[9],
                    imageUrl = fields[10]
                )
            } catch (e: Exception) {
                Log.e("CsvUtilsGuitarra", "Error parsing line: $line. Error: ${e.message}")
                null
            }
        } else {
            Log.e("CsvUtilsGuitarra", "Invalid CSV line: $line")
            null
        }
    }
}
