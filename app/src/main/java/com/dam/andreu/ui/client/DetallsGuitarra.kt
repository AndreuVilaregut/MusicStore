package com.dam.andreu.ui.client

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.dam.andreu.R

class DetallsGuitarra : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detalls_guitarra)

        // Obtenir la imatge des de l'intent
        val imageUrl = intent.getStringExtra("IMAGE_URL")
        val imageView: ImageView = findViewById(R.id.guitarImageDetail)
        Glide.with(this)
            .load(imageUrl)
            .centerCrop()
            .into(imageView)

        // Assignar detalls amb etiquetes
        findViewById<TextView>(R.id.descripcio).text = "Descripció: ${intent.getStringExtra("DESCRIPCIO")}"
        findViewById<TextView>(R.id.marca).text = "Marca: ${intent.getStringExtra("MARCA")}"
        findViewById<TextView>(R.id.model).text = "Model: ${intent.getStringExtra("MODEL")}"
        findViewById<TextView>(R.id.any_fabricacio).text = "Any de fabricació: ${intent.getStringExtra("ANY_FABRICACIO")}"
        findViewById<TextView>(R.id.tipus).text = "Tipus: ${intent.getStringExtra("TIPUS")}"
        findViewById<TextView>(R.id.preu).text = "Preu: €${intent.getStringExtra("PREU")}"
        findViewById<TextView>(R.id.color).text = "Color: ${intent.getStringExtra("COLOR")}"
        findViewById<TextView>(R.id.numero_cordes).text = "Número de cordes: ${intent.getStringExtra("NUMERO_CORDES")}"
    }
}
