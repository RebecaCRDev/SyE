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
        setContentView(R.layout.activity_main)

        val tvUsuario = findViewById<TextView>(R.id.tvUsuario)
        val btnJugar = findViewById<Button>(R.id.btnJugar)
        val btnCrear = findViewById<Button>(R.id.btnCrearPerfil)
        val btnLogin = findViewById<Button>(R.id.btnIrLogin)
        val btnCerrarSesion = findViewById<Button>(R.id.btnCerrarSesion)

        // MODALES AL VENIR DEL REGISTRO / LOGIN
        if (intent.getBooleanExtra("usuarioCreado", false)) {
            MaterialAlertDialogBuilder(this)
                .setTitle("Éxito")
                .setMessage("Usuario creado con éxito")
                .setPositiveButton("Aceptar", null)
                .show()
        }

        if (intent.getBooleanExtra("inicioCorrecto", false)) {
            MaterialAlertDialogBuilder(this)
                .setTitle("Bienvenido")
                .setMessage("Inicio de sesión correcto")
                .setPositiveButton("Aceptar", null)
                .show()
        }

        // CARGAR USUARIO
        val prefs = getSharedPreferences("usuarios", MODE_PRIVATE)
        val nombrePila = prefs.getString("nombrePila", null)

        if (nombrePila != null) {
            // YA HAY UN USUARIO → MENU REDUCIDO
            tvUsuario.text = nombrePila
            btnJugar.isEnabled = true
            btnJugar.alpha = 1f

            btnCrear.visibility = View.GONE
            btnLogin.visibility = View.GONE
            btnCerrarSesion.visibility = View.VISIBLE

        } else {
            // NO HAY USUARIO → MENU COMPLETO
            tvUsuario.text = "USUARIO"
            btnJugar.isEnabled = false
            btnJugar.alpha = 0.4f

            btnCrear.visibility = View.VISIBLE
            btnLogin.visibility = View.VISIBLE
            btnCerrarSesion.visibility = View.GONE
        }

        // BOTÓN JUGAR → ABRIR TABLERO
        btnJugar.setOnClickListener {
            val prefsCheck = getSharedPreferences("usuarios", MODE_PRIVATE)
            val user = prefsCheck.getString("nombrePila", null)

            if (user == null) {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Aviso")
                    .setMessage("Primero debes crear un usuario o iniciar sesión.")
                    .setPositiveButton("Aceptar", null)
                    .show()
            } else {
                // AQUÍ ES DONDE ABRE TU TABLERO
                startActivity(Intent(this, GameActivity::class.java))
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            }
        }

        // BOTÓN CREAR USUARIO
        btnCrear.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

        // BOTÓN INICIAR SESIÓN
        btnLogin.setOnClickListener {
            startActivity(Intent(this, LoginFormActivity::class.java))
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

        // BOTÓN CERRAR SESIÓN
        btnCerrarSesion.setOnClickListener {
            prefs.edit().clear().apply()
            recreate()
        }
    }
}