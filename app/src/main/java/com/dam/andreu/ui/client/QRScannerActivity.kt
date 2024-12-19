package com.dam.andreu.ui.client

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import android.util.Log
import com.dam.andreu.utils.CsvUtilsGuitarra
import com.google.zxing.integration.android.IntentIntegrator

class QRScannerActivity : Activity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val integrator = IntentIntegrator(this)
        integrator.setPrompt("Escanejant QR...")
        integrator.setBeepEnabled(true)
        integrator.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result != null && result.contents != null) {
            val codiQR = result.contents
            Log.d("QRScannerActivity", "Contingut del codi QR: $codiQR")

            val guitarraId = codiQR.replace(Regex("[^0-9]"), "").toIntOrNull()
            Log.d("QRScannerActivity", "Codi QR convertit a ID: $guitarraId")

            if (guitarraId != null) {
                val guitarres = CsvUtilsGuitarra.carregarGuitarres(this)
                Log.d("QRScannerActivity", "Guitarres carregades des del CSV: ${guitarres.size}")

                val guitarra = guitarres.firstOrNull { it.id == guitarraId }
                if (guitarra != null) {
                    Log.d("QRScannerActivity", "Guitarra trobada: ${guitarra.marca} ${guitarra.model}")

                    Log.d("QRScannerActivity", "Passant dades al bundle: Marca=${guitarra.marca}, Model=${guitarra.model}, Preu=${guitarra.preu}")

                    val intent = Intent(this, DetallsGuitarra::class.java)
                    val bundle = Bundle().apply {
                        putString("MARCA", guitarra.marca ?: "")
                        putString("MODEL", guitarra.model ?: "")
                        putInt("ANY_FABRICACIO", guitarra.anyFabricacio)
                        putString("TIPUS", guitarra.tipus ?: "")
                        putDouble("PREU", guitarra.preu)
                        putString("COLOR", guitarra.color ?: "")
                        putInt("NUMERO_CORDES", guitarra.numeroCordes)
                        putString("DESCRIPCIO", guitarra.descripcio ?: "")
                        putString("IMAGE_URL", guitarra.imageUrl ?: "")
                    }
                    intent.putExtra("GUITARRA", bundle)
                    startActivity(intent)

                } else {
                    Log.e("QRScannerActivity", "Guitarra no trobada per l'ID: $guitarraId")
                    Toast.makeText(this, "Guitarra no trobada", Toast.LENGTH_SHORT).show()
                }
            } else {
                Log.e("QRScannerActivity", "Codi QR no vàlid: $codiQR")
                Toast.makeText(this, "Codi QR no vàlid", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
