package com.theofficial.applogin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.theofficial.applogin.Firebase.PersonaRepository
import com.theofficial.applogin.Models.Adapters.PersonaAdapter
import com.theofficial.applogin.Models.ViewModels.MainViewModel
import com.theofficial.applogin.Room.Database.DbAppIni
import com.theofficial.applogin.Room.Entity.Persona

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val añadirPersona = findViewById<FloatingActionButton>(R.id.añadirPersona)
        val cerrarSesion = findViewById<FloatingActionButton>(R.id.cerrarSesion)

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("personas")

        val adapter = PersonaAdapter { persona ->
            Log.d("prueba", persona.id.toString())
            cargarDatosEnFormulario(persona,"editar")
        }

        viewModel = ViewModelProvider(this).get(MainViewModel::class.java)

        viewModel.personas.observe(this, Observer { personas ->
            // Actualiza la lista de personas en el adapter
            adapter.setPersonas(personas)
        })



        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        cerrarSesion.setOnClickListener {
            abrirActivity(LoginActivity::class.java,"")
        }

        añadirPersona.setOnClickListener {
            abrirActivity(RegistroPersonaActivity::class.java,"agregar")
        }

        getAllPersonas().observe(this, Observer { personas ->
            // Iterar sobre la lista de personas y subirlas a Firebase Realtime Database
            personas.forEach { persona ->
                myRef.child(persona.id.toString()).setValue(persona)
            }
        })


    }


    private fun abrirActivity(activityClass: Class<*>,accion: String) {
        val intent = Intent(this, activityClass)
            .putExtra("accion", accion)
        startActivity(intent)
    }

    private fun cargarDatosEnFormulario(persona: Persona, accion:String) {
        val intent = Intent(this, RegistroPersonaActivity::class.java).apply {
            Log.d("persona cargada", persona.toString())
            putExtra("persona", persona)
            putExtra("accion", accion)
        }

        startActivity(intent)
    }


    private fun getAllPersonas(): LiveData<List<Persona>> {
        return DbAppIni.db.personaDao().getAll()
    }

}