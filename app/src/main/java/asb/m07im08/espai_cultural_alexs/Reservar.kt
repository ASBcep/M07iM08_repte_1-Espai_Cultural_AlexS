package asb.m07im08.espai_cultural_alexs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class Reservar : AppCompatActivity() {
    private var novaReserva: Boolean = false
    private var entradaThis: Entrada = Entrada()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //rebo putextra de l'intent
        novaReserva = intent.getBooleanExtra("detall", false)
        entradaThis = intent.getSerializableExtra("entrada") as Entrada? ?: Entrada()

        setContentView(R.layout.activity_reservar)

        val etTitularEntrades = findViewById<TextView>(R.id.etTitularEntrades)
        if (novaReserva == false) {
            etTitularEntrades.text = entradaThis.nom_reserva
        }

        val btnEnrere = findViewById<Button>(R.id.btnEnrere)
        btnEnrere.setOnClickListener {
            finish()
        }
    }
}