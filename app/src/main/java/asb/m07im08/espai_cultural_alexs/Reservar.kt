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
        val ivPlanellLocalitats = findViewById<ImageView>(R.id.ivPlanellLocalitats)
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
        tvEntradesOcupades.text = "Les entrades: \n" + GestorEntrades.treureClaudatorsDelLlistat(
            JsonIO.ordenarMutableListInt(
                GestorEntrades.entradesReservadesLlistat(esdevenimentThis)
            ).toString()
        ) + "\nno estan disponibles"
        tvLocalitatsTotal.text = "Localitats: " + aforament
        tvLocalitatsDisponibles.text = "Disponibles: " + (GestorEntrades.entradesDisponiblesNumero(esdevenimentThis)).toString()

        //insereixo imatge
        GestorImatge.inserirImatgeHR(esdevenimentThis.id.toString(), this, ivHR)

        //cribo segons si l'esdeveniment és numerat o no
        if (esdevenimentThis.numerat){
            if (novaReserva){
                tvTriarEntrades.text = getString(R.string.reservartvTriarEntradesNum)
                tvTitol.text = "Reservar entrada per l'esdeveniment: " + esdevenimentThis.nom
            } else {//modificar reserva
                tvTriarEntrades.text = "Canvia l'entrada assignada:"
                tvTitol.text = "Modificar entrada reservada de l'esdeveniment: " + esdevenimentThis.nom + "\nTitular de la reserva: " + entradaThis.nom_reserva
                tvEntradesOriginals.text = "Aquesta entrada és la número: " + entradaThis.id
            }
            tvTipusEntrades.text = "Entrades numerades"
            llistaSpinner = GestorEntrades.entradesDisponiblesLlistat(esdevenimentThis)
            /*var numeroDEntradesAReservar = 1
            for (id in 1..GestorEntrades.entradesDisponiblesNumero(esdevenimentThis)){
                llistaSpinner.add(numeroDEntradesAReservar)
                numeroDEntradesAReservar++
            }*/
        } else {//no numerat
            if (novaReserva){
                tvTriarEntrades.text = getString(R.string.reservartvTriarEntradesNoNum)
                tvTitol.text = "Reservar entrades per l'esdeveniment: " + esdevenimentThis.nom
            } else {//modificar reserva
                tvTriarEntrades.text = "Modifica el número d'entrades assignades:"
                tvTitol.text = "Modificar entrades reservades de l'esdeveniment: " + esdevenimentThis.nom + "\nTitular de la reserva: " + entradaThis.nom_reserva
                val llistatEntradesPersona = GestorEntrades.entradesPerPersonaLlistat(esdevenimentThis, entradaThis.nom_reserva)
                tvEntradesOriginals.text = "En total tens " + llistatEntradesPersona.count().toString()+ " entrades reservades: \n" + GestorEntrades.treureClaudatorsDelLlistat(llistatEntradesPersona.toString())
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
            //tvTitol.text = "Reservar entrades per l'esdeveniment: " + esdevenimentThis.nom
            tvEntradesOriginals.visibility = View.GONE
            //tvEntradesAssignades.visibility = View.GONE
            btnEliminar.visibility = View.GONE
        } else {
            //tvTitol.text = "Entrades reservades de l'esdeveniment: " + esdevenimentThis.nom + " a nom de: " + entradaThis.nom_reserva
            tvTitularEntrades.text = "Edita el titular de la reserva:"
            etTitularEntrades.text = entradaThis.nom_reserva
            //llNumeradesNomPersona.visibility = View.GONE
            btnReservar.text = "Desar canvis"
        }
        //introduir la llista que es mostrarà a l'spinner (desplegable)
        if (llistaSpinner.count() > 0){
            llistaSpinner = JsonIO.ordenarMutableListInt(llistaSpinner)
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
                    entradesPreassignades = JsonIO.ordenarMutableListInt(entradesPreassignades)
                    tvEntradesAssignades.text = getString(R.string.reservartvEntradesAssignadesNum) + GestorEntrades.treureClaudatorsDelLlistat(entradesPreassignades.toString())
                } else {
                    entradesPreassignades = GestorEntrades.trobarIdsDisponibles(esdevenimentThis.entrades, entradesTriades, aforament)
                    entradesPreassignades = JsonIO.ordenarMutableListInt(entradesPreassignades)
                    tvEntradesAssignades.text = getString(R.string.reservartvEntradesAssignadesNoNum) + GestorEntrades.treureClaudatorsDelLlistat(entradesPreassignades.toString())
                }
            }
            override fun onNothingSelected(parent: AdapterView<*>?) {
                // No cal fer res aquí, ja que aquest mètode es crida quan no es selecciona cap ítem
            }
        }
        ivPlanellLocalitats.setOnClickListener{
            Toast.makeText(this, "Si us plau, tria localitat al desplegable", Toast.LENGTH_SHORT).show()
        }
        //events dels buttons
        btnEnrere.setOnClickListener {
            setResult(RESULT_CANCELED)
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
                            setResult(RESULT_OK)
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
                            setResult(RESULT_OK)
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
                            setResult(RESULT_OK)
                            finish()
                        } else {
                            Toast.makeText(this, "Error: No s'han desat els canvis", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "Si us plau, introdueix un nom per modificar la reserva", Toast.LENGTH_SHORT).show()
                    }
                } else {//no numerat
                    if (!etTitularEntrades.text.toString().equals("")){
                        var mateixTitular = false
                        var mateixesEntrades = false
                        if(etTitularEntrades.text.toString() == entradaThis.nom_reserva) { mateixTitular = true }
                        if (GestorEntrades.entradesPerPersonaNumero(esdevenimentThis, entradaThis.nom_reserva) == spEntrades.selectedItem) {
                            mateixesEntrades = true
                        }
                        if (!(mateixesEntrades&&mateixTitular)){
                            var entradesOriginals = GestorEntrades.entradesPerPersonaLlistat(esdevenimentThis, entradaThis.nom_reserva)
                            var eliminades = false
                            var entradesAEliminar = mutableListOf<Entrada>()
                            for (entrada in esdevenimentThis.entrades){//per cada entrada
                                if (entradesOriginals.contains(entrada.id)){//verifico que l'id coincideixi amb les entrades del titular
                                    entradesAEliminar.add(entrada)//afegeixo entrada al llistat
                                }
                            }
                            eliminades = GestorEntrades.eliminarEntrades(this, esdevenimentThis, entradesAEliminar)
                            if (eliminades){
                                for (id in entradesPreassignades){//per cada número d'entrada preassignada
                                    entradesAReservar.add (Entrada(id, etTitularEntrades.text.toString()))//afegeixo l'entrada
                                }
                                esdevenimentModificat = GestorEntrades.assignarEntrades(esdevenimentThis, entradesAReservar)//assigno les noves entrades
                                desat = JsonIO.modificarEsdeveniment(this, esdevenimentModificat, JsonIO.cercarEsdeveniment(esdevenimentThis))//deso l'esdeveniment al llistat
                                if (desat) {
                                    setResult(RESULT_OK)
                                    finish()
                                }
                            } else {
                                Toast.makeText(
                                    this,
                                    "Error: No s'han eliminat les entrades",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        } else {
                            Toast.makeText(this, "Si us plau, tria un número d'entrades diferent a l'actual o canvia el titular", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(this, "Si us plau, introdueix un nom per modificar la reserva", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
        btnEliminar.setOnClickListener{
            if (eliminar){//TODO("Elminar entrades no numerades")
                var entradaEliminada = GestorEntrades.eliminarEntrada(this, esdevenimentThis, entradaThis)
                if (entradaEliminada){
                    setResult(RESULT_OK)
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