package com.rebeca.sye

import android.graphics.Color
import android.os.Bundle
import android.util.TypedValue
import android.view.Gravity
import android.widget.FrameLayout
import android.widget.GridLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.card.MaterialCardView

class GameActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)

        val tablero = findViewById<GridLayout>(R.id.tableroLayout)

        tablero.rowCount = 10
        tablero.columnCount = 10

        val colores = listOf(
            Color.parseColor("#F7DC6F"),
            Color.parseColor("#AED6F1"),
            Color.parseColor("#ABEBC6"),
            Color.parseColor("#F5B7B1")
        )

        val serpientes = setOf(98, 87, 65, 43)
        val escaleras = setOf(4, 12, 27, 39)

        for (numero in 100 downTo 1) {
            val card = MaterialCardView(this)

            val params = GridLayout.LayoutParams().apply {
                width = 0
                height = dpToPx(42f)
                columnSpec = GridLayout.spec(GridLayout.UNDEFINED, 1f)
                setMargins(dpToPx(2f), dpToPx(2f), dpToPx(2f), dpToPx(2f))
            }

            card.layoutParams = params
            card.radius = dpToPx(4f).toFloat()
            card.cardElevation = dpToPx(2f).toFloat()

            val index = (numero - 1) % colores.size
            card.setCardBackgroundColor(colores[index])

            val frame = FrameLayout(this)

            val tv = TextView(this).apply {
                text = numero.toString()
                setTextColor(Color.BLACK)
                gravity = Gravity.TOP or Gravity.START
                setPadding(dpToPx(4f), dpToPx(4f), 0, 0)
            }

            frame.addView(tv)

            if (numero in serpientes) {
                val serpienteView = android.view.View(this).apply {
                    layoutParams = FrameLayout.LayoutParams(dpToPx(12f), dpToPx(38f), Gravity.CENTER)
                    setBackgroundResource(R.drawable.serpiente)
                }
                frame.addView(serpienteView)
            }

            if (numero in escaleras) {
                val escaleraView = android.view.View(this).apply {
                    layoutParams = FrameLayout.LayoutParams(dpToPx(40f), dpToPx(6f), Gravity.CENTER)
                    rotation = 45f
                    setBackgroundResource(R.drawable.escalera)
                }
                frame.addView(escaleraView)
            }

            card.addView(frame)
            tablero.addView(card)
        }
    }

    private fun dpToPx(dp: Float): Int =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resources.displayMetrics).toInt()
}