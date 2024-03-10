package asb.m07im08.espai_cultural_alexs

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView

class Detall : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_detall)
        val lvDetalls = findViewById<ListView>(R.id.lvDetalls)

        val headerView = layoutInflater.inflate(R.layout.textview_layout, null, false)
        val txtVwPersonalitzat = headerView.findViewById<TextView>(R.id.txtVwPersonalitzat)
        txtVwPersonalitzat.text = getString(R.string.descripcio_demo)

        // Afegir l'encapçalament abans de configurar l'adaptador
        lvDetalls.addHeaderView(headerView)

        val llistaDetalls = listOf("Element 1", "Element 2", "Element 3", "Element 4")

        val adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, llistaDetalls)
        lvDetalls.adapter = adaptador

        //obro gestió esdeveniment.
        val btnModificar = findViewById<Button>(R.id.btnModificar)
        btnModificar.setOnClickListener {
            val intent = Intent(this, Gestio_Esdeveniment::class.java)
            startActivity(intent)
        }
        //obro reservar numerada
        val btnReservar2 = findViewById<Button>(R.id.btnReservar2)
        btnReservar2.setOnClickListener {
            val intent = Intent(this, Reservar::class.java)
            startActivity(intent)
        }
        val btnEnrere = findViewById<Button>(R.id.btnEnrere)
        btnEnrere.setOnClickListener {
            finish()
        }
   }
}