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
                //Toast.makeText(this, "Reserves modificades", Toast.LENGTH_SHORT).show()
                resultatResult = true
                reserves = false
                //recreate()
                carregaViews()
            } else {
                resultatResult = true
                reserves = true
                //recreate()
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


/*
        if (reserves) {
            val tvTitol = findViewById<TextView>(R.id.tvTitol)
            tvTitol.text = "Esdeveniments reservats"
            btnEnrere.setPadding(0,0,0,0)
            btnReserves.visibility = View.GONE
            btnNou.visibility = View.GONE

        } else {
            btnEnrere.visibility = View.GONE
        }
*/
        carregaViews()

        //ActualitzarLlistat(this, reserves)
        //JsonIO.llegirLlistat(this, reserves)

        esdeveniments = Esdeveniment_Manager.esdeveniments

        if (esdeveniments.isEmpty()){
            Toast.makeText(this, "llistat d'esdeveniments buit", Toast.LENGTH_SHORT).show()
        }
/*
        val columnesRecyclerView = 1;
        // Trobar el RecyclerView pel seu ID
        val recyclerView = findViewById<RecyclerView>(R.id.ListEsdeveniments)

        // Configurar el layout manager
        val layoutManager = GridLayoutManager(this, columnesRecyclerView)
        recyclerView.layoutManager = layoutManager

        // Crear un adaptador amb la llista d'esdeveniments i una funció de clic
        //val adapter = EsdevenimentAdapter(Esdeveniment_Manager.esdeveniments) { esdeveniment, position ->
        val adapter = EsdevenimentAdapter(esdeveniments) { esdeveniment ->

        // Gestiona el clic de l'esdeveniment
            //Esdeveniment_Manager.index = position
            //val intent = Intent(this, Detall::class.java)//canvio la classe
            val intent: Intent
            if (reserves) {
                intent = Intent(this, Reserves::class.java).apply {
                    putExtra("esdeveniment", esdeveniment)
                }
            } else {
                intent = Intent(this, Gestio_Esdeveniment::class.java).apply {
                    putExtra("esdeveniment", esdeveniment)
                    putExtra("detall", true)
                    //putExtra("nou", true)
                    //putExtra("modificar", false)
                }
            }
            //startActivity(intent)
            getResult.launch(intent)
            JsonIO.llegirLlistat(this, reserves)
        }

        // Assignar l'adaptador al RecyclerView
        recyclerView.adapter = adapter
*/


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
        //val adapter = EsdevenimentAdapter(Esdeveniment_Manager.esdeveniments) { esdeveniment, position ->
        val adapter = EsdevenimentAdapter(esdeveniments) { esdeveniment ->

            // Gestiona el clic de l'esdeveniment
            //Esdeveniment_Manager.index = position
            //val intent = Intent(this, Detall::class.java)//canvio la classe
            val intent: Intent
            if (reserves) {
                intent = Intent(this, Reserves::class.java).apply {
                    putExtra("esdeveniment", esdeveniment)
                }
            } else {
                intent = Intent(this, Gestio_Esdeveniment::class.java).apply {
                    putExtra("esdeveniment", esdeveniment)
                    putExtra("detall", true)
                    //putExtra("nou", true)
                    //putExtra("modificar", false)
                }
            }
            //startActivity(intent)
            getResult.launch(intent)
            JsonIO.llegirLlistat(this, reserves)
        }

        // Assignar l'adaptador al RecyclerView
        recyclerView.adapter = adapter
    }
}