package asb.m07im08.espai_cultural_alexs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Llistat_Esdeveniments : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_llistat_esdeveniments)

        // Crear una llista d'exemple d'esdeveniments (substitueix-ho per la teva llista real)
        val esdeveniments = listOf(
            Esdeveniment(1, "Nom Esdeveniment 1", "imatge1.jpg", "Descripció esdeveniment 1", 10.5f),
            Esdeveniment(2, "Nom Esdeveniment 2", "imatge2.jpg", "Descripció esdeveniment 2", 15.0f)
            // Afegir més esdeveniments si cal
        )

        // Trobar el RecyclerView pel seu ID
        val recyclerView = findViewById<RecyclerView>(R.id.ListEsdeveniments)

        // Configurar el layout manager amb 3 columnes
        val layoutManager = GridLayoutManager(this, 3)
        recyclerView.layoutManager = layoutManager

        // Crear un adaptador amb la llista d'esdeveniments i una funció de clic
        val adapter = EsdevenimentAdapter(esdeveniments) { esdeveniment ->
            // Gestiona el clic de l'esdeveniment (pots implementar això segons les teves necessitats)
        }

        // Assignar l'adaptador al RecyclerView
        recyclerView.adapter = adapter
    }
}


/*package asb.m07im08.espai_cultural_alexs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

class Llistat_Esdeveniments : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_llistat_esdeveniments)

        val listEsdeveniments = findViewById<RecyclerView>(R.id.ListEsdeveniments)
        val layoutManager = GridLayoutManager(this, 3)
        listEsdeveniments.layoutManager = layoutManager

        listEsdeveniments.adapter = adapter
    }
}*/