package com.rebeca.sye

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class LoginFormActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_form)

        val etEmail = findViewById<EditText>(R.id.etEmailLogin)
        val etPass = findViewById<EditText>(R.id.etPasswordLogin)
        val btnBack = findViewById<Button>(R.id.btnBackLoginForm)
        val btnIniciar = findViewById<Button>(R.id.btnIniciarSesion)

        btnBack.setOnClickListener { finish() }

        btnIniciar.setOnClickListener {
            val prefs = getSharedPreferences("usuarios", MODE_PRIVATE)
            val emailGuardado = prefs.getString("email", null)
            val passGuardada = prefs.getString("password", null)

            if (etEmail.text.toString() == emailGuardado &&
                etPass.text.toString() == passGuardada) {

                val intent = Intent(this, LoginActivity::class.java)
                intent.putExtra("inicioCorrecto", true)
                startActivity(intent)
                finish()
            } else {
                MaterialAlertDialogBuilder(this)
                    .setTitle("Datos incorrectos")
                    .setMessage("Email o contraseña no válidos.")
                    .setPositiveButton("Aceptar", null)
                    .show()
            }
        }
    }
}