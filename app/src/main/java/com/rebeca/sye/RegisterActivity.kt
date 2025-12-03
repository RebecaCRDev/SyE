package com.rebeca.sye

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.util.Calendar

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        val btnBack = findViewById<Button>(R.id.btnBackRegister)
        btnBack.setOnClickListener { finish() }

        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etApellidos = findViewById<EditText>(R.id.etApellidos)
        val etFecha = findViewById<EditText>(R.id.etFechaNacimiento)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etTelefono = findViewById<EditText>(R.id.etTelefono)
        val etNombrePila = findViewById<EditText>(R.id.etNombrePila)
        val etPassword = findViewById<EditText>(R.id.etPassword)
        val btnCrear = findViewById<Button>(R.id.btnCrearUsuario)

        etEmail.addTextChangedListener(validarEmail(etEmail))
        etTelefono.addTextChangedListener(validarTelefono(etTelefono))
        etFecha.addTextChangedListener(validarFecha(etFecha))
        etNombrePila.addTextChangedListener(validarNombreJuego(etNombrePila))

        btnCrear.setOnClickListener {

            val camposError = listOf(etEmail, etTelefono, etFecha, etNombrePila)
            if (camposError.any { it.error != null }) {
                alert("Datos inválidos", "Corrige todos los errores antes de continuar.")
                return@setOnClickListener
            }

            if (etNombre.text.isBlank() ||
                etApellidos.text.isBlank() ||
                etPassword.text.isBlank()) {
                alert("Faltan datos", "Completa todos los campos.")
                return@setOnClickListener
            }

            val prefs = getSharedPreferences("usuarios", MODE_PRIVATE).edit()
            prefs.putString("nombrePila", etNombrePila.text.toString())
            prefs.putString("email", etEmail.text.toString())
            prefs.putString("password", etPassword.text.toString())
            prefs.apply()

            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("usuarioCreado", true)
            startActivity(intent)
            finish()
        }
    }

    private fun alert(t: String, m: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle(t)
            .setMessage(m)
            .setPositiveButton("Aceptar", null)
            .show()
    }

    private fun validarEmail(et: EditText) = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            et.error = if (!s.toString().contains("@")) "Debe contener @" else null
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    private fun validarTelefono(et: EditText) = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            val t = s.toString()
            et.error = if (t.length == 9 && t.all { it.isDigit() }) null else "Debe tener 9 dígitos"
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    private fun validarFecha(et: EditText) = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            try {
                val p = s.toString().split("/")
                if (p.size != 3) {
                    et.error = "Formato dd/MM/yyyy"
                    return
                }

                val d = p[0].toInt()
                val m = p[1].toInt()
                val y = p[2].toInt()

                val hoy = Calendar.getInstance()
                val edad = hoy.get(Calendar.YEAR) - y -
                        if (hoy.get(Calendar.MONTH) + 1 < m ||
                            (hoy.get(Calendar.MONTH) + 1 == m && hoy.get(Calendar.DAY_OF_MONTH) < d)) 1 else 0

                et.error = if (edad < 18) "Debes ser mayor de 18" else null

            } catch (_: Exception) {
                et.error = "Fecha no válida"
            }
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }

    private fun validarNombreJuego(et: EditText) = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
            et.error = if (s.toString().length < 3) "Mínimo 3 caracteres" else null
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
    }
}