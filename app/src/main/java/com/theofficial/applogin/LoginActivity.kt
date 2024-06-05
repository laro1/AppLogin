package com.theofficial.applogin

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.theofficial.applogin.Models.ViewModels.LoginViewModel

class LoginActivity : AppCompatActivity() {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val btnLogin = findViewById<Button>(R.id.btnLogin)
        val etUsuario = findViewById<EditText>(R.id.etUsuario)
        val etpContraseña = findViewById<EditText>(R.id.etpContrasena)
        val btRegistrarse = findViewById<TextView>(R.id.btRegistrarse)

        loginViewModel.loginSuccess.observe(this, Observer { success ->
            if (success) {
                // Login exitoso, abrir la actividad principal
                Toast.makeText(this, "Login exitoso", Toast.LENGTH_SHORT).show()
                abrirActivity(MainActivity::class.java)
            } else {
                // Login fallido, mostrar mensaje de error
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show()
            }
        })

        btnLogin.setOnClickListener {
            val username = etUsuario.text.toString()
            val password = etpContraseña.text.toString()
            loginViewModel.login(username, password)
        }

        btRegistrarse.setOnClickListener {
            abrirActivity(RegistroActivity::class.java)
        }


    }

    private fun abrirActivity(activityClass: Class<*>) {
        val intent = Intent(this, activityClass)
        startActivity(intent)
    }

}