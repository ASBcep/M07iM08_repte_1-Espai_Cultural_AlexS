package asb.m07im08.espai_cultural_alexs

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView

class Gestio_Esdeveniment : AppCompatActivity() {
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_gestio_esdeveniment)
        val lvDetalls = findViewById<ListView>(R.id.lvDetalls)

        val headerView = layoutInflater.inflate(R.layout.textview_layout, null, false)
        val TxtVwPersonalitzat = headerView.findViewById<TextView>(R.id.TxtVwPersonalitzat)
        TxtVwPersonalitzat.text = getString(R.string.descripcio_demo)

        // Afegir l'encapçalament abans de configurar l'adaptador
        lvDetalls.addHeaderView(headerView)

        val llistaDetalls = listOf("Element 1", "Element 2", "Element 3", "Element 4")

        val adaptador = ArrayAdapter(this, android.R.layout.simple_list_item_1, llistaDetalls)
        lvDetalls.adapter = adaptador

    }
}