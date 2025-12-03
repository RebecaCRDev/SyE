package com.rebeca.sye

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val tvUsuario = findViewById<TextView>(R.id.tvUsuario)
        val btnJugar = findViewById<Button>(R.id.btnJugar)
        val btnCrearPerfil = findViewById<Button>(R.id.btnCrearPerfil)
        val btnIrLogin = findViewById<Button>(R.id.btnIrLogin)
        val btnCerrarSesion = findViewById<Button>(R.id.btnCerrarSesion)

        val prefs = getSharedPreferences("usuarios", MODE_PRIVATE)
        val usuario = prefs.getString("nombrePila", null)

        if (usuario != null) {
            tvUsuario.text = usuario
            btnJugar.visibility = Button.VISIBLE
            btnCerrarSesion.visibility = Button.VISIBLE
            btnCrearPerfil.visibility = Button.GONE
            btnIrLogin.visibility = Button.GONE
        } else {
            tvUsuario.text = "USUARIO"
            btnJugar.visibility = Button.GONE
            btnCerrarSesion.visibility = Button.GONE
            btnCrearPerfil.visibility = Button.VISIBLE
            btnIrLogin.visibility = Button.VISIBLE
        }

        btnJugar.setOnClickListener {
            startActivity(Intent(this, GameActivity::class.java))
        }

        btnCrearPerfil.setOnClickListener {
            startActivity(Intent(this, RegisterActivity::class.java))
        }

        btnIrLogin.setOnClickListener {
            startActivity(Intent(this, LoginFormActivity::class.java))
        }

        btnCerrarSesion.setOnClickListener {
            prefs.edit().clear().apply()
            recreate()
        }
    }
}