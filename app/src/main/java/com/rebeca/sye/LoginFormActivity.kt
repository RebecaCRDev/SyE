package com.rebeca.sye

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class LoginFormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_form)

        val etEmail = findViewById<EditText>(R.id.etEmailLogin)
        val etPassword = findViewById<EditText>(R.id.etPasswordLogin)
        val btnLogin = findViewById<Button>(R.id.btnIniciarSesion)
        val btnBack = findViewById<ImageButton>(R.id.btnBackLoginForm)

        // Botón atrás (flecha)
        btnBack.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

        // Botón iniciar sesión
        btnLogin.setOnClickListener {
            val email = etEmail.text.toString().trim()
            val password = etPassword.text.toString().trim()

            if (email.isEmpty() || password.isEmpty()) {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Error")
                    .setMessage("Todos los campos son obligatorios.")
                    .setPositiveButton("Aceptar", null)
                    .show()
                return@setOnClickListener
            }

            val prefs = getSharedPreferences("usuarios", MODE_PRIVATE)
            val emailGuardado = prefs.getString("email", null)
            val passGuardada = prefs.getString("password", null)

            if (email == emailGuardado && password == passGuardada) {
                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("inicioCorrecto", true)
                startActivity(intent)
                overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
            } else {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Error")
                    .setMessage("Credenciales incorrectas.")
                    .setPositiveButton("Aceptar", null)
                    .show()
            }
        }
    }
}