package com.theofficial.applogin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.theofficial.applogin.Models.ViewModels.RegistroViewModel

class RegistroActivity : AppCompatActivity() {

    private val registroViewModel: RegistroViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro)
        //val btnVolver = findViewById<Button>(R.id.btnVolver)
        val tvCerrar = findViewById<TextView>(R.id.tvCerrar)
        val etUsuarioR = findViewById<EditText>(R.id.etNombre)
        val etpContraseñaR = findViewById<EditText>(R.id.etpContraseñaR)
        val etpContraseñaR2 = findViewById<EditText>(R.id.etpContraseñaR2)
        val btnRegistrate = findViewById<Button>(R.id.btnRegistrate)

        tvCerrar.setOnClickListener {
            abrirActivity(LoginActivity::class.java)
        }

        btnRegistrate.setOnClickListener {
            val usuario = etUsuarioR.text.toString()
            val contraseña = etpContraseñaR.text.toString()
            val contraseña2 = etpContraseñaR2.text.toString()


            if (contraseña == contraseña2) {
                // Llamar al método registrarUsuario del RegistroViewModel, pasando los datos y callbacks
                registroViewModel.registrarUsuario(usuario, contraseña,
                    onSuccess = {
                        Toast.makeText(this, "Registrado con éxito", Toast.LENGTH_SHORT).show()
                        abrirActivity(LoginActivity::class.java)
                    },
                    onError = {
                        Toast.makeText(this, "Error al registrar usuario", Toast.LENGTH_SHORT).show()
                    }
                )
            } else {
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun abrirActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }

}