package com.rebeca.sye

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val tvUsuario = findViewById<TextView>(R.id.tvUsuario)
        val btnJugar = findViewById<Button>(R.id.btnJugar)
        val btnCrearPerfil = findViewById<Button>(R.id.btnCrearPerfil)
        val btnIrLogin = findViewById<Button>(R.id.btnIrLogin)
        val btnCerrarSesion = findViewById<Button>(R.id.btnCerrarSesion)

        val prefs = getSharedPreferences("usuarios", MODE_PRIVATE)
        val nombrePila = prefs.getString("nombrePila", null)

        if (intent.getBooleanExtra("usuarioCreado", false)) {
            MaterialAlertDialogBuilder(this)
                .setTitle("Éxito")
                .setMessage("Usuario creado con éxito.")
                .setPositiveButton("Aceptar", null)
                .show()
        }

        if (intent.getBooleanExtra("inicioCorrecto", false)) {
            MaterialAlertDialogBuilder(this)
                .setTitle("Bienvenido")
                .setMessage("Inicio de sesión correcto.")
                .setPositiveButton("Aceptar", null)
                .show()
        }

        if (nombrePila != null) {
            tvUsuario.text = nombrePila
            btnJugar.alpha = 1f
            btnCerrarSesion.visibility = View.VISIBLE
        } else {
            tvUsuario.text = "USUARIO"
            btnJugar.alpha = 0.5f
            btnCerrarSesion.visibility = View.GONE
        }

        btnJugar.setOnClickListener {
            val jugador = prefs.getString("nombrePila", null)
            if (jugador == null) {
                MaterialAlertDialogBuilder(this)
                    .setTitle("No puedes jugar aún")
                    .setMessage("Debes iniciar sesión o crear un usuario.")
                    .setPositiveButton("Aceptar", null)
                    .show()
            } else {
                startActivity(Intent(this, MainActivity::class.java))
            }
        }

        btnCrearPerfil.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        btnIrLogin.setOnClickListener {
            startActivity(Intent(this, LoginFormActivity::class.java))
        }

        btnCerrarSesion.setOnClickListener {
            prefs.edit().clear().apply()

            MaterialAlertDialogBuilder(this)
                .setTitle("Sesión cerrada")
                .setMessage("Has cerrado sesión correctamente.")
                .setPositiveButton("Aceptar", null)
                .show()

            startActivity(Intent(this, LoginActivity::class.java))
            finish()
        }
    }
}