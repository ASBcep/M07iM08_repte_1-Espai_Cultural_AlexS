package asb.m07im08.espai_cultural_alexs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Esdeveniments_Reservats : AppCompatActivity() {//SENSE ÚS
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_esdeveniments_reservats)

        var index = 0
        val columnesRecyclerView = 1
        //val esdeveniments = Esdeveniment_Manager.esdeveniments
        val esdevenimentsReservats = mutableListOf<(Esdeveniment)>()
        for (Esdeveniment in Esdeveniment_Manager.esdeveniments){
            if (Esdeveniment.entrades.count() > 0){
                esdevenimentsReservats.add(Esdeveniment)
            }
        }

        // Trobar el RecyclerView pel seu ID
        val recyclerView = findViewById<RecyclerView>(R.id.rvEsdevenimentsReservats)

        // Configurar el layout manager

        val layoutManager = GridLayoutManager(this, columnesRecyclerView)
        recyclerView.layoutManager = layoutManager

        // Crear un adaptador amb la llista d'esdeveniments i una funció de clic
        val adapter = EsdevenimentAdapter(esdevenimentsReservats) { esdeveniment, position ->
            // Gestiona el clic de l'esdeveniment
            Esdeveniment_Manager.index = position
            val intent = Intent(this, Reserves::class.java)
            startActivity(intent)
        }

        // Assignar l'adaptador al RecyclerView
        recyclerView.adapter = adapter

        //obro gestió esdeveniment.
        val btnEnrere = findViewById<Button>(R.id.btnEnrere)
        btnEnrere.setOnClickListener {
            finish()
        }
    }
}