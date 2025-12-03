package com.rebeca.sye

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.SimpleDateFormat
import java.util.*

class RegisterActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        // CAMPOS
        val etNombre = findViewById<EditText>(R.id.etNombre)
        val etApellidos = findViewById<EditText>(R.id.etApellidos)
        val etFecha = findViewById<EditText>(R.id.etFechaNacimiento)
        val etEmail = findViewById<EditText>(R.id.etEmail)
        val etTelefono = findViewById<EditText>(R.id.etTelefono)
        val etNombrePila = findViewById<EditText>(R.id.etNombrePila)
        val etPassword = findViewById<EditText>(R.id.etPassword)

        // BOTONES
        val btnBack = findViewById<ImageButton>(R.id.btnBackRegister)
        val btnCrear = findViewById<Button>(R.id.btnCrearUsuario)

        // Botón atrás
        btnBack.setOnClickListener {
            finish()
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

        // Botón crear usuario
        btnCrear.setOnClickListener {

            // Validación campos vacíos
            if (etNombre.text.isBlank() ||
                etApellidos.text.isBlank() ||
                etFecha.text.isBlank() ||
                etEmail.text.isBlank() ||
                etTelefono.text.isBlank() ||
                etNombrePila.text.isBlank() ||
                etPassword.text.isBlank()
            ) {
                mostrarError("Debes completar todos los campos")
                return@setOnClickListener
            }

            // Validar email
            if (!Patterns.EMAIL_ADDRESS.matcher(etEmail.text.toString()).matches()) {
                mostrarError("Email no válido")
                return@setOnClickListener
            }

            // Validar teléfono
            if (!etTelefono.text.toString().matches(Regex("^[0-9]{9}$"))) {
                mostrarError("Introduce un número de teléfono válido (9 dígitos)")
                return@setOnClickListener
            }

            // Validar contraseña
            if (etPassword.text.length < 6) {
                mostrarError("La contraseña debe tener al menos 6 caracteres")
                return@setOnClickListener
            }

            // Validar fecha y edad
            val edad = calcularEdad(etFecha.text.toString())
            if (edad == null) {
                mostrarError("Introduce la fecha en formato dd/MM/yyyy")
                return@setOnClickListener
            }
            if (edad < 12 || edad > 120) {
                mostrarError("La edad debe estar entre 12 y 120 años")
                return@setOnClickListener
            }

            // Guardar usuario
            val prefs = getSharedPreferences("usuarios", MODE_PRIVATE).edit()
            prefs.putString("nombre", etNombre.text.toString())
            prefs.putString("apellidos", etApellidos.text.toString())
            prefs.putString("fecha", etFecha.text.toString())
            prefs.putString("email", etEmail.text.toString())
            prefs.putString("telefono", etTelefono.text.toString())
            prefs.putString("nombrePila", etNombrePila.text.toString())
            prefs.putString("password", etPassword.text.toString())
            prefs.apply()

            // Volver al login
            val intent = Intent(this, LoginActivity::class.java)
            intent.putExtra("usuarioCreado", true)
            startActivity(intent)
            overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
        }

        overridePendingTransition(R.anim.fade_in, R.anim.fade_out)
    }

    private fun mostrarError(msg: String) {
        MaterialAlertDialogBuilder(this)
            .setTitle("Error")
            .setMessage(msg)
            .setPositiveButton("Aceptar", null)
            .show()
    }

    private fun calcularEdad(fechaStr: String): Int? {
        return try {
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            sdf.isLenient = false
            val fechaNacimiento = sdf.parse(fechaStr) ?: return null

            val hoy = Calendar.getInstance()
            val nacimiento = Calendar.getInstance()
            nacimiento.time = fechaNacimiento

            var edad = hoy.get(Calendar.YEAR) - nacimiento.get(Calendar.YEAR)
            if (hoy.get(Calendar.DAY_OF_YEAR) < nacimiento.get(Calendar.DAY_OF_YEAR)) {
                edad--
            }
            edad
        } catch (e: Exception) {
            null
        }
    }
}