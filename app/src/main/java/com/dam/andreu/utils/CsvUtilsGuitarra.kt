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

    fun guardarGuitarres(context: Context, guitarres: List<Guitarra> = emptyList()) {
        val file = File(context.filesDir, CSV_FILE_NAME)
        Log.i("CsvUtilsGuitarra", "CSV file path: ${file.absolutePath}")

        try {
            if (!file.exists()) {
                try {
                    val created = file.createNewFile()
                    if (created) {
                        Log.i("CsvUtilsGuitarra", "CSV file created at: ${file.absolutePath}")
                    } else {
                        Log.w("CsvUtilsGuitarra", "CSV file already exists.")
                    }
                } catch (e: Exception) {
                    Log.e("CsvUtilsGuitarra", "Error creating CSV file: ${e.message}")
                    return
                }
            } else {
                Log.i("CsvUtilsGuitarra", "CSV file exists at: ${file.absolutePath}")
            }

            val existingGuitarres = carregarGuitarres(context).toMutableList()

            if (existingGuitarres.isEmpty()) {
                val guitarresDeProva = crearGuitarresDeProva(context)
                existingGuitarres.addAll(guitarresDeProva)
            }

            existingGuitarres.addAll(guitarres)

            BufferedWriter(FileWriter(file)).use { writer ->
                existingGuitarres.forEach { guitarra ->
                    writer.write(formatGuitarraToCsv(guitarra))
                    writer.newLine()
                }
                Log.i("CsvUtilsGuitarra", "Saved ${existingGuitarres.size} guitars to CSV")
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

    private fun crearGuitarresDeProva(context: Context): List<Guitarra> {
        val guitarra1 = Guitarra(
            id = 1,
            marca = "Fender",
            model = "Stratocaster",
            anyFabricacio = 2020,
            tipus = "Elèctrica",
            preu = 1200.0,
            color = "Blau",
            numeroCordes = 6,
            unitatsEstoc = 10,
            descripcio = "Guitarra elèctrica Fender Stratocaster",
            imageUrl = "https://thumbs.static-thomann.de/thumb/padthumb600x600/pics/bdb/_59/595251/19374051_800.jpg",
            qrImagePath = QRGenerator.desarQRCode(context, "Guitarra ID: 1")
        )

        val guitarra2 = Guitarra(
            id = 2,
            marca = "Gibson",
            model = "Les Paul",
            anyFabricacio = 2019,
            tipus = "Elèctrica",
            preu = 1500.0,
            color = "Negra",
            numeroCordes = 6,
            unitatsEstoc = 5,
            descripcio = "Guitarra elèctrica Gibson Les Paul",
            imageUrl = "https://thumbs.static-thomann.de/thumb/padthumb600x600/pics/bdb/_59/595251/19374051_800.jpg",
            qrImagePath = QRGenerator.desarQRCode(context, "Guitarra ID: 2")
        )

        val guitarra3 = Guitarra(
            id = 3,
            marca = "Ibanez",
            model = "RG",
            anyFabricacio = 2021,
            tipus = "Elèctrica",
            preu = 900.0,
            color = "Vermell",
            numeroCordes = 6,
            unitatsEstoc = 7,
            descripcio = "Guitarra elèctrica Ibanez RG",
            imageUrl = "https://thumbs.static-thomann.de/thumb/padthumb600x600/pics/bdb/_59/595251/19374051_800.jpg",
            qrImagePath = QRGenerator.desarQRCode(context, "Guitarra ID: 3")
        )

        return listOf(guitarra1, guitarra2, guitarra3)
    }

    fun editarGuitarra(context: Context, guitarraModificada: Guitarra) {
        val file = File(context.filesDir, CSV_FILE_NAME)
        val guitarres = carregarGuitarres(context).toMutableList()

        val index = guitarres.indexOfFirst { it.id == guitarraModificada.id }
        if (index != -1) {
            guitarres[index] = guitarraModificada
            Log.i("CsvUtilsGuitarra", "Guitar ID: ${guitarraModificada.id} updated.")
        } else {
            Log.w("CsvUtilsGuitarra", "Guitar ID: ${guitarraModificada.id} not found.")
        }

        try {
            BufferedWriter(FileWriter(file)).use { writer ->
                guitarres.forEach { guitarra ->
                    writer.write(formatGuitarraToCsv(guitarra))
                    writer.newLine()
                }
                Log.i("CsvUtilsGuitarra", "Saved updated guitars to CSV")
            }
        } catch (e: Exception) {
            Log.e("CsvUtilsGuitarra", "Error saving updated guitars to CSV: ${e.message}")
        }
    }

    fun eliminarGuitarra(context: Context, guitarraId: Int) {
        val file = File(context.filesDir, CSV_FILE_NAME)
        val guitarres = carregarGuitarres(context).toMutableList()

        val guitarraEliminada = guitarres.removeIf { it.id == guitarraId }

        if (guitarraEliminada) {
            Log.i("CsvUtilsGuitarra", "Guitar ID: $guitarraId removed.")
        } else {
            Log.w("CsvUtilsGuitarra", "Guitar ID: $guitarraId not found.")
        }

        try {
            BufferedWriter(FileWriter(file)).use { writer ->
                guitarres.forEach { guitarra ->
                    writer.write(formatGuitarraToCsv(guitarra))
                    writer.newLine()
                }
                Log.i("CsvUtilsGuitarra", "Saved updated list of guitars to CSV after deletion")
            }
        } catch (e: Exception) {
            Log.e("CsvUtilsGuitarra", "Error saving updated list of guitars after deletion to CSV: ${e.message}")
        }
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
                "${guitarra.descripcio}," +
                "${guitarra.imageUrl}," +
                "${guitarra.qrImagePath}"
    }

    fun saveToCsv(context: Context, guitarres: List<Guitarra>) {
        val file = File(context.filesDir, "guitarres.csv")
        file.bufferedWriter().use { writer ->
            for (guitarra in guitarres) {
                writer.write(
                    "${guitarra.id},${guitarra.marca},${guitarra.model},${guitarra.anyFabricacio},${guitarra.tipus},${guitarra.preu},${guitarra.color},${guitarra.numeroCordes},${guitarra.unitatsEstoc},${guitarra.descripcio},${guitarra.imageUrl},${guitarra.qrImagePath}\n"
                )
            }
        }
    }
    fun parseCsvToGuitarra(line: String): Guitarra? {
        val parts = line.split(",")

        if (parts.size >= 12) {
            try {
                return Guitarra(
                    id = parts[0].toInt(),
                    marca = parts[1],
                    model = parts[2],
                    anyFabricacio = parts[3].toInt(),
                    tipus = parts[4],
                    preu = parts[5].toDouble(),
                    color = parts[6],
                    numeroCordes = parts[7].toInt(),
                    unitatsEstoc = parts[8].toInt(),
                    descripcio = parts[9],
                    imageUrl = parts[10],
                    qrImagePath = parts[11]
                )
            } catch (e: Exception) {
                Log.e("CsvUtilsGuitarra", "Error parsing line: $line", e)
            }
        }
        return null
    }

}
