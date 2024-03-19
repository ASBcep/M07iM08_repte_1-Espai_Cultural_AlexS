package asb.m07im08.espai_cultural_alexs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Llistat_Esdeveniments : AppCompatActivity() {
private var reserves: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_llistat_esdeveniments)

        //rebo putextra de l'intent
        reserves = intent.getBooleanExtra("reserves", false)

        val btnEnrere = findViewById<Button>(R.id.btnEnrere)
        val btnReserves = findViewById<Button>(R.id.btnReserves)
        val btnNou = findViewById<Button>(R.id.btnNou)



        if (reserves) {
            val tvTitol = findViewById<TextView>(R.id.tvTitol)
            tvTitol.text = "Esdeveniments reservats"
            btnEnrere.setPadding(0,0,0,0)
            btnReserves.visibility = View.GONE
            btnNou.visibility = View.GONE

        } else {
            btnEnrere.visibility = View.GONE
        }
        val columnesRecyclerView = 1;

        //ActualitzarLlistat(this, reserves)
        JsonIO.llegirLlistat(this, reserves)

        val esdeveniments = Esdeveniment_Manager.esdeveniments

        if (esdeveniments.isEmpty()){
            Toast.makeText(this, "llistat d'esdeveniments buit", Toast.LENGTH_SHORT).show()
        }

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
            startActivity(intent)
        }

        // Assignar l'adaptador al RecyclerView
        recyclerView.adapter = adapter

        btnEnrere.setOnClickListener {
            finish()
        }
        //obro esdeveniments reservats.
        btnReserves.setOnClickListener {
            //val intent = Intent(this, Reserves::class.java)
            //val intent = Intent(this, Esdeveniments_Reservats::class.java)
            val intent = Intent(this, Llistat_Esdeveniments::class.java)
            intent.putExtra("reserves", true)
            startActivity(intent)
        }
        //obro llista d'esdeveniments reservats (aquesta mateixa activity amb altres paràmetres)
        btnNou.setOnClickListener {
            val intent = Intent(this, Gestio_Esdeveniment::class.java).apply {
                //putExtra("detall", false)
                putExtra("nou", true)
                //putExtra("modificar", false)
            }
            startActivity(intent)
        }

    }
}