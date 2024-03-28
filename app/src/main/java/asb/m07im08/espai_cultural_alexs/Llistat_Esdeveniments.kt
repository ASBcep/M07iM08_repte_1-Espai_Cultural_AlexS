package asb.m07im08.espai_cultural_alexs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Llistat_Esdeveniments : AppCompatActivity() {
    private var reserves = false
    private lateinit var esdeveniments: MutableList<Esdeveniment>
    //private lateinit var adapter: EsdevenimentAdapter
    private var resultatResult = false
    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult())
        {
            if(it.resultCode == RESULT_OK) {
                resultatResult = true
                reserves = false
                carregaViews()
            } else {
                resultatResult = true
                reserves = true
                carregaViews()
            }
        }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_llistat_esdeveniments)

        if (!resultatResult) {
            //rebo putextra de l'intent
            reserves = intent.getBooleanExtra("reserves", false)
        }

        val btnEnrere = findViewById<Button>(R.id.btnEnrere)
        val btnReserves = findViewById<Button>(R.id.btnReserves)
        val btnNou = findViewById<Button>(R.id.btnNou)

        carregaViews()

        esdeveniments = Esdeveniment_Manager.esdeveniments

        if (esdeveniments.isEmpty()){
            Toast.makeText(this, "llistat d'esdeveniments buit", Toast.LENGTH_SHORT).show()
        }

        btnEnrere.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }
        //obro esdeveniments reservats.
        btnReserves.setOnClickListener {
            //val intent = Intent(this, Reserves::class.java)
            //val intent = Intent(this, Esdeveniments_Reservats::class.java)
            val intent = Intent(this, Llistat_Esdeveniments::class.java)
            intent.putExtra("reserves", true)
            //startActivity(intent)
            getResult.launch(intent)
        }
        //obro llista d'esdeveniments reservats (aquesta mateixa activity amb altres paràmetres)
        btnNou.setOnClickListener {
            val intent = Intent(this, Gestio_Esdeveniment::class.java).apply {
                //putExtra("detall", false)
                putExtra("nou", true)
                //putExtra("modificar", false)
            }
            //startActivity(intent)
            getResult.launch(intent)
        }
    }
    private fun carregaViews() {
        val btnEnrere = findViewById<Button>(R.id.btnEnrere)
        val btnReserves = findViewById<Button>(R.id.btnReserves)
        val btnNou = findViewById<Button>(R.id.btnNou)

        btnEnrere.visibility = View.VISIBLE
        btnReserves.visibility = View.VISIBLE
        btnNou.visibility = View.VISIBLE

        //ActualitzarLlistat(this, reserves)
        JsonIO.llegirLlistat(this, reserves)

        if (reserves) {
            val tvTitol = findViewById<TextView>(R.id.tvTitol)
            tvTitol.text = "Esdeveniments reservats"
            //btnEnrere.setPadding(0,0,0,0)
            btnReserves.visibility = View.GONE
            btnNou.visibility = View.GONE
        } else {
            btnEnrere.visibility = View.GONE
        }

        val columnesRecyclerView = 1;
        // Trobar el RecyclerView pel seu ID
        esdeveniments = Esdeveniment_Manager.esdeveniments
        val recyclerView = findViewById<RecyclerView>(R.id.ListEsdeveniments)

        // Configurar el layout manager
        val layoutManager = GridLayoutManager(this, columnesRecyclerView)
        recyclerView.layoutManager = layoutManager

        // Crear un adaptador amb la llista d'esdeveniments i una funció de clic
        val adapter = EsdevenimentAdapter(esdeveniments,
        //adapter = EsdevenimentAdapter(esdeveniments,
            // Funció per al clic normal
            { esdeveniment ->
                // Gestiona el clic de l'esdeveniment
                val intent: Intent = if (reserves) {
                    Intent(this, Reserves::class.java).apply {
                        putExtra("esdeveniment", esdeveniment)
                    }
                } else {
                    Intent(this, Gestio_Esdeveniment::class.java).apply {
                        putExtra("esdeveniment", esdeveniment)
                        putExtra("detall", true)
                    }
                }
                getResult.launch(intent)
                JsonIO.llegirLlistat(this, reserves)
            },
            // Funció per al clic llarg
            { esdeveniment ->
                // Gestiona el clic llarg de l'esdeveniment aquí
                val intent: Intent = if (reserves) {
                    Intent(this, Reserves::class.java).apply {
                        putExtra("esdeveniment", esdeveniment)
                    }
                } else {
                    Intent(this, Gestio_Esdeveniment::class.java).apply {
                        putExtra("esdeveniment", esdeveniment)
                        putExtra("modificar", true)
                    }
                }
                if (!reserves) {
                    Toast.makeText(this, "Modificar esdeveniment", Toast.LENGTH_SHORT).show()
                }
                getResult.launch(intent)
                JsonIO.llegirLlistat(this, reserves)
                true // Indica que s'ha gestionat el clic llarg
            }
        )
        // Assignar l'adaptador al RecyclerView
        recyclerView.adapter = adapter
    }
}