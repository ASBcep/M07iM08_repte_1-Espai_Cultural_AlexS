package asb.m07im08.espai_cultural_alexs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import kotlinx.coroutines.selects.select

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
        tvLocalitatsDisponibles.text = "Disponibles: " + (entradesDisponibles).toString()

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
        val llSelectorEntrades = findViewById<LinearLayout>(R.id.llSelectorEntrades)
        if (llistatEntradesDisponibles.count() > 1){
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, llistatEntradesDisponibles)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spEntrades.adapter = adapter
        } else {
            Toast.makeText(this, "No hi ha entrades disponibles", Toast.LENGTH_SHORT).show()
            llSelectorEntrades.visibility = View.GONE
        }
        var entradesTriades = 0
        var entradesPreassignades = mutableListOf<Int>()
        val tvEntradesPreassignades = findViewById<TextView>(R.id.tvEntradesPreassignades)
        spEntrades.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long) {
                // Aquí pots obtenir el valor de l'ítem seleccionat
                val selectedItem = parent?.getItemAtPosition(position)
                // Feu el que vulgueu amb el valor seleccionat
                entradesTriades = selectedItem.toString().toInt()
                entradesPreassignades = GestorEntrades.trobarIdsDisponibles(esdevenimentThis.entrades, entradesTriades, aforament)
                tvEntradesPreassignades.text = "Se t'han preassignat les entrades: " + entradesPreassignades
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No cal fer res aquí, ja que aquest mètode es crida quan no es selecciona cap ítem
            }
        }


        val btnEnrere = findViewById<Button>(R.id.btnEnrere)
        btnEnrere.setOnClickListener {
            finish()
        }
    }
}