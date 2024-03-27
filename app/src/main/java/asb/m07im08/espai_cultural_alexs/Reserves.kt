package asb.m07im08.espai_cultural_alexs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Reserves : AppCompatActivity() {
    private var esdevenimentThis: Esdeveniment = Esdeveniment()
    private var resultatResult = false
    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult())
        {
            //val txtVwAnunci = findViewById(R.id.TxtVwAnunci) as TextView
            if(it.resultCode == RESULT_OK) {
                //retornar un string
                //val nomComplet = it.data?.getStringExtra(Comprovacio.loginConstants.USUARI)
                /*retornar un objecte
                val satellite = it.data?.getSerializableExtra()
                */
                //txtVwAnunci.text = "Usuari i contrasenya correctes"
                Toast.makeText(this, "Reserva modificada", Toast.LENGTH_SHORT).show()
                resultatResult = true
                //recreate()
                carregaViews()
            }
        }
    //private lateinit var adapter: TaulaEntradesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserves)

        //rebo putextra de l'intent
        //esdeveniment = intent.getSerializableExtra("esdeveniment", Esdeveniment)

        esdevenimentThis = intent.getSerializableExtra("esdeveniment") as Esdeveniment? ?: Esdeveniment()

        carregaViews()

        val tvTitol = findViewById<TextView>(R.id.tvTitol)
        tvTitol.text = "Gestionar entrades de " + esdevenimentThis.nom

        val ivHR = findViewById<ImageView>(R.id.ivHR)
        GestorImatge.inserirImatgeHR(esdevenimentThis.id.toString(), this, ivHR)

        val tvLocalitatsTotal = findViewById<TextView>(R.id.tvLocalitatsTotal)
        tvLocalitatsTotal.text = "Localitats: " + Esdeveniment_Manager.aforament

        //var index = 0
        /*val columnesRecyclerView = 1

        // Trobar el RecyclerView pel seu ID
        val recyclerView = findViewById<RecyclerView>(R.id.rvTaulaEntrades)

        // Configurar el layout manager

        val layoutManager = GridLayoutManager(this, columnesRecyclerView)
        recyclerView.layoutManager = layoutManager

        // Crear un adaptador amb la llista d'esdeveniments i una funció de clic
        val adapter = TaulaEntradesAdapter(esdevenimentThis.entrades) { entrada ->
            // Gestiona el clic de l'esdeveniment
            val intent = Intent(this, Reservar::class.java).apply {
                putExtra("entrada", entrada)
                putExtra("esdeveniment", esdevenimentThis)
            }
            //startActivity(intent)
            getResult.launch(intent)
        }

        // Assignar l'adaptador al RecyclerView
        recyclerView.adapter = adapter*/

        val btnEnrere = findViewById<Button>(R.id.btnEnrere)
        btnEnrere.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }
    }

    private fun carregaViews() {
        if (resultatResult){
            esdevenimentThis = Esdeveniment_Manager.esdeveniments[JsonIO.cercarEsdeveniment(esdevenimentThis)]
        }
        val tvLocalitatsDisponibles = findViewById<TextView>(R.id.tvLocalitatsDisponibles)
        tvLocalitatsDisponibles.text = "Disponibles: " + GestorEntrades.entradesDisponiblesNumero(esdevenimentThis)

        val columnesRecyclerView = 1
        // Trobar el RecyclerView pel seu ID
        val recyclerView = findViewById<RecyclerView>(R.id.rvTaulaEntrades)
        // Configurar el layout manager
        val layoutManager = GridLayoutManager(this, columnesRecyclerView)
        recyclerView.layoutManager = layoutManager
        // Crear un adaptador amb la llista d'esdeveniments i una funció de clic
        val adapter = TaulaEntradesAdapter(esdevenimentThis.entrades) { entrada ->
            // Gestiona el clic de l'esdeveniment
            val intent = Intent(this, Reservar::class.java).apply {
                putExtra("entrada", entrada)
                putExtra("esdeveniment", esdevenimentThis)
            }
            //startActivity(intent)
            getResult.launch(intent)
        }
        // Assignar l'adaptador al RecyclerView
        recyclerView.adapter = adapter
    }
}