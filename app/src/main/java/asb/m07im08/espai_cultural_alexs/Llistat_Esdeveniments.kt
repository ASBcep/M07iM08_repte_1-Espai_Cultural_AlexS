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
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_llistat_esdeveniments)

        val columnesRecyclerView = 1;

        //falta funció per cridar a lectura json en una classe a banda, i si no troba el json que ompli la llista.

        // Crear una llista d'exemple d'esdeveniments (substitueix-ho per la teva llista real)
        /*var esdeveniments = mutableListOf<Esdeveniment>(
            Esdeveniment(1, "Nom Esdeveniment 1", "imatge1.jpg", "Descripció esdeveniment 1",LocalDateTime.now(),10.5f,false,"Pel·lícula",
                MutableList(Esdeveniment_Manager.aforament) { _ -> Entrada(0,"Alexito Numerario") },"Frenando Prueba",1999.toString(),111.toString(),MutableList<String>("Kenoa Ribes","Catering Zeta Joan","Garrison Porc"),),
            Esdeveniment(2, "Nom Esdeveniment 2", "imatge2.jpg", "Descripció esdeveniment 2",LocalDateTime.of(2024,3,30,15,30),15.0f),
            Esdeveniment(3, "Nom Esdeveniment 3", "imatge2.jpg", "Descripció esdeveniment 3",LocalDateTime.of(2024,5,2,21,0),15.0f),
            Esdeveniment(4, "Nom Esdeveniment 4", "imatge2.jpg", "Descripció esdeveniment 4",LocalDateTime.of(2024,8,5,19,45),15.0f)
        )*/
        // Creació de la llista mutable d'Esdeveniments
        var esdeveniments = mutableListOf<Esdeveniment>(
            Esdeveniment(
                id = 1,
                nom = "Peli Esdeveniment 1",
                image = "imatge1.jpg",
                descripcio = "Descripció esdeveniment 1",
                data = LocalDateTime.now(),
                preu = 10.5f,
                numerat = false,
                tipus = "Pel·lícula",
                //entrades = MutableList(Esdeveniment_Manager.aforament) { _ -> Entrada(0, "Alexito Numerario") },
                entrades = MutableList(Esdeveniment_Manager.aforament) { index -> Entrada(index, "Alexito Numerario") },
                especific1 = "Frenando Prueba",
                especific2 = 1999.toString(),
                especific3 = 111.toString(),
                especific4 = mutableListOf("Kenoa Ribes", "Catering Zeta Joan", "Garrison Porc")
            ),
            Esdeveniment(
                id = 2,
                nom = "Xerrada Esdeveniment 2",
                image = "imatge1.jpg",
                descripcio = "Descripció esdeveniment 2",
                data = LocalDateTime.now(),
                preu = 10.5f,
                numerat = false,
                tipus = "Xerrada",
                entrades = MutableList(Esdeveniment_Manager.aforament) { _ -> Entrada(0, "Alexito Numerista") },
                especific1 = "",
                especific2 = "",
                especific3 = "",
                especific4 = mutableListOf("Alex Bolea", "Marcos Garcia", "Luis José")
            ),
            Esdeveniment(
                id = 3,
                nom = "Concert Esdeveniment 3",
                image = "imatge1.jpg",
                descripcio = "Descripció esdeveniment 3",
                data = LocalDateTime.now(),
                preu = 10.5f,
                numerat = false,
                tipus = "Pel·lícula",
                entrades = MutableList(Esdeveniment_Manager.aforament) { _ -> Entrada(0, "Alexito Numeroso") },
                especific1 = "Joan Guillems",
                especific2 = "Orfeó Ca Talà",
                especific3 = "Orquesta Simfònica del Va Llest",
                especific4 = mutableListOf("Josep Violante", "Anna Guitarrín", "Mariví Piana", "Guillermina Flautènsia", "Ramon Tambort")
            ),
            Esdeveniment(
                id = 4,
                nom = "Nom Esdeveniment 4 sense entrades reservades",
                image = "imatge1.jpg",
                descripcio = "Descripció esdeveniment 4",
                data = LocalDateTime.now(),
                preu = 10.5f,
                numerat = false,
                tipus = "Pel·lícula",
                entrades = MutableList(Esdeveniment_Manager.aforament) { _ -> Entrada(0, "Alexito Numercio") },
                especific1 = "Frenando Prueba",
                especific2 = 1999.toString(),
                especific3 = 111.toString(),
                especific4 = mutableListOf("Kenoa Ribes", "Catering Zeta Joan", "Garrison Porc")
            ),

        )

        Esdeveniment_Manager.esdeveniments.clear()
        Esdeveniment_Manager.esdeveniments.addAll(esdeveniments)

        val esdevenimentsJson = ActualitzarLlistat(this);

        // Trobar el RecyclerView pel seu ID
        val recyclerView = findViewById<RecyclerView>(R.id.ListEsdeveniments)

        // Configurar el layout manager
        val layoutManager = GridLayoutManager(this, columnesRecyclerView)
        recyclerView.layoutManager = layoutManager

        // Crear un adaptador amb la llista d'esdeveniments i una funció de clic
        val adapter = EsdevenimentAdapter(esdeveniments) { esdeveniment ->
            // Gestiona el clic de l'esdeveniment

            val intent = Intent(this, Detall::class.java)
            startActivity(intent)
        }

        // Assignar l'adaptador al RecyclerView
        recyclerView.adapter = adapter

        //obro gestió esdeveniment.
        val btnNou = findViewById<Button>(R.id.btnNou)
        btnNou.setOnClickListener {
            val intent = Intent(this, Gestio_Esdeveniment::class.java)
            startActivity(intent)
        }
        //obro esdeveniments reservats.
        val btnReserves = findViewById<Button>(R.id.btnReserves)
        btnReserves.setOnClickListener {
            //val intent = Intent(this, Reserves::class.java)
            val intent = Intent(this, Esdeveniments_Reservats::class.java)
            startActivity(intent)
        }
        /*//obro reserves.
        val btnReserves = findViewById<Button>(R.id.btnReserves)
        btnReserves.setOnClickListener {
            val intent = Intent(this, Reserves::class.java)
            startActivity(intent)
        }*/


    }
}