package asb.m07im08.espai_cultural_alexs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView

class Gestio_Esdeveniment : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_gestio_esdeveniment)

        val rbPeli = findViewById<RadioButton>(R.id.rbPeli)
        val rbXerrada = findViewById<RadioButton>(R.id.rbXerrada)
        val rbConcert = findViewById<RadioButton>(R.id.rbConcert)
        val rgEsdeveniment = findViewById<RadioGroup>(R.id.rgEsdeveniment)

        /*
        val llEspecific1a3 = findViewById<LinearLayout>(R.id.llEspecific1a3)
        val llEspecificEntre3i4 = findViewById<LinearLayout>(R.id.llEspecificEntre3i4)
        val llEspecific4 = findViewById<LinearLayout>(R.id.llEspecific4)
        */
        val llEspecificDreta4 = findViewById<LinearLayout>(R.id.llEspecificDreta4)
        /*
        val tvEspecific1 = findViewById<TextView>(R.id.tvEspecific1)
        val tvEspecific2 = findViewById<TextView>(R.id.tvEspecific2)
        val tvEspecific3 = findViewById<TextView>(R.id.tvEspecific3)
        val tvEspecific4 = findViewById<TextView>(R.id.tvEspecific4)

        val etEspecific1 = findViewById<EditText>(R.id.etEspecific1)
        val etEspecific2 = findViewById<EditText>(R.id.etEspecific2)
        val etEspecific3 = findViewById<EditText>(R.id.etEspecific3)*/
        val etEspecific4 = findViewById<EditText>(R.id.etEspecific4)

        rgEsdeveniment.setOnCheckedChangeListener{_, isChecked ->
            amagarCampsEspecifics()
            if (rbPeli.isChecked){
                habilitarPeli()
            }
            if (rbXerrada.isChecked){
                habilitarXerrada()
            }
            if(rbConcert.isChecked){
                habilitarConcert()
            }
        }

        val llista = listOf("Llista específica Element 1", "Llista específica Element 2", "Llista específica Element 3", "Llista específica Element 4")
        val llistaEnString = llista.joinToString("\n") // Converteix la llista a una cadena de text, amb cada element en una nova línia

        etEspecific4.setText(llistaEnString)
        llEspecificDreta4.visibility = View.GONE

    }
    //funció per buidar camps específics
    private fun amagarCampsEspecifics(){
        //declaro Views

        val llEspecific1a3 = findViewById<LinearLayout>(R.id.llEspecific1a3)
        val llEspecificEntre3i4 = findViewById<LinearLayout>(R.id.llEspecificEntre3i4)
        val llEspecific4 = findViewById<LinearLayout>(R.id.llEspecific4)
        val llEspecificDreta4 = findViewById<LinearLayout>(R.id.llEspecificDreta4)

        val tvEspecific1 = findViewById<TextView>(R.id.tvEspecific1)
        val tvEspecific2 = findViewById<TextView>(R.id.tvEspecific2)
        val tvEspecific3 = findViewById<TextView>(R.id.tvEspecific3)
        val tvEspecific4 = findViewById<TextView>(R.id.tvEspecific4)

        val etEspecific1 = findViewById<EditText>(R.id.etEspecific1)
        val etEspecific2 = findViewById<EditText>(R.id.etEspecific2)
        val etEspecific3 = findViewById<EditText>(R.id.etEspecific3)
        val etEspecific4 = findViewById<EditText>(R.id.etEspecific4)

        //Amago els camps
        tvEspecific1.visibility = View.GONE;
        tvEspecific2.visibility = View.GONE;
        tvEspecific3.visibility = View.GONE;
        tvEspecific4.visibility = View.GONE;

        etEspecific1.visibility = View.GONE;
        etEspecific2.visibility = View.GONE;
        etEspecific3.visibility = View.GONE;
        etEspecific4.visibility = View.GONE;

        //Buido els camps
        tvEspecific1.text = null
        tvEspecific2.text = null
        tvEspecific3.text = null
        tvEspecific4.text = null

        etEspecific1.text = null
        etEspecific2.text = null
        etEspecific3.text = null
        etEspecific4.text = null

        //  Amago LinearLayouts
        llEspecific1a3.visibility = View.GONE
        llEspecificEntre3i4.visibility = View.GONE
        llEspecific4.visibility = View.GONE
        llEspecificDreta4.visibility = View.GONE
    }
    //funcions per habilitar camps específics
    private fun habilitarPeli(){
        //declaro Views

        val llEspecific1a3 = findViewById<LinearLayout>(R.id.llEspecific1a3)
        val llEspecificEntre3i4 = findViewById<LinearLayout>(R.id.llEspecificEntre3i4)
        val llEspecific4 = findViewById<LinearLayout>(R.id.llEspecific4)

        val tvEspecific1 = findViewById<TextView>(R.id.tvEspecific1)
        val tvEspecific2 = findViewById<TextView>(R.id.tvEspecific2)
        val tvEspecific3 = findViewById<TextView>(R.id.tvEspecific3)
        val tvEspecific4 = findViewById<TextView>(R.id.tvEspecific4)

        val etEspecific1 = findViewById<EditText>(R.id.etEspecific1)
        val etEspecific2 = findViewById<EditText>(R.id.etEspecific2)
        val etEspecific3 = findViewById<EditText>(R.id.etEspecific3)
        val etEspecific4 = findViewById<EditText>(R.id.etEspecific4)

        val titolEspecific1 = "Director:"
        val titolEspecific2 = "Any d'estrena:"
        val titolEspecific3 = "Durada:"
        val titolEspecific4 = "Actors principals:"

        val director = "Frenando Prueba"
        val any = 1999
        val duracio = 111
        val duracioString = duracio.toString() + " minuts"
        val llista = listOf("Kenoa Ribes","Catering Zeta Joan","Garrison Porc")
        val llistaEnString = llista.joinToString("\n") // Converteix la llista a una cadena de text, amb cada element en una nova línia

        llEspecific1a3.visibility = View.VISIBLE
        llEspecificEntre3i4.visibility = View.VISIBLE
        llEspecific4.visibility = View.VISIBLE

        tvEspecific1.visibility = View.VISIBLE
        tvEspecific1.text = titolEspecific1

        etEspecific1.visibility = View.VISIBLE
        etEspecific1.text = Editable.Factory.getInstance().newEditable(director)

        tvEspecific2.visibility = View.VISIBLE
        tvEspecific2.text = titolEspecific2

        etEspecific2.visibility = View.VISIBLE
        etEspecific2.text = Editable.Factory.getInstance().newEditable(any.toString())

        tvEspecific3.visibility = View.VISIBLE
        tvEspecific3.text = titolEspecific3

        etEspecific3.visibility = View.VISIBLE
        etEspecific3.text = Editable.Factory.getInstance().newEditable(duracioString)

        tvEspecific4.visibility = View.VISIBLE
        tvEspecific4.text = titolEspecific4

        etEspecific4.visibility = View.VISIBLE
        etEspecific4.text = Editable.Factory.getInstance().newEditable(llistaEnString)
    }

    //reprogramar textos i variables com a habilitarPeli()
    private fun habilitarXerrada(){
        //declaro Views
        val llEspecific4 = findViewById<LinearLayout>(R.id.llEspecific4)
        val llEspecificDreta4 = findViewById<LinearLayout>(R.id.llEspecificDreta4)

        val tvEspecific4 = findViewById<TextView>(R.id.tvEspecific4)

        val etEspecific4 = findViewById<EditText>(R.id.etEspecific4)


        llEspecific4.visibility = View.VISIBLE
        llEspecificDreta4.visibility = View.VISIBLE

        tvEspecific4.visibility = View.VISIBLE
        tvEspecific4.text = "Llistat de participants:"

        etEspecific4.visibility = View.VISIBLE
        etEspecific4.text = Editable.Factory.getInstance().newEditable("Alex Bolea\nMarcos Garcia\nLuis José")
    }

    //reprogramar textos i variables com a habilitarPeli()
    private fun habilitarConcert() {
        //declaro Views

        val llEspecific1a3 = findViewById<LinearLayout>(R.id.llEspecific1a3)
        val llEspecificEntre3i4 = findViewById<LinearLayout>(R.id.llEspecificEntre3i4)
        val llEspecific4 = findViewById<LinearLayout>(R.id.llEspecific4)

        val tvEspecific1 = findViewById<TextView>(R.id.tvEspecific1)
        val tvEspecific2 = findViewById<TextView>(R.id.tvEspecific2)
        val tvEspecific3 = findViewById<TextView>(R.id.tvEspecific3)
        val tvEspecific4 = findViewById<TextView>(R.id.tvEspecific4)

        val etEspecific1 = findViewById<EditText>(R.id.etEspecific1)
        val etEspecific2 = findViewById<EditText>(R.id.etEspecific2)
        val etEspecific3 = findViewById<EditText>(R.id.etEspecific3)
        val etEspecific4 = findViewById<EditText>(R.id.etEspecific4)


        llEspecific1a3.visibility = View.VISIBLE
        llEspecificEntre3i4.visibility = View.VISIBLE
        llEspecific4.visibility = View.VISIBLE

        tvEspecific1.visibility = View.VISIBLE
        tvEspecific1.text = "Compositor:"

        etEspecific1.visibility = View.VISIBLE
        etEspecific1.text = Editable.Factory.getInstance().newEditable("Joan Guillems")

        tvEspecific2.visibility = View.VISIBLE
        tvEspecific2.text = "Cor:"

        etEspecific2.visibility = View.VISIBLE
        etEspecific2.text = Editable.Factory.getInstance().newEditable("Orfeó Ca Talà")

        tvEspecific3.visibility = View.VISIBLE
        tvEspecific3.text = "Orquestra:"

        etEspecific3.visibility = View.VISIBLE
        etEspecific3.text = Editable.Factory.getInstance().newEditable("Orquesta Simfònica del Va Llest")

        tvEspecific4.visibility = View.VISIBLE
        tvEspecific4.text = "Solistes principals:"

        etEspecific4.visibility = View.VISIBLE
        etEspecific4.text = Editable.Factory.getInstance().newEditable("Josep Violante\nAnna Guitarrín\nMariví Piana\nGuillermina Flautènsia\nRamon Tambort")
    }

}