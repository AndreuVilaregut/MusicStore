package com.dam.andreu.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dam.andreu.entitats.Guitarra
import com.dam.andreu.R
import com.dam.andreu.ui.client.DetallsGuitarra

class GuitarraAdapterClient(
    private val context: Context,
    private val guitarres: MutableList<Guitarra>
) : RecyclerView.Adapter<GuitarraAdapterClient.GuitarraViewHolder>() {

    inner class GuitarraViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.guitarra_image)
        val nameTextView: TextView = view.findViewById(R.id.guitarra_name)
        val priceTextView: TextView = view.findViewById(R.id.guitarra_price)

        init {
            view.setOnClickListener {
                val guitarra = guitarres[adapterPosition]
                val intent = Intent(context, DetallsGuitarra::class.java).apply {
                    putExtra("IMAGE_URL", guitarra.imageUrl)
                    putExtra("DESCRIPCIO", guitarra.descripcio)
                    putExtra("MARCA", guitarra.marca)
                    putExtra("MODEL", guitarra.model)
                    putExtra("ANY_FABRICACIO", guitarra.anyFabricacio.toString())
                    putExtra("TIPUS", guitarra.tipus)
                    putExtra("PREU", guitarra.preu.toString())
                    putExtra("COLOR", guitarra.color)
                    putExtra("NUMERO_CORDES", guitarra.numeroCordes.toString())
                }
                context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GuitarraViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.guitarra_client_recycleview, parent, false)
        return GuitarraViewHolder(view)
    }

    override fun onBindViewHolder(holder: GuitarraViewHolder, position: Int) {
        val guitarra = guitarres[position]
        holder.nameTextView.text = guitarra.model
        holder.priceTextView.text = "€${guitarra.preu}"
        Glide.with(context)
            .load(guitarra.imageUrl)
            .centerCrop()
            .placeholder(R.drawable.imatge_corporativa)
            .into(holder.imageView)
    }

    override fun getItemCount(): Int = guitarres.size

    fun getGuitarra(position: Int): Guitarra? {
        return if (position in guitarres.indices) guitarres[position] else null
    }
}
