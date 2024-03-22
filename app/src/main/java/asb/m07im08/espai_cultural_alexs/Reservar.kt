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

        //declaro views
        val tvTitol = findViewById<TextView>(R.id.tvTitol)
        val ivHR = findViewById<ImageView>(R.id.ivHR)
        val tvTipusEntrades = findViewById<TextView>(R.id.tvTipusEntrades)
        val etTitularEntrades = findViewById<TextView>(R.id.etTitularEntrades)
        val llSelectorEntrades = findViewById<LinearLayout>(R.id.llSelectorEntrades)
        val spEntrades = findViewById<Spinner>(R.id.spEntrades)
        val tvEntradesAssignades = findViewById<TextView>(R.id.tvEntradesAssignades)
        val tvEntradesOcupades = findViewById<TextView>(R.id.tvEntradesOcupades)
        val tvLocalitatsTotal = findViewById<TextView>(R.id.tvLocalitatsTotal)
        val tvLocalitatsDisponibles = findViewById<TextView>(R.id.tvLocalitatsDisponibles)
        val btnEnrere = findViewById<Button>(R.id.btnEnrere)
        val btnReservar = findViewById<Button>(R.id.btnReservar)

        //declaro variables
        var llistaSpinner = mutableListOf<Int>()
        var aforament = Esdeveniment_Manager.aforament
        var entradesTriades = 0
        var entradesPreassignades = mutableListOf<Int>()

        //modifico contingut de views
        tvTitol.text = "Reservar entrades per " + esdevenimentThis.nom
        tvEntradesOcupades.text = "Les entrades: \n" + GestorEntrades.entradesReservadesLlistat(esdevenimentThis).toString() + "\n no estan disponibles"
        tvLocalitatsTotal.text = "Localitats: " + aforament
        tvLocalitatsDisponibles.text = "Disponibles: " + (GestorEntrades.entradesDisponiblesNumero(esdevenimentThis)).toString()

        //insereixo imatge
        GestorImatge.inserirImatgeHR(esdevenimentThis.id.toString(), this, ivHR)

        //cribo segons si l'esdeveniment és numerat o no
        if (esdevenimentThis.numerat){
            tvTipusEntrades.text = "Entrades numerades"
            //TODO("adaptar activity per reservar entrades numerades")
            for (id in GestorEntrades.trobarIdsDisponibles(esdevenimentThis.entrades, GestorEntrades.entradesDisponiblesNumero(esdevenimentThis), Esdeveniment_Manager.aforament)) {
                llistaSpinner.add(id)
            }
        } else {
            tvTipusEntrades.text = "Entrades no numerades"
            //TODO("adaptar activity per reservar entrades no numerades")
            llistaSpinner = GestorEntrades.entradesDisponiblesLlistat(esdevenimentThis)
            var numeroDEntradesAReservar = 1
            for (id in 1..GestorEntrades.entradesDisponiblesNumero(esdevenimentThis)){
                llistaSpinner.add(numeroDEntradesAReservar)
                numeroDEntradesAReservar++
            }
            tvEntradesOcupades.visibility = View.GONE
        }
        
        //cribo segons si la reserva és nova o existent
        if (novaReserva) {

        } else {
            etTitularEntrades.text = entradaThis.nom_reserva
        }
        if (llistaSpinner.count() > 1){
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, llistaSpinner)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spEntrades.adapter = adapter
        } else {
            Toast.makeText(this, "No hi ha entrades disponibles", Toast.LENGTH_SHORT).show()
            llSelectorEntrades.visibility = View.GONE
        }
        
        //event de l'spinner
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
                tvEntradesAssignades.text = "Se t'han preassignat les entrades: \n" + entradesPreassignades
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No cal fer res aquí, ja que aquest mètode es crida quan no es selecciona cap ítem
            }
        }
        //events dels buttons
        btnEnrere.setOnClickListener {
            finish()
        }
        btnReservar.setOnClickListener{
            var entradesAReservar = mutableListOf<Entrada>()
            if (esdevenimentThis.numerat) {
                for (id in entradesPreassignades){
                    entradesAReservar.add (Entrada(id, etTitularEntrades.text.toString()))
                }
                if (!etTitularEntrades.text.equals(null)){
                    GestorEntrades.assignarEntrades(esdevenimentThis, entradesAReservar)
                    JsonIO.modificarEsdeveniment(this, esdevenimentThis, JsonIO.cercarEsdeveniment(esdevenimentThis))
                    finish()
                } else {
                    Toast.makeText(this, "Si us plau, introdueix un nom per fer la reserva", Toast.LENGTH_SHORT).show()
                }
            } else {
                if (!etTitularEntrades.text.equals(null)){
                    GestorEntrades.assignarEntrades(esdevenimentThis, entradesAReservar)
                    JsonIO.modificarEsdeveniment(this, esdevenimentThis, JsonIO.cercarEsdeveniment(esdevenimentThis))
                    finish()
                } else {
                    Toast.makeText(this, "Si us plau, introdueix un nom per fer la reserva", Toast.LENGTH_SHORT).show()
                }
            }
        }
        //TODO("btnEliminar.setOnClickListener{}")
    }
}