package asb.m07im08.espai_cultural_alexs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast

class Reservar : AppCompatActivity() {
    private var novaReserva: Boolean = false
    private var entradaThis: Entrada = Entrada()
    private var esdevenimentThis: Esdeveniment = Esdeveniment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //rebo putextra de l'intent
        novaReserva = intent.getBooleanExtra("novaReserva", false)
        entradaThis = intent.getSerializableExtra("entrada") as Entrada? ?: Entrada()
        esdevenimentThis = intent.getSerializableExtra("esdeveniment") as Esdeveniment? ?: Esdeveniment()
        setContentView(R.layout.activity_reservar)

        val tvTitol = findViewById<TextView>(R.id.tvTitol)
        tvTitol.text = "Reservar entrades per " + esdevenimentThis.nom

        val ivHR = findViewById<ImageView>(R.id.ivHR)
        GestorImatge.inserirImatgeHR(esdevenimentThis.id.toString(), this, ivHR)

        val tvTipusEntrades = findViewById<TextView>(R.id.tvTipusEntrades)
        if (esdevenimentThis.numerat){
            tvTipusEntrades.text = "Entrades numerades"
            //TODO("adaptar activity per reservar entrades numerades")
        } else {
            tvTipusEntrades.text = "Entrades no numerades"
            //TODO("adaptar activity per reservar entrades no numerades")
        }

        var aforament = Esdeveniment_Manager.aforament
        val entradesDisponibles = GestorEntrades.entradesDisponiblesNumero(esdevenimentThis)

        val tvLocalitatsTotal = findViewById<TextView>(R.id.tvLocalitatsTotal)
        tvLocalitatsTotal.text = "Localitats: " + aforament
        val tvLocalitatsDisponibles = findViewById<TextView>(R.id.tvLocalitatsDisponibles)
        tvLocalitatsDisponibles.text = "Disponibles: " + (aforament - entradesDisponibles).toString()

        val etTitularEntrades = findViewById<TextView>(R.id.etTitularEntrades)
        if (novaReserva) {

        } else {
            etTitularEntrades.text = entradaThis.nom_reserva
        }

        val spEntrades = findViewById<Spinner>(R.id.spEntrades)
        //val llistatEntradesDisponibles = GestorEntrades.entradesDisponiblesLlistat(esdevenimentThis)
        var llistatEntradesDisponibles = mutableListOf<Int>()
            for (i in 1..entradesDisponibles) {
            llistatEntradesDisponibles.add(i)
        }
        if (llistatEntradesDisponibles.count() > 1){
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, llistatEntradesDisponibles)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spEntrades.adapter = adapter
        } else {
            Toast.makeText(this, "Error: no s'ha pogut llegir el llistat d'entrades disponibles", Toast.LENGTH_SHORT).show()
        }




        val btnEnrere = findViewById<Button>(R.id.btnEnrere)
        btnEnrere.setOnClickListener {
            finish()
        }
    }
}