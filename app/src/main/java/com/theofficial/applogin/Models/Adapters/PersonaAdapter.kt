package com.theofficial.applogin.Models.Adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.theofficial.applogin.R
import com.theofficial.applogin.Room.Entity.Persona

class PersonaAdapter(private val clickListener: (Persona) -> Unit) : RecyclerView.Adapter<PersonaAdapter.PersonaViewHolder>() {

    private var personas = emptyList<Persona>()

    class PersonaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvNombre: TextView = itemView.findViewById(R.id.tvNombre)
        val tvApellido: TextView = itemView.findViewById(R.id.tvApellido)
        val tvCelular: TextView = itemView.findViewById(R.id.tvCelular)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonaViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.lista_item_persona, parent, false)
        return PersonaViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonaViewHolder, position: Int) {
        val personaActual = personas[position]

        holder.tvNombre.text = personaActual.nombres
        holder.tvApellido.text = personaActual.apellidos
        holder.tvCelular.text = personaActual.celular
        holder.itemView.setOnClickListener {
            clickListener(personaActual)
        }


    }

    override fun getItemCount(): Int {
        return personas.size
    }

    fun setPersonas(personas: List<Persona>) {
        this.personas = personas
        notifyDataSetChanged()
    }
}