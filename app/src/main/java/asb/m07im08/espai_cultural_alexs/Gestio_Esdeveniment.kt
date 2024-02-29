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

        val llEspecific1a3 = findViewById<LinearLayout>(R.id.llEspecific1a3)
        //val llEspecific2 = findViewById<LinearLayout>(R.id.llEspecific2)
        //val llEspecific3 = findViewById<LinearLayout>(R.id.llEspecific3)
        val llEspecific4 = findViewById<LinearLayout>(R.id.llEspecific4)

        val tvEspecific1 = findViewById<TextView>(R.id.tvEspecific1)
        val tvEspecific2 = findViewById<TextView>(R.id.tvEspecific2)
        val tvEspecific3 = findViewById<TextView>(R.id.tvEspecific3)
        val tvEspecific4 = findViewById<TextView>(R.id.tvEspecific4)

        val etEspecific1 = findViewById<EditText>(R.id.etEspecific1)
        val etEspecific2 = findViewById<EditText>(R.id.etEspecific2)
        val etEspecific3 = findViewById<EditText>(R.id.etEspecific3)
        val etEspecific4 = findViewById<EditText>(R.id.etEspecific4)

        fun amagarCampsEspecifics(){
            tvEspecific1.visibility = View.INVISIBLE;
            tvEspecific2.visibility = View.INVISIBLE;
            tvEspecific3.visibility = View.INVISIBLE;
            tvEspecific4.visibility = View.INVISIBLE;

            etEspecific1.visibility = View.INVISIBLE;
            etEspecific2.visibility = View.INVISIBLE;
            etEspecific3.visibility = View.INVISIBLE;
            etEspecific4.visibility = View.INVISIBLE;

            /*// Deshabilita les vistes
            tvEspecific1.isEnabled = false
            tvEspecific2.isEnabled = false
            tvEspecific3.isEnabled = false
            tvEspecific4.isEnabled = false

            etEspecific1.isEnabled = false
            etEspecific2.isEnabled = false
            etEspecific3.isEnabled = false
            etEspecific4.isEnabled = false*/

            //  PROVAR A DESHABILITAR LINEARLAYOUT
            llEspecific1a3.isEnabled = false
            llEspecific4.isEnabled = false
        }
        rgEsdeveniment.setOnCheckedChangeListener{_, isChecked ->
            amagarCampsEspecifics()
            if (rbPeli.isChecked){
                llEspecific4.isEnabled = true
                //tvEspecific4.isEnabled = true
                tvEspecific4.visibility = View.VISIBLE

                //etEspecific4.isEnabled = true
                etEspecific4.visibility = View.VISIBLE

                tvEspecific4.text = "llistat d\'actors"
                etEspecific4.text = Editable.Factory.getInstance().newEditable("Catering Zeta Jones\nGarrison Fork")
            }
            if (rbXerrada.isChecked){
                llEspecific1a3.isEnabled = true
                //tvEspecific1.isEnabled = true
                tvEspecific1.visibility = View.VISIBLE

                //etEspecific1.isEnabled = true
                etEspecific1.visibility = View.VISIBLE

                tvEspecific1.text = "Nom del debat:"
                etEspecific1.text = Editable.Factory.getInstance().newEditable("Israel: Jueus nazis?")
            }
        }

        /*rbPeli.setOnCheckedChangeListener{ _, isChecked ->
            amagarCampsEspecifics()
            if (rbPeli.isChecked){
                //tvEspecific4.isEnabled = true
                tvEspecific4.visibility = View.VISIBLE

                //etEspecific4.isEnabled = true
                etEspecific4.visibility = View.VISIBLE

                tvEspecific4.text = "llistat d\'actors"
                etEspecific4.text = Editable.Factory.getInstance().newEditable("Catering Zeta Jones\nGarrison Fork")
            }
        }*/
        /*rbXerrada.setOnCheckedChangeListener{ _, isChecked ->
            amagarCampsEspecifics()
            if (rbXerrada.isChecked){
                //tvEspecific1.isEnabled = true
                tvEspecific1.visibility = View.VISIBLE

                //etEspecific1.isEnabled = true
                etEspecific1.visibility = View.VISIBLE

                tvEspecific1.text = "Nom del debat:"
                etEspecific1.text = Editable.Factory.getInstance().newEditable("Israel: Jueus nazis?")
            }
        }*/

        val llista = listOf("Llista específica Element 1", "Llista específica Element 2", "Llista específica Element 3", "Llista específica Element 4")
        val llistaComText = llista.joinToString("\n") // Converteix la llista a una cadena de text, amb cada element en una nova línia

        etEspecific4.setText(llistaComText)



    }
}