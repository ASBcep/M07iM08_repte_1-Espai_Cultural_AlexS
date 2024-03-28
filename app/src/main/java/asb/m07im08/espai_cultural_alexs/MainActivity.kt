package asb.m07im08.espai_cultural_alexs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout

class MainActivity : AppCompatActivity() {
    private val handler = Handler(Looper.getMainLooper())
    private lateinit var runnable: Runnable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val llMainActivity = findViewById<LinearLayout>(R.id.llMainActivity)
        llMainActivity.setOnClickListener {
            iniciarActivitat()
        }

        // Configura el runnable per a iniciar l'activitat després de 10 segons
        runnable = Runnable {
            val intent = Intent(this, Llistat_Esdeveniments::class.java)
            intent.putExtra("reserves", false)
            startActivity(intent)
        }
        // Inicia el compte enrere quan es crea l'activitat
        iniciarCompteEnrere()
    }
    private fun iniciarCompteEnrere() {
        // Cancel·la la tasca anterior per evitar múltiples execucions
        handler.removeCallbacks(runnable)
        // Inicia una nova tasca per a l'activitat després de 10 segons
        handler.postDelayed(runnable, 10000) // 10 segons en mil·lisegons
    }
    private fun iniciarActivitat() {
        // Cancel·la el compte enrere quan es toca la pantalla
        handler.removeCallbacks(runnable)
        // Inicia l'activitat
        val intent = Intent(this, Llistat_Esdeveniments::class.java)
        intent.putExtra("reserves", false)
        startActivity(intent)
    }
    override fun onResume() {
        super.onResume()
        // Reconfigura el compte enrere quan l'aplicació es torna a reprendre
        iniciarCompteEnrere()
    }
    override fun onPause() {
        super.onPause()
        // Cancel·la el compte enrere quan l'aplicació està en pausa per evitar fuites de memòria
        handler.removeCallbacks(runnable)
    }
}