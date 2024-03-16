package asb.m07im08.espai_cultural_alexs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Reserves : AppCompatActivity() {
    private var esdeveniment: Esdeveniment = Esdeveniment()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserves)

        //rebo putextra de l'intent
        //esdeveniment = intent.getSerializableExtra("esdeveniment", Esdeveniment)
        esdeveniment = intent.getSerializableExtra("esdeveniment") as Esdeveniment? ?: Esdeveniment()

        val tvTitol = findViewById<TextView>(R.id.tvTitol)
        tvTitol.text = "Gestionar entrades de " + esdeveniment.nom

        val ivHR = findViewById<ImageView>(R.id.ivHR)
        GestorImatge.inserirImatgeHR(esdeveniment.imatge, this, ivHR)


        var index = 0
        val columnesRecyclerView = 1

        // Trobar el RecyclerView pel seu ID
        val recyclerView = findViewById<RecyclerView>(R.id.rvTaulaEntrades)

        // Configurar el layout manager

        val layoutManager = GridLayoutManager(this, columnesRecyclerView)
        recyclerView.layoutManager = layoutManager

        // Crear un adaptador amb la llista d'esdeveniments i una funció de clic
        val adapter = TaulaEntradesAdapter(esdeveniment.entrades) { entrada ->
            // Gestiona el clic de l'esdeveniment
            //obrir popup per modificar entrada??
            val intent = Intent(this, Reservar::class.java).apply {
                putExtra("entrada", entrada)
            }
            startActivity(intent)
        }

        // Assignar l'adaptador al RecyclerView
        recyclerView.adapter = adapter

        val btnEnrere = findViewById<Button>(R.id.btnEnrere)
        btnEnrere.setOnClickListener {
            finish()
        }
    }
}