package com.dam.andreu.ui.treballador

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import androidx.appcompat.app.AppCompatActivity
import com.dam.andreu.R
import com.dam.andreu.entitats.Guitarra
import com.dam.andreu.singleton.AppSingleton

class DetallsGuitarra1 : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalls_guitarra1)

        val backButton: ImageButton = findViewById(R.id.backButton)
        val btnGuardar: Button = findViewById(R.id.btn_guardar)

        val bundle = intent.getBundleExtra("GUITARRA")
        if (bundle != null) {
            val id = bundle.getInt("ID")
            val marca = bundle.getString("MARCA") ?: "Desconeguda"
            val model = bundle.getString("MODEL") ?: "Desconegut"
            val anyFabricacio = bundle.getInt("ANY_FABRICACIO", 0)
            val tipus = bundle.getString("TIPUS") ?: "Desconegut"
            val preu = bundle.getDouble("PREU", 0.0)
            val color = bundle.getString("COLOR") ?: "Desconegut"
            val numeroCordes = bundle.getInt("NUMERO_CORDES", 0)
            val descripcio = bundle.getString("DESCRIPCIO") ?: "Sense descripció"
            val unitatsEstoc = bundle.getInt("UNITATS_ESTOC", 0)
            val imageUrl = bundle.getString("IMAGE_URL") ?: ""
            val qrImagePath = bundle.getString("QR_IMAGE_PATH") ?: ""

            findViewById<EditText>(R.id.et_marca).setText(marca)
            findViewById<EditText>(R.id.et_model).setText(model)
            findViewById<EditText>(R.id.et_any_fabricacio).setText(anyFabricacio.toString())
            findViewById<EditText>(R.id.et_tipus).setText(tipus)
            findViewById<EditText>(R.id.et_preu).setText(preu.toString())
            findViewById<EditText>(R.id.et_color).setText(color)
            findViewById<EditText>(R.id.et_numero_cordes).setText(numeroCordes.toString())
            findViewById<EditText>(R.id.et_unitats_estoc).setText(unitatsEstoc.toString())
            findViewById<EditText>(R.id.et_descripcio).setText(descripcio)

            Glide.with(this).load(imageUrl).centerCrop().into(findViewById<ImageView>(R.id.img_preview))
            Glide.with(this).load(qrImagePath).centerCrop().into(findViewById<ImageView>(R.id.qr_preview))

            btnGuardar.setOnClickListener {
                val updatedMarca = findViewById<EditText>(R.id.et_marca).text.toString()
                val updatedModel = findViewById<EditText>(R.id.et_model).text.toString()
                val updatedAnyFabricacio = findViewById<EditText>(R.id.et_any_fabricacio).text.toString().toIntOrNull() ?: 0
                val updatedTipus = findViewById<EditText>(R.id.et_tipus).text.toString()
                val updatedPreu = findViewById<EditText>(R.id.et_preu).text.toString().toDoubleOrNull() ?: 0.0
                val updatedColor = findViewById<EditText>(R.id.et_color).text.toString()
                val updatedNumeroCordes = findViewById<EditText>(R.id.et_numero_cordes).text.toString().toIntOrNull() ?: 0
                val updatedDescripcio = findViewById<EditText>(R.id.et_descripcio).text.toString()
                val updatedUnitatsEstoc = findViewById<EditText>(R.id.et_unitats_estoc).text.toString().toIntOrNull() ?: 0

                val updatedGuitarra = Guitarra(
                    id = id,
                    marca = updatedMarca,
                    model = updatedModel,
                    anyFabricacio = updatedAnyFabricacio,
                    tipus = updatedTipus,
                    preu = updatedPreu,
                    color = updatedColor,
                    numeroCordes = updatedNumeroCordes,
                    descripcio = updatedDescripcio,
                    unitatsEstoc = updatedUnitatsEstoc,
                    imageUrl = imageUrl,
                    qrImagePath = qrImagePath
                )

                AppSingleton.getInstance().editGuitarra(updatedGuitarra, applicationContext)
                Toast.makeText(this, "Guitarra actualitzada correctament!", Toast.LENGTH_SHORT).show()

                finish()
            }

            backButton.setOnClickListener {

                finish()
            }
        }
    }
}
