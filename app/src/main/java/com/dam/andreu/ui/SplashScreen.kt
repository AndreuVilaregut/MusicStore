package com.dam.andreu.ui

import android.animation.ValueAnimator
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.dam.andreu.R
import com.dam.andreu.ui.formRorL.FragmentLorR
import com.dam.andreu.utils.CsvUtilsGuitarra

class SplashScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_splash_screen)

        CsvUtilsGuitarra.guardarGuitarres(this)

        val guitarres = CsvUtilsGuitarra.carregarGuitarres(this)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val progressBar: ProgressBar = findViewById(R.id.ProgressBarCarregant)
        val animator = ValueAnimator.ofInt(0, 100)
        animator.duration = 5000

        val textCarregnat: TextView = findViewById(R.id.textCarregnat)

        Handler(Looper.getMainLooper()).postDelayed({
            textCarregnat.text = "Esperi un moment..."
        }, 3000)

        animator.interpolator = android.view.animation.AccelerateDecelerateInterpolator()

        animator.addUpdateListener { animation ->
            val progress = animation.animatedValue as Int
            progressBar.progress = progress
        }

        animator.start()

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, FragmentLorR::class.java)
            startActivity(intent)
            finish()
        }, 5000)
    }
}
