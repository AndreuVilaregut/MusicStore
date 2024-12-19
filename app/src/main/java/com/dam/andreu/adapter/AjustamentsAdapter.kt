package com.dam.andreu.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dam.andreu.R

class AjustamentsAdapter(private val options: List<String>, private val onItemClick: (String) -> Unit) :
    RecyclerView.Adapter<AjustamentsAdapter.SettingsViewHolder>() {

    inner class SettingsViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val optionText: TextView = view.findViewById(R.id.optionText)

        init {
            view.setOnClickListener {
                onItemClick(options[adapterPosition])
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SettingsViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ajustaments, parent, false)
        return SettingsViewHolder(view)
    }

    override fun onBindViewHolder(holder: SettingsViewHolder, position: Int) {
        holder.optionText.text = options[position]
    }

    override fun getItemCount(): Int = options.size
}
