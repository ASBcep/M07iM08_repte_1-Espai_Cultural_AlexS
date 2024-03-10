package asb.m07im08.espai_cultural_alexs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class Reservar : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reservar)

        val btnEnrereRsrvNum = findViewById<Button>(R.id.btnEnrereRsrvNum)
        btnEnrereRsrvNum.setOnClickListener {
            finish()
        }
    }
}