package com.dam.andreu.ui.ajustaments

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.dam.andreu.R
import java.util.*

class Traduccio : AppCompatActivity() {

    private lateinit var btnChangeLanguage: Button
    private lateinit var btnBack: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_traduccio)

        btnChangeLanguage = findViewById(R.id.btnChangeLanguage)
        btnBack = findViewById(R.id.btnBack)

        btnChangeLanguage.setOnClickListener {
            val locale = Locale("en")
            Locale.setDefault(locale)

            val config = resources.configuration
            config.setLocale(locale)
            resources.updateConfiguration(config, resources.displayMetrics)

            val prefs = getSharedPreferences("language_prefs", MODE_PRIVATE)
            val editor = prefs.edit()
            editor.putString("language", "en")
            editor.apply()

            Toast.makeText(this, "Idioma canviat a Anglès", Toast.LENGTH_SHORT).show()

            recreate()
        }

        btnBack.setOnClickListener {
            finish()
        }
    }
}
