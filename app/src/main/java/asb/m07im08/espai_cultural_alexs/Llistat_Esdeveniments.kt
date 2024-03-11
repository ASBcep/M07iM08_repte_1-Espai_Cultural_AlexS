package asb.m07im08.espai_cultural_alexs

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDateTime

class Llistat_Esdeveniments : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_llistat_esdeveniments)

        val columnesRecyclerView = 1;

        ActualitzarLlistat(this)

        // Trobar el RecyclerView pel seu ID
        val recyclerView = findViewById<RecyclerView>(R.id.ListEsdeveniments)

        // Configurar el layout manager
        val layoutManager = GridLayoutManager(this, columnesRecyclerView)
        recyclerView.layoutManager = layoutManager

        // Crear un adaptador amb la llista d'esdeveniments i una funció de clic
        val adapter = EsdevenimentAdapter(Esdeveniment_Manager.esdeveniments) { esdeveniment, position ->
            // Gestiona el clic de l'esdeveniment
            Esdeveniment_Manager.index = position
            val intent = Intent(this, Detall::class.java)
            startActivity(intent)
        }

        // Assignar l'adaptador al RecyclerView
        recyclerView.adapter = adapter

        //obro gestió esdeveniment.
        val btnNou = findViewById<Button>(R.id.btnNou)
        btnNou.setOnClickListener {
            val intent = Intent(this, Gestio_Esdeveniment::class.java).apply {
                putExtra("detall", false)
                putExtra("nou", true)
                putExtra("modificar", false)
            }
            startActivity(intent)
        }
        //obro esdeveniments reservats.
        val btnReserves = findViewById<Button>(R.id.btnReserves)
        btnReserves.setOnClickListener {
            val intent = Intent(this, Reserves::class.java)
            startActivity(intent)
        }

    }
}