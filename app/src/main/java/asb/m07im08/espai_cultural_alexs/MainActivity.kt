package asb.m07im08.espai_cultural_alexs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        fun obrirLlistatEsdeveniments(view: View) {
            val intent = Intent(this, Llistat_Esdeveniments::class.java)
            startActivity(intent)
        }
    }
}