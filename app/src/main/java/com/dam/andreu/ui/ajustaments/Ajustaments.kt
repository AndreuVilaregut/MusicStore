package com.dam.andreu.ui.ajustaments

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.dam.andreu.R
import com.dam.andreu.adapter.AjustamentsAdapter

class Ajustaments : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var optionsAdapter: AjustamentsAdapter
    private val options = listOf("About Us", "Perfil", "Traducció")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.ajustaments)

        recyclerView = findViewById(R.id.optionText)
        optionsAdapter = AjustamentsAdapter(options) { option ->
            when (option) {
                "About Us" -> {
                    showAboutUs()
                }
                "Perfil" -> {
                    //showPerfil()
                }
                "Traducció" -> {
                    showLanguageSelection()
                }
            }
        }
        recyclerView.adapter = optionsAdapter
    }

    private fun showAboutUs() {
        val aboutUsIntent = Intent(this, SobreApp::class.java)
        startActivity(aboutUsIntent)
    }

    private fun showLanguageSelection() {
        val languageIntent = Intent(this, Traduccio::class.java)
        startActivity(languageIntent)
    }
}
