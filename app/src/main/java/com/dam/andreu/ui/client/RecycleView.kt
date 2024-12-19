package com.dam.andreu.ui.client

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dam.andreu.R
import com.dam.andreu.adapters.GuitarraAdapterClient
import com.dam.andreu.ui.ajustaments.Ajustaments
import com.dam.andreu.utils.CsvUtilsGuitarra

class RecycleView : AppCompatActivity() {

    @SuppressLint("WrongViewCast")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.recylce_view_client_activity)

        val guitarres = CsvUtilsGuitarra.carregarGuitarres(this).toMutableList()

        val recyclerView = findViewById<RecyclerView>(R.id.guitarra_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = GuitarraAdapterClient(this, guitarres)
        recyclerView.adapter = adapter

        val qrButton = findViewById<AppCompatImageButton>(R.id.qrScannerButton)
        qrButton.setOnClickListener {
            val intent = Intent(this, QRScannerActivity::class.java)
            startActivity(intent)
        }

        val sobre = findViewById<AppCompatImageButton>(R.id.conte)
        sobre.setOnClickListener {
            val intent = Intent(this, Ajustaments::class.java)
            startActivity(intent)
        }
    }
}
