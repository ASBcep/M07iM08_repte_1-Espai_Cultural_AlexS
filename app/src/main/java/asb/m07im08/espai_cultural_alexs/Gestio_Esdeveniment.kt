package asb.m07im08.espai_cultural_alexs

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.icu.util.Calendar
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.TimePicker
import android.widget.Toast
import java.time.LocalDateTime
import java.time.Month
import java.time.MonthDay
import java.time.Year
import java.util.Date

class Gestio_Esdeveniment : AppCompatActivity() {
    private var detall: Boolean = false
    private var nou: Boolean = false
    private var modificar: Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //rebo putextra de l'intent
        detall = intent.getBooleanExtra("detall", false)
        nou = intent.getBooleanExtra("nou", false)
        modificar = intent.getBooleanExtra("modificar", false)

        setContentView(R.layout.activity_gestio_esdeveniment)

        var esdevenimentThis = llegirEsdeveniment()

        val tvTitol = findViewById<TextView>(R.id.tvTitol)
        val etTitol = findViewById<EditText>(R.id.etTitol)

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

        val ivCalendari = findViewById<ImageView>(R.id.ivCalendari)
        val etDia = findViewById<EditText>(R.id.etDia)
        val etMes = findViewById<EditText>(R.id.etMes)
        val etAny = findViewById<EditText>(R.id.etAny)
        val etHora = findViewById<EditText>(R.id.etHora)
        val etMinuts = findViewById<EditText>(R.id.etMinuts)

        val etIdioma = findViewById<EditText>(R.id.etIdioma)
        val etPreu = findViewById<EditText>(R.id.etPreu)

        val btnModifiCrear = findViewById<Button>(R.id.btnModifiCrear)
        val btnEliminar = findViewById<Button>(R.id.btnEliminar)

        ivCalendari.visibility = View.GONE
        etIdioma.text = Editable.Factory.getInstance().newEditable(esdevenimentThis.idioma)
        etPreu.text = Editable.Factory.getInstance().newEditable(esdevenimentThis.preu.toString())

        val mesFormatat = String.format("%02d", esdevenimentThis.data.monthValue)

        if (detall){
            tvTitol.visibility = View.GONE
            etTitol.isEnabled = false
            etTitol.text = Editable.Factory.getInstance().newEditable(esdevenimentThis.nom)
            etAny.text = Editable.Factory.getInstance().newEditable(esdevenimentThis.data.year.toString())
            etAny.isEnabled = false
            //etMes.text = Editable.Factory.getInstance().newEditable(esdevenimentThis.data.month.toString())
            etMes.text = Editable.Factory.getInstance().newEditable(mesFormatat)
            etMes.isEnabled = false
            etDia.text = Editable.Factory.getInstance().newEditable(esdevenimentThis.data.dayOfMonth.toString())
            etDia.isEnabled = false
            etHora.text = Editable.Factory.getInstance().newEditable(esdevenimentThis.data.hour.toString())
            etHora.isEnabled = false
            etMinuts.text = Editable.Factory.getInstance().newEditable(esdevenimentThis.data.minute.toString())
            etMinuts.isEnabled = false
            rgEsdeveniment.visibility = View.GONE
            if (esdevenimentThis.tipus == "Pel·lícula"){
                rbPeli.isChecked = true
            } else if (esdevenimentThis.tipus == "Xerrada"){
                rbXerrada.isChecked = true
            } else if (esdevenimentThis.tipus == "Concert") {
                rbConcert.isChecked = true
            }
            btnModifiCrear.text = "Modificar"
            btnEliminar.visibility = View.GONE
            ivCalendari.visibility = View.GONE
        }
        if (nou){
            rgEsdeveniment.visibility = View.VISIBLE
            btnModifiCrear.text = "Crear"
            btnEliminar.visibility = View.GONE
        }
        if (modificar){
            etAny.text = Editable.Factory.getInstance().newEditable(esdevenimentThis.data.year.toString())
            //etMes.text = Editable.Factory.getInstance().newEditable(esdevenimentThis.data.month.toString())
            etMes.text = Editable.Factory.getInstance().newEditable(mesFormatat)
            etDia.text = Editable.Factory.getInstance().newEditable(esdevenimentThis.data.dayOfMonth.toString())
            etHora.text = Editable.Factory.getInstance().newEditable(esdevenimentThis.data.hour.toString())
            etMinuts.text = Editable.Factory.getInstance().newEditable(esdevenimentThis.data.minute.toString())
            etTitol.text = Editable.Factory.getInstance().newEditable(esdevenimentThis.nom)
            rgEsdeveniment.visibility = View.GONE
            btnModifiCrear.text = "Desar canvis"

        }

        rgEsdeveniment.setOnCheckedChangeListener{_, isChecked ->
            resetCamps()
            habilitarCamps(esdevenimentThis)
            /*if (rbPeli.isChecked){
                //habilitarPeli(esdevenimentThis)
                habilitarCamps(esdevenimentThis, "Pel·lícula")
            }
            if (rbXerrada.isChecked){
                //habilitarXerrada(esdevenimentThis)
                habilitarCamps(esdevenimentThis, "Xerrada")
            }
            if(rbConcert.isChecked){
                //habilitarConcert(esdevenimentThis)
                habilitarCamps(esdevenimentThis, "Concert")
            }*/
        }
        /*
        ivCalendari.setOnClickListener{
            var any = LocalDateTime.now().year
            var mes = LocalDateTime.now().month
            var dia = LocalDateTime.now().dayOfMonth
            var hora = LocalDateTime.now().hour
            var minuts = LocalDateTime.now().minute

            if (modificar){
                any = esdevenimentThis.data.year
                mes = esdevenimentThis.data.month
                dia = esdevenimentThis.data.dayOfMonth
                hora = esdevenimentThis.data.hour
                minuts = esdevenimentThis.data.minute
            }
            var localDateTime: LocalDateTime
            localDateTime = obrirDatePicker(any, mes.toString().toInt(), dia, hora, minuts)

            etAny.text = Editable.Factory.getInstance().newEditable(localDateTime.year.toString())
            etMes.text = Editable.Factory.getInstance().newEditable(localDateTime.month.toString())
            etDia.text = Editable.Factory.getInstance().newEditable(localDateTime.dayOfMonth.toString())
            etHora.text = Editable.Factory.getInstance().newEditable(localDateTime.hour.toString())
            etMinuts.text = Editable.Factory.getInstance().newEditable(localDateTime.minute.toString())

        }
        */

        val llista = listOf("Llista específica Element 1", "Llista específica Element 2", "Llista específica Element 3", "Llista específica Element 4")
        val llistaEnString = llista.joinToString("\n") // Converteix la llista a una cadena de text, amb cada element en una nova línia

        etEspecific4.setText(llistaEnString)
        llEspecificDreta4.visibility = View.GONE

        val btnEnrere = findViewById<Button>(R.id.btnEnrere)
        btnEnrere.setOnClickListener {
            finish()
        }

    }

    private fun llegirEsdeveniment():Esdeveniment {

        val id = -1
        val nom = ""
        val imatge = ""
        val descripcio = ""
        val data = LocalDateTime.now()
        val idioma = ""
        val preu = 0.00
        val numerat = false
        val tipus = ""
        val entrades: MutableList<Entrada> = mutableListOf()
        val especific1 = ""
        val especific2 = ""
        val especific3 = ""
        val especific4: MutableList<String> = mutableListOf()
        var esdeveniment = Esdeveniment(id, nom, imatge, descripcio, data, idioma, preu, numerat, tipus, entrades, especific1, especific2, especific3, especific4)

        //comprovar si rebem esdeveniment o se n'ha de crear un de nou
        if (detall){
            esdeveniment = Esdeveniment_Manager.esdeveniments[Esdeveniment_Manager.index]
        } else if (modificar) {
            esdeveniment = Esdeveniment_Manager.esdeveniments[Esdeveniment_Manager.index]
        } // si es nou retornarà un esdeveniment buit
        return esdeveniment
    }//funció per buidar camps específics
    private fun resetCamps(){
        //declaro Views
        val tvTitol = findViewById<TextView>(R.id.tvTitol)
        val etTitol = findViewById<TextView>(R.id.etTitol)

        val rgEsdeveniment = findViewById<RadioGroup>(R.id.rgEsdeveniment)

        val llEspecific1a3 = findViewById<LinearLayout>(R.id.llEspecific1a3)
        val llEspecificEntre3i4 = findViewById<LinearLayout>(R.id.llEspecificEntre3i4)
        val llEspecific4 = findViewById<LinearLayout>(R.id.llEspecific4)
        val llEspecificDreta4 = findViewById<LinearLayout>(R.id.llEspecificDreta4)

        val tvTipus = findViewById<TextView>(R.id.tvTipus)
        val tvEspecific1 = findViewById<TextView>(R.id.tvEspecific1)
        val tvEspecific2 = findViewById<TextView>(R.id.tvEspecific2)
        val tvEspecific3 = findViewById<TextView>(R.id.tvEspecific3)
        val tvEspecific4 = findViewById<TextView>(R.id.tvEspecific4)

        val etEspecific1 = findViewById<EditText>(R.id.etEspecific1)
        val etEspecific2 = findViewById<EditText>(R.id.etEspecific2)
        val etEspecific3 = findViewById<EditText>(R.id.etEspecific3)
        val etEspecific4 = findViewById<EditText>(R.id.etEspecific4)

        //mostro camps
        tvTitol.visibility = View.VISIBLE

        //habilito camps
        etTitol.isEnabled = true

        //Amago els camps
        rgEsdeveniment.visibility = View.GONE

        tvTipus.visibility = View.GONE
        //tvEspecific1.visibility = View.GONE
        //tvEspecific2.visibility = View.GONE
        //tvEspecific3.visibility = View.GONE
        //tvEspecific4.visibility = View.GONE

        //etEspecific1.visibility = View.GONE
        //etEspecific2.visibility = View.GONE
        //etEspecific3.visibility = View.GONE
        //etEspecific4.visibility = View.GONE

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
    //funció per habilitar camps específics segons tipus d'esdeveniment
    private fun habilitarCamps(esdeveniment: Esdeveniment){
        //declaro Views
        //val tvTitol = findViewById<TextView>(R.id.tvTitol)
        val etTitol = findViewById<TextView>(R.id.etTitol)

        val rgEsdeveniment = findViewById<RadioGroup>(R.id.rgEsdeveniment)
        val rbPeli = findViewById<RadioButton>(R.id.rbPeli)
        val rbXerrada = findViewById<RadioButton>(R.id.rbXerrada)
        val rbConcert = findViewById<RadioButton>(R.id.rbConcert)

        val llEspecific1a3 = findViewById<LinearLayout>(R.id.llEspecific1a3)
        val llEspecificEntre3i4 = findViewById<LinearLayout>(R.id.llEspecificEntre3i4)
        val llEspecific4 = findViewById<LinearLayout>(R.id.llEspecific4)

        val tvTipus = findViewById<TextView>(R.id.tvTipus)
        val tvEspecific1 = findViewById<TextView>(R.id.tvEspecific1)
        val tvEspecific2 = findViewById<TextView>(R.id.tvEspecific2)
        val tvEspecific3 = findViewById<TextView>(R.id.tvEspecific3)
        val tvEspecific4 = findViewById<TextView>(R.id.tvEspecific4)

        val etEspecific1 = findViewById<EditText>(R.id.etEspecific1)
        val etEspecific2 = findViewById<EditText>(R.id.etEspecific2)
        val etEspecific3 = findViewById<EditText>(R.id.etEspecific3)
        val etEspecific4 = findViewById<EditText>(R.id.etEspecific4)

        var titolEspecific1 = ""
        var titolEspecific2 = ""
        var titolEspecific3 = ""
        var titolEspecific4 = ""

        //var tipus = esdeveniment.tipus
        var tipus = esdeveniment.tipus

        /*if (modificar || detall){
            tipus = esdeveniment.tipus
        } else if (nou) {
            tipus = rbTipus
        }*/
        if (rbPeli.isChecked)  {
            tipus = "Pel·lícula"
        } else if (rbXerrada.isChecked) {
            tipus = "Xerrada"
        } else if (rbConcert.isChecked) {
            tipus = "Concert"
        }

        //títols camps específics
        if (tipus == "Pel·lícula"){
            titolEspecific1 = "Director:"
            titolEspecific2 = "Any d'estrena:"
            titolEspecific3 = "Durada:"
            titolEspecific4 = "Actors principals:"
        } else if (tipus == "Xerrada") {
            titolEspecific4 = ""
        } else if (tipus == "Concert") {
            titolEspecific1 = "Compositor:"
            titolEspecific2 = "Cor:"
            titolEspecific3 = "Orquestra:"
            titolEspecific4 = "Solistes principals:"
        } else {

        }
        //valors dels camps específics (comú)
        val especific1 = esdeveniment.especific1
        val especific2 = esdeveniment.especific2
        val especific3 = esdeveniment.especific3
        val especific4 = listOf(esdeveniment.especific4).joinToString("\n") // Converteix la llista a una cadena de text, amb cada element en una nova línia

        //omplo camps comuns:
        tvEspecific1.text = titolEspecific1
        tvEspecific2.text = titolEspecific2
        tvEspecific3.text = titolEspecific3
        tvEspecific4.text = titolEspecific4

        //cribo segons esdeveniment nou, modificar o detall
        if (nou || modificar){
            rgEsdeveniment.visibility = View.VISIBLE
            tvEspecific4.text = tvEspecific4.text.toString() + "\n(Un per línia)"
        } else {
            tvTipus.visibility = View.VISIBLE
            tvTipus.text = esdeveniment.tipus
        }
        if (modificar || detall) {
            etEspecific1.text = Editable.Factory.getInstance().newEditable(especific1)
            etEspecific2.text = Editable.Factory.getInstance().newEditable(especific2)
            etEspecific3.text = Editable.Factory.getInstance().newEditable(especific3)
            etEspecific4.text = Editable.Factory.getInstance().newEditable(especific4)
        }

        //habilito camps comuns
        llEspecific4.visibility = View.VISIBLE
        //cribo segons tipus d'esdeveniment per habilitar camps específics
        if (tipus == "Pel·lícula" || tipus == "Concert"){
            llEspecific1a3.visibility = View.VISIBLE
            llEspecificEntre3i4.visibility = View.VISIBLE
        } else if (esdeveniment.tipus == "Xerrada") {

        } else {

        }
    }

}