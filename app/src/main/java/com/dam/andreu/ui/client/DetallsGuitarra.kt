package com.dam.andreu.ui.client

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import android.widget.TextView
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import androidx.appcompat.app.AppCompatActivity
import com.dam.andreu.R

class DetallsGuitarra : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalls_guitarra)


        val backButton: ImageButton = findViewById(R.id.backButton)

        val bundle = intent.getBundleExtra("GUITARRA")
        if (bundle != null) {
            val marca = bundle.getString("MARCA") ?: "Desconeguda"
            val model = bundle.getString("MODEL") ?: "Desconegut"
            val anyFabricacio = bundle.getInt("ANY_FABRICACIO", 0)
            val tipus = bundle.getString("TIPUS") ?: "Desconegut"
            val preu = bundle.getDouble("PREU", 0.0)
            val color = bundle.getString("COLOR") ?: "Desconegut"
            val numeroCordes = bundle.getInt("NUMERO_CORDES", 0)
            val descripcio = bundle.getString("DESCRIPCIO") ?: "Sense descripció"
            val imageUrl = bundle.getString("IMAGE_URL") ?: ""

            Log.d("DetallsGuitarra", "Rebent dades: Marca=$marca, Model=$model, Any=$anyFabricacio, Tipus=$tipus, Preu=$preu")

            findViewById<TextView>(R.id.marca).text = "Marca: $marca"
            findViewById<TextView>(R.id.model).text = "Model: $model"
            findViewById<TextView>(R.id.any_fabricacio).text = "Any: $anyFabricacio"
            findViewById<TextView>(R.id.tipus).text = "Tipus: $tipus"
            findViewById<TextView>(R.id.preu).text = "Preu: €$preu"
            findViewById<TextView>(R.id.color).text = "Color: $color"
            findViewById<TextView>(R.id.numero_cordes).text = "Cordes: $numeroCordes"
            findViewById<TextView>(R.id.descripcio).text = "Descripció: $descripcio"

            Glide.with(this)
                .load(imageUrl)
                .centerCrop()
                .into(findViewById<ImageView>(R.id.guitarImageDetail))
        } else {
            Log.e("DetallsGuitarra", "No s'han rebut dades correctes")
            Toast.makeText(this, "Error al carregar les dades de la guitarra.", Toast.LENGTH_SHORT).show()
        }
        backButton.setOnClickListener {
            val intent = Intent(this, RecycleView::class.java)
            startActivity(intent)
        }
    }
}
