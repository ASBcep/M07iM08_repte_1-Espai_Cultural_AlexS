package asb.m07im08.espai_cultural_alexs

import android.graphics.Color
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
92
        //declaro views
        val tvTitol = findViewById<TextView>(R.id.tvTitol)
        val ivHR = findViewById<ImageView>(R.id.ivHR)
        val tvTipusEntrades = findViewById<TextView>(R.id.tvTipusEntrades)
        val llNumeradesNomPersona = findViewById<LinearLayout>(R.id.llNumeradesNomPersona)
        val tvTitularEntrades = findViewById<TextView>(R.id.tvTitularEntrades)
        val etTitularEntrades = findViewById<TextView>(R.id.etTitularEntrades)
        val tvEntradesOriginals = findViewById<TextView>(R.id.tvEntradesOriginals)
        val tvTriarEntrades = findViewById<TextView>(R.id.tvTriarEntrades)
        val llSelectorEntrades = findViewById<LinearLayout>(R.id.llSelectorEntrades)
        val spEntrades = findViewById<Spinner>(R.id.spEntrades)
        val tvEntradesAssignades = findViewById<TextView>(R.id.tvEntradesAssignades)
        val tvEntradesOcupades = findViewById<TextView>(R.id.tvEntradesOcupades)
        val tvLocalitatsTotal = findViewById<TextView>(R.id.tvLocalitatsTotal)
        val tvLocalitatsDisponibles = findViewById<TextView>(R.id.tvLocalitatsDisponibles)
        val btnEnrere = findViewById<Button>(R.id.btnEnrere)
        val btnReservar = findViewById<Button>(R.id.btnReservar)
        val btnEliminar = findViewById<Button>(R.id.btnEliminar)

        //declaro variables
        var llistaSpinner = mutableListOf<Int>()
        var aforament = Esdeveniment_Manager.aforament
        var entradesTriades = 0
        var entradesPreassignades = mutableListOf<Int>()
        var eliminar = false

        //modifico contingut de views
        tvEntradesOcupades.text = "Les entrades: \n" + GestorEntrades.entradesReservadesLlistat(esdevenimentThis).toString() + "\nno estan disponibles"
        tvLocalitatsTotal.text = "Localitats: " + aforament
        tvLocalitatsDisponibles.text = "Disponibles: " + (GestorEntrades.entradesDisponiblesNumero(esdevenimentThis)).toString()

        //insereixo imatge
        GestorImatge.inserirImatgeHR(esdevenimentThis.id.toString(), this, ivHR)

        //cribo segons si l'esdeveniment és numerat o no
        if (esdevenimentThis.numerat){
            if (novaReserva){
                tvTriarEntrades.text = getString(R.string.reservartvTriarEntradesNum)
            } else {//modificar reserva
                tvTriarEntrades.text = "Canvia l'entrada assignada:"
                tvEntradesOriginals.text = "La teva entrada: " + entradaThis.id
            }
            tvTipusEntrades.text = "Entrades numerades"
            llistaSpinner = GestorEntrades.entradesDisponiblesLlistat(esdevenimentThis)
            var numeroDEntradesAReservar = 1
            for (id in 1..GestorEntrades.entradesDisponiblesNumero(esdevenimentThis)){
                llistaSpinner.add(numeroDEntradesAReservar)
                numeroDEntradesAReservar++
            }
        } else {//no numerat
            if (novaReserva){
                tvTriarEntrades.text = getString(R.string.reservartvTriarEntradesNoNum)
            } else {//modificar reserva
                tvTriarEntrades.text = "Modifica el número d'entrades assignades:"
                val llistatEntradesPersona = GestorEntrades.entradesPerPersonaLlistat(esdevenimentThis, entradaThis.nom_reserva)
                tvEntradesOriginals.text = "Les teves entrades sumen un total de " + llistatEntradesPersona.count().toString()+ " : \n" + llistatEntradesPersona.toString()
            }
            tvTipusEntrades.text = "Entrades no numerades"
            for (id in 1..GestorEntrades.entradesDisponiblesNumero(esdevenimentThis)) {
                llistaSpinner.add(id)
            }
            tvEntradesOcupades.visibility = View.GONE
            tvTriarEntrades.text = getString(R.string.reservartvTriarEntradesNoNum)
        }
        //cribo segons si la reserva és nova o existent
        if (novaReserva) {
            tvTitol.text = "Reservar entrades per " + esdevenimentThis.nom
            tvEntradesAssignades.visibility = View.GONE
            btnEliminar.visibility = View.GONE
        } else {
            tvTitol.text = "Entrades reservades de " + esdevenimentThis.nom + " a nom de " + entradaThis.nom_reserva
            tvTitularEntrades.text = "Edita el titular de la reserva:"
            etTitularEntrades.text = entradaThis.nom_reserva
            //llNumeradesNomPersona.visibility = View.GONE
            btnReservar.text = "Desar canvis"
        }

        //introduir la llista que es mostrarà a l'spinner (desplegable)
        if (llistaSpinner.count() > 0){
            val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, llistaSpinner)
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spEntrades.adapter = adapter
        } else {
            Toast.makeText(this, getString(R.string.noEntrades), Toast.LENGTH_SHORT).show()
            tvEntradesAssignades.text = getString(R.string.noEntrades)
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
                if (esdevenimentThis.numerat){
                    entradesPreassignades.clear()
                    entradesPreassignades.add(entradesTriades)
                    tvEntradesAssignades.text = getString(R.string.reservartvEntradesAssignadesNum) + entradesPreassignades
                } else {
                    entradesPreassignades = GestorEntrades.trobarIdsDisponibles(esdevenimentThis.entrades, entradesTriades, aforament)
                    tvEntradesAssignades.text = getString(R.string.reservartvEntradesAssignadesNoNum) + entradesPreassignades
                }
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
            var esdevenimentModificat = esdevenimentThis
            var desat = false
            if (novaReserva){
                if (esdevenimentThis.numerat) {//numerat
                    if (!etTitularEntrades.text.toString().equals("")){
                        for (id in entradesPreassignades){
                            entradesAReservar.add (Entrada(id, etTitularEntrades.text.toString()))
                        }
                        esdevenimentModificat = GestorEntrades.assignarEntrades(esdevenimentThis, entradesAReservar)
                        desat = JsonIO.modificarEsdeveniment(this, esdevenimentModificat, JsonIO.cercarEsdeveniment(esdevenimentThis))
                        if (desat) {
                            finish()
                        }
                    } else {
                        Toast.makeText(this, "Si us plau, introdueix un nom per fer la reserva", Toast.LENGTH_SHORT).show()
                    }
                } else {//no numerat
                    if (!etTitularEntrades.text.toString().equals("")){
                        for (id in entradesPreassignades){
                            entradesAReservar.add (Entrada(id, etTitularEntrades.text.toString()))
                        }
                        esdevenimentModificat = GestorEntrades.assignarEntrades(esdevenimentThis, entradesAReservar)
                        desat = JsonIO.modificarEsdeveniment(this, esdevenimentModificat, JsonIO.cercarEsdeveniment(esdevenimentThis))
                        if (desat) {
                            finish()
                        }
                    } else {
                        Toast.makeText(this, "Si us plau, introdueix un nom per fer la reserva", Toast.LENGTH_SHORT).show()
                    }
                }
            } else {//modificar reserva
                if (esdevenimentThis.numerat) {//numerat
                    if (!etTitularEntrades.text.toString().equals("")){
                        val entradaModificada = Entrada(spEntrades.selectedItem.toString().toInt(), etTitularEntrades.text.toString())
                        var modificada = GestorEntrades.modificarEntrada(this, esdevenimentThis, entradaThis, entradaModificada)
                        if (modificada){
                            finish()
                        } else {
                            Toast.makeText(this, "Error: No s'han desat els canvis", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "Si us plau, introdueix un nom per modificar la reserva", Toast.LENGTH_SHORT).show()
                    }
                } else {//no numerat
                    //TODO("Codi per modificar reserva no numerada")
                    //GestorEntrades.modificarEntrada(this, esdevenimentThis,1,1)
                    if (!etTitularEntrades.text.toString().equals("")){
                        var mateixTitular = false
                        var mateixesEntrades = false
                        if(etTitularEntrades.text.toString() == entradaThis.nom_reserva) { mateixTitular = true }
                        if (GestorEntrades.entradesPerPersonaNumero(esdevenimentThis, entradaThis.nom_reserva) == spEntrades.selectedItem) {
                            mateixesEntrades = true
                        }
                        if (!(mateixesEntrades&&mateixTitular)){
                            var entradesOriginals = GestorEntrades.entradesPerPersonaLlistat(esdevenimentThis, etTitularEntrades.text.toString())
                            var eliminades = false
                            for (entrada in esdevenimentThis.entrades){
                                if (entradesOriginals.contains(entrada.id)){
                                    eliminades = GestorEntrades.eliminarEntrada(this,esdevenimentThis, entrada)
                                }
                            }
                            if (eliminades){
                                eliminades = false
                                for (id in entradesPreassignades){
                                    entradesAReservar.add (Entrada(id, etTitularEntrades.text.toString()))
                                    esdevenimentModificat = GestorEntrades.assignarEntrades(esdevenimentThis, entradesAReservar)
                                    desat = JsonIO.modificarEsdeveniment(this, esdevenimentModificat, JsonIO.cercarEsdeveniment(esdevenimentThis))
                                    if (desat) {
                                        finish()
                                    }
                                }
                            }
                        } else {
                            Toast.makeText(this, "Si us plau, tria un número d'entrades diferent a l'actual", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "Si us plau, introdueix un nom per modificar la reserva", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        btnEliminar.setOnClickListener{
            if (eliminar){
                //GestorEntrades.trobarEntrada(esdevenimentThis, entradaThis)
                var entradaEliminada = GestorEntrades.eliminarEntrada(this, esdevenimentThis, entradaThis)
                if (entradaEliminada){
                    finish()
                }
            } else {
                eliminar = true
                btnEliminar.setBackgroundColor(Color.RED)
                btnEliminar.text = "CONFIRMAR ELIMINACIÓ"
            }
        }
    }
}