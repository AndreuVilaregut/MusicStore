package com.dam.andreu.ui.treballador

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatImageButton
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dam.andreu.R
import com.dam.andreu.adapters.GuitarraAdapterTreballador
import com.dam.andreu.entitats.Guitarra
import com.dam.andreu.singleton.AppSingleton
import com.dam.andreu.ui.client.QRScannerActivity
import java.io.File

class RecycleView1 : AppCompatActivity() {

    private lateinit var guitarres: MutableList<Guitarra>
    private lateinit var adapter: GuitarraAdapterTreballador

    @SuppressLint("WrongViewCast", "MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.recylce_view_treballador_activity)

        // Inicialitza el RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.guitarra_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Inicialitza el botó de scanner QR
        val qrButton = findViewById<AppCompatImageButton>(R.id.qrScannerButtonOne)
        qrButton.setOnClickListener {
            val intent = Intent(this, QRScannerActivity1::class.java)
            startActivity(intent)
        }

        val afegirGuitarraButton = findViewById<ImageButton>(R.id.afegirGuitarra)
        afegirGuitarraButton.setOnClickListener {
            val intent = Intent(this, AfegirGuitarra::class.java)
            startActivity(intent)
        }

        loadGuitarres()
    }

    override fun onResume() {
        super.onResume()
        // Carregar les guitarres cada cop que tornis a aquesta activitat
        loadGuitarres()
    }

    private fun loadGuitarres() {
        // Obtenir les guitarres des del singleton
        val singleton = AppSingleton.getInstance()
        singleton.loadGuitarresFromCsv(this)
        guitarres = singleton.getAllGuitarres().toMutableList()

        // Configurar l'adaptador per a mostrar les guitarres
        adapter = GuitarraAdapterTreballador(this, guitarres)
        val recyclerView = findViewById<RecyclerView>(R.id.guitarra_recycler_view)
        recyclerView.adapter = adapter

        // Configurar l'ItemTouchHelper per esborrar guitarres amb swipe
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val guitarra = guitarres[position]

                singleton.deleteGuitarra(guitarra.id, this@RecycleView1)

                guitarra.qrImagePath?.let { qrPath ->
                    val qrFile = File(qrPath)
                    if (qrFile.exists() && qrFile.delete()) {
                        Toast.makeText(this@RecycleView1, "Codi QR esborrat correctament", Toast.LENGTH_SHORT).show()
                    }
                }

                guitarres.removeAt(position)
                adapter.notifyItemRemoved(position)

                Toast.makeText(this@RecycleView1, "Guitarra borrada correctament: UID ${guitarra.id}", Toast.LENGTH_SHORT).show()
            }
        }

        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}
