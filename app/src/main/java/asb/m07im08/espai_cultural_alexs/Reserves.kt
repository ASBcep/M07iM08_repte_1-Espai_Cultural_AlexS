package asb.m07im08.espai_cultural_alexs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Reserves : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_reserves)


        var index = 0
        val columnesRecyclerView = 1

        // Trobar el RecyclerView pel seu ID
        val recyclerView = findViewById<RecyclerView>(R.id.rvTaulaEntrades)

        // Configurar el layout manager

        val layoutManager = GridLayoutManager(this, columnesRecyclerView)
        recyclerView.layoutManager = layoutManager

        // Crear un adaptador amb la llista d'esdeveniments i una funció de clic
        val adapter = TaulaEntradesAdapter(Esdeveniment_Manager.esdeveniments[index].entrades) { entrada ->
            // Gestiona el clic de l'esdeveniment
            //obrir popup per modificar entrada??
        }

        // Assignar l'adaptador al RecyclerView
        recyclerView.adapter = adapter

        val btnEnrereRsrvNum = findViewById<Button>(R.id.btnEnrereRsrvNum)
        btnEnrereRsrvNum.setOnClickListener {
            finish()
        }
    }
}