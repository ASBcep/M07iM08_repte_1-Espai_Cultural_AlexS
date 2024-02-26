package asb.m07im08.espai_cultural_alexs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val btnLlistat = findViewById<Button>(R.id.btnLlistat)
        btnLlistat.setOnClickListener {
            val intent = Intent(this, Llistat_Esdeveniments::class.java)
            startActivity(intent)
        }
    }
}