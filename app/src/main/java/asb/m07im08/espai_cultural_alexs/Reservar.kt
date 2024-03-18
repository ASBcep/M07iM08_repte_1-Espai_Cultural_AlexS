package asb.m07im08.espai_cultural_alexs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

class Reservar : AppCompatActivity() {
    private var novaReserva: Boolean = false
    private var entradaThis: Entrada = Entrada()
    private var esdevenimentThis: Esdeveniment = Esdeveniment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //rebo putextra de l'intent
        novaReserva = intent.getBooleanExtra("detall", false)
        entradaThis = intent.getSerializableExtra("entrada") as Entrada? ?: Entrada()
        esdevenimentThis = intent.getSerializableExtra("esdeveniment") as Esdeveniment? ?: Esdeveniment()
        setContentView(R.layout.activity_reservar)

        val tvTitol = findViewById<TextView>(R.id.tvTitol)
        tvTitol.text = "Reservar entrades per " + esdevenimentThis.nom
        if (esdevenimentThis.numerat){
            //TODO("adaptar activity per reservar entrades numerades")
        } else {
            //TODO("adaptar activity per reservar entrades no numerades")
        }

        val ivHR = findViewById<ImageView>(R.id.ivHR)
        GestorImatge.inserirImatgeHR(esdevenimentThis.imatge, this, ivHR)

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