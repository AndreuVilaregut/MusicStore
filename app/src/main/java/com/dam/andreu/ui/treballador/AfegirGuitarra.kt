package com.dam.andreu.ui.treballador

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dam.andreu.R
import com.dam.andreu.entitats.Guitarra
import com.dam.andreu.utils.CsvUtilsGuitarra
import com.dam.andreu.utils.QRGenerator

class AfegirGuitarra : AppCompatActivity() {

    private lateinit var etMarca: EditText
    private lateinit var etModel: EditText
    private lateinit var etAnyFabricacio: EditText
    private lateinit var etTipus: EditText
    private lateinit var etPreu: EditText
    private lateinit var etColor: EditText
    private lateinit var etNumeroCordes: EditText
    private lateinit var etUnitatsEstoc: EditText
    private lateinit var etDescripcio: EditText
    private lateinit var etImatgeUrl: EditText
    private lateinit var btnAfegirGuitarra: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_afegir_guitarra)

        // Inicialitzar els components de la UI
        etMarca = findViewById(R.id.et_marca)
        etModel = findViewById(R.id.et_model)
        etAnyFabricacio = findViewById(R.id.et_any_fabricacio)
        etTipus = findViewById(R.id.et_tipus)
        etPreu = findViewById(R.id.et_preu)
        etColor = findViewById(R.id.et_color)
        etNumeroCordes = findViewById(R.id.et_numero_cordes)
        etUnitatsEstoc = findViewById(R.id.et_unitats_estoc)
        etDescripcio = findViewById(R.id.et_descripcio)
        etImatgeUrl = findViewById(R.id.et_imatge)
        btnAfegirGuitarra = findViewById(R.id.btn_guardar)

        btnAfegirGuitarra.setOnClickListener {
            val marca = etMarca.text.toString()
            val model = etModel.text.toString()
            val anyFabricacio = etAnyFabricacio.text.toString().toIntOrNull() ?: 0
            val tipus = etTipus.text.toString()
            val preu = etPreu.text.toString().toDoubleOrNull() ?: 0.0
            val color = etColor.text.toString()
            val numeroCordes = etNumeroCordes.text.toString().toIntOrNull() ?: 0
            val unitatsEstoc = etUnitatsEstoc.text.toString().toIntOrNull() ?: 0
            val descripcio = etDescripcio.text.toString()
            val imatgeUrl = etImatgeUrl.text.toString()

            val id = generarIDUnica()

            val qrImagePath = QRGenerator.desarQRCode(this, "Guitarra ID: $id")

            val novaGuitarra = Guitarra(id, marca, model, anyFabricacio, tipus, preu, color, numeroCordes, unitatsEstoc, descripcio, imatgeUrl, qrImagePath)

            CsvUtilsGuitarra.guardarGuitarres(this, listOf(novaGuitarra))

            Toast.makeText(this, "Guitarra afegida correctament!", Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private fun generarIDUnica(): Int {
        val guitarresExistents = CsvUtilsGuitarra.carregarGuitarres(this)
        return if (guitarresExistents.isEmpty()) {
            1
        } else {
            val idsExistents = guitarresExistents.map { it.id }
            var idNou = 1
            while (idsExistents.contains(idNou)) {
                idNou++
            }
            idNou
        }
    }
}
