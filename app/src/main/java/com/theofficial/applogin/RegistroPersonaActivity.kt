package com.theofficial.applogin

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase
import com.theofficial.applogin.Models.ViewModels.RegistrarPersonaViewModel
import com.theofficial.applogin.Room.Entity.Persona

class RegistroPersonaActivity : AppCompatActivity() {
    private val registrarPersonaViewModel: RegistrarPersonaViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {

        val database = FirebaseDatabase.getInstance()
        val myRef = database.getReference("personas")

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_persona)

        val etNombre = findViewById<TextView>(R.id.etNombre)
        val etApellido = findViewById<EditText>(R.id.etApellido)
        val etCelular = findViewById<EditText>(R.id.etCelular)
        val btnRegistrate = findViewById<Button>(R.id.btnRegistrate)
        val tvCerrar = findViewById<TextView>(R.id.tvCerrar)
        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val btnEliminar = findViewById<Button>(R.id.btnEliminar)
        val etId = findViewById<EditText>(R.id.etId)

        val accion = intent.getStringExtra("accion")
        if (accion == "agregar") {
            // Mostrar el botón de registro y ocultar los de guardar y eliminar
            btnRegistrate.visibility = View.VISIBLE
            btnGuardar.visibility = View.GONE
            btnEliminar.visibility = View.GONE
        } else if (accion == "editar") {
            // Ocultar el botón de registro y mostrar los de guardar y eliminar
            btnRegistrate.visibility = View.GONE
            btnGuardar.visibility = View.VISIBLE
            btnEliminar.visibility = View.VISIBLE
        }

        val persona = intent.getParcelableExtra<Persona>("persona")
        //btnRegistrate.visibility = View.VISIBLE
        // Usar los datos de la persona para inicializar los campos del formulario
        Log.d("formulario", persona?.id.toString())
        etId.setText(persona?.id.toString())
        etNombre.setText(persona?.nombres)
        etApellido.setText(persona?.apellidos)
        etCelular.setText(persona?.celular)

        tvCerrar.setOnClickListener {
            abrirActivity(MainActivity::class.java)
        }

        btnRegistrate.setOnClickListener {

            val nombre = etNombre.text.toString()
            val apellido = etApellido.text.toString()
            val celular = etCelular.text.toString()
            if (nombre.isNotBlank() && apellido.isNotBlank() && celular.isNotBlank()) {
                registrarPersonaViewModel.registrarPersona(
                    Persona(0, nombre, apellido, celular),
                    onSuccess = {
                        Toast.makeText(this, "Registrado con éxito", Toast.LENGTH_SHORT).show()
                        abrirActivity(MainActivity::class.java)
                    },
                    onError = {
                        Toast.makeText(this, "Error al registrar persona", Toast.LENGTH_SHORT)
                            .show()
                    }
                )
            } else {
                Toast.makeText(this, "Por favor llenar todos los campos", Toast.LENGTH_SHORT).show()
            }

        }

        btnGuardar.setOnClickListener {
            val id = etId.text.toString()
            val nombre = etNombre.text.toString()
            val apellido = etApellido.text.toString()
            val celular = etCelular.text.toString()
            if (nombre.isNotBlank() && apellido.isNotBlank() && celular.isNotBlank()) {
                registrarPersonaViewModel.actualizarPersona(
                    Persona(id.toLong(), nombre, apellido, celular),
                    onSuccess = {
                        Toast.makeText(this, "Actualizado con exito", Toast.LENGTH_SHORT).show()
                        abrirActivity(MainActivity::class.java)
                    },
                    onError = {
                        Toast.makeText(this, "Error al actualizar persona", Toast.LENGTH_SHORT)
                            .show()
                    })

            } else {
                Toast.makeText(this, "Por favor llenar todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        btnEliminar.setOnClickListener {
            val id = etId.text.toString()
            val nombre = etNombre.text.toString()
            val apellido = etApellido.text.toString()
            val celular = etCelular.text.toString()
            if (nombre.isNotBlank() && apellido.isNotBlank() && celular.isNotBlank()) {
                myRef.child(id).removeValue()
                    .addOnSuccessListener {
                        registrarPersonaViewModel.eliminarPersona(
                            Persona(id.toLong(), nombre, apellido, celular),
                            onSuccess = {
                                Toast.makeText(this, "Eliminado con exito", Toast.LENGTH_SHORT)
                                    .show()
                                abrirActivity(MainActivity::class.java)
                            },
                            onError = {
                                Toast.makeText(
                                    this,
                                    "Error al eliminar la persona",
                                    Toast.LENGTH_SHORT
                                )
                                    .show()
                            })

                    }
                    .addOnFailureListener {
                        // Si la eliminación en Firebase falla, mostrar mensaje de error
                        Toast.makeText(
                            this,
                            "Error al eliminar la persona en Firebase",
                            Toast.LENGTH_SHORT
                        ).show()
                    }

            } else {
                    Toast.makeText(this, "Por favor llenar todos los campos", Toast.LENGTH_SHORT)
                        .show()
                }

            }
        }


    private fun abrirActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }
}