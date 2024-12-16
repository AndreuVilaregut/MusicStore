package com.dam.andreu.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dam.andreu.R
import com.dam.andreu.adapters.GuitarraAdapterClient
import com.dam.andreu.entitats.Carreto
import com.dam.andreu.entitats.Guitarra

class RecycleView : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.recylce_view_client_activity)

        // Llista de guitarres
        val guitarres = mutableListOf<Guitarra>()
        guitarres.add(
            Guitarra(
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
                imageUrl = "https://thumbs.static-thomann.de/thumb/padthumb600x600/pics/bdb/_59/595289/19268613_800.jpg"
            )
        )
        guitarres.add(
            Guitarra(
                id = 2,
                marca = "Gibson",
                model = "Les Paul",
                anyFabricacio = 2019,
                tipus = "Elèctrica",
                preu = 1500.0,
                color = "Vermell",
                numeroCordes = 6,
                unitatsEstoc = 5,
                descripcio = "Guitarra elèctrica Gibson Les Paul",
                imageUrl = "https://thumbs.static-thomann.de/thumb/padthumb600x600/pics/bdb/_46/462509/18203706_800.jpg"
            )
        )
        guitarres.add(
            Guitarra(
                id = 3,
                marca = "Yamaha",
                model = "FG800",
                anyFabricacio = 2018,
                tipus = "Acústica",
                preu = 400.0,
                color = "Marró",
                numeroCordes = 6,
                unitatsEstoc = 15,
                descripcio = "Guitarra acústica Yamaha FG800",
                imageUrl = "https://thumbs.static-thomann.de/thumb/padthumb600x600/pics/bdb/_37/379935/10723417_800.jpg"
            )
        )

        // Configuració del RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.guitarra_recycler_view)
        recyclerView.layoutManager = LinearLayoutManager(this)
        val adapter = GuitarraAdapterClient(this, guitarres)
        recyclerView.adapter = adapter

        // Configuració d'ItemTouchHelper per lliscar cap a l'esquerra
        val itemTouchHelperCallback = object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                // No s'implementa moviment d'elements
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val guitarra = adapter.getGuitarra(position)

                // Afegir al carretó
                guitarra?.let {
                    Carreto.getInstance().afegirGuitarra(it)
                }

                // Mostrar un missatge de confirmació
                Toast.makeText(this@RecycleView, "Afegida al carretó: ${guitarra?.model}", Toast.LENGTH_SHORT).show()

                // Notificar l'adaptador perquè l'element no sigui esborrat
                adapter.notifyItemChanged(position)
            }
        }

        // Vincular ItemTouchHelper amb RecyclerView
        val itemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)
    }
}
