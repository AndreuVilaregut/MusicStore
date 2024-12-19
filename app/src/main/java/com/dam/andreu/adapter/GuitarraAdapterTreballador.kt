package com.dam.andreu.adapters

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.dam.andreu.entitats.Guitarra
import com.dam.andreu.R
import com.dam.andreu.ui.treballador.DetallsGuitarra1

class GuitarraAdapterTreballador(
    private val context: Context,
    public val guitarres: MutableList<Guitarra>
) : RecyclerView.Adapter<GuitarraAdapterTreballador.GuitarraViewHolder>() {

    inner class GuitarraViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val imageView: ImageView = view.findViewById(R.id.guitarra_image)
        val nameTextView: TextView = view.findViewById(R.id.guitarra_name)
        val priceTextView: TextView = view.findViewById(R.id.guitarra_price)

        init {
            view.setOnClickListener {
                val guitarra = guitarres[adapterPosition]

                val bundle = Bundle().apply {
                    putInt("ID", guitarra.id)
                    putString("MARCA", guitarra.marca ?: "")
                    putString("MODEL", guitarra.model ?: "")
                    putInt("ANY_FABRICACIO", guitarra.anyFabricacio)
                    putString("TIPUS", guitarra.tipus ?: "")
                    putDouble("PREU", guitarra.preu)
                    putString("COLOR", guitarra.color ?: "")
                    putInt("NUMERO_CORDES", guitarra.numeroCordes)
                    putString("DESCRIPCIO", guitarra.descripcio ?: "")
                    putInt("UNITATS_ESTOC", guitarra.unitatsEstoc)
                    putString("IMAGE_URL", guitarra.imageUrl ?: "")
                    putString("QR_IMAGE_PATH", guitarra.qrImagePath ?: "")
                }

                val intent = Intent(context, DetallsGuitarra1::class.java).apply {
                    putExtra("GUITARRA", bundle)
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
