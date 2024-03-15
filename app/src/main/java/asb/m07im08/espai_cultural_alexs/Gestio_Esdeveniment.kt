package asb.m07im08.espai_cultural_alexs

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.BitmapFactory
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
import org.w3c.dom.Text
import java.io.File
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

        val ivSR = findViewById<ImageView>(R.id.ivSR)
        val ivHR = findViewById<ImageView>(R.id.ivHR)

        val ivCalendari = findViewById<ImageView>(R.id.ivCalendari)
        val etDia = findViewById<EditText>(R.id.etDia)
        val etMes = findViewById<EditText>(R.id.etMes)
        val etAny = findViewById<EditText>(R.id.etAny)
        val etHora = findViewById<EditText>(R.id.etHora)
        val etMinuts = findViewById<EditText>(R.id.etMinuts)

        val etIdioma = findViewById<EditText>(R.id.etIdioma)
        val etPreu = findViewById<EditText>(R.id.etPreu)

        val tvAforament = findViewById<TextView>(R.id.tvAforament)
        val rgNumerat = findViewById<RadioGroup>(R.id.rgNumerat)
        val rbNumerat = findViewById<RadioButton>(R.id.rbNumerat)
        val rbNoNumerat = findViewById<RadioButton>(R.id.rbNoNumerat)

        val rbPeli = findViewById<RadioButton>(R.id.rbPeli)
        val rbXerrada = findViewById<RadioButton>(R.id.rbXerrada)
        val rbConcert = findViewById<RadioButton>(R.id.rbConcert)
        val rgEsdeveniment = findViewById<RadioGroup>(R.id.rgEsdeveniment)
        val tvTipus = findViewById<TextView>(R.id.tvTipus)

        val llEspecific4 = findViewById<LinearLayout>(R.id.llEspecific4)
        val llEspecificDreta4 = findViewById<LinearLayout>(R.id.llEspecificDreta4)
        val etEspecific4 = findViewById<EditText>(R.id.etEspecific4)

        val btnEnrere = findViewById<Button>(R.id.btnEnrere)
        val btnModifiCrear = findViewById<Button>(R.id.btnModifiCrear)
        val btnEliminar = findViewById<Button>(R.id.btnEliminar)

        //TODO //si puc faré el datetimepicker
        ivCalendari.visibility = View.GONE


        //inserirImatgeSR(esdevenimentThis.imatge)
        //inserirImatgeHR(esdevenimentThis.imatge)
        GestorImatge.inserirImatgeSR(esdevenimentThis.imatge, this, ivSR)
        GestorImatge.inserirImatgeHR(esdevenimentThis.imatge, this, ivHR)

        resetCamps()
        habilitarCamps(esdevenimentThis)
        val mesFormatat = String.format("%02d", esdevenimentThis.data.monthValue)


        rgEsdeveniment.setOnCheckedChangeListener{_, isChecked ->
            resetCamps()
            habilitarCamps(esdevenimentThis)
            //habilito camps comuns
            llEspecific4.visibility = View.VISIBLE
        }
        btnEnrere.setOnClickListener {
            finish()
        }

        btnModifiCrear.setOnClickListener {
            val intent: Intent
            if (detall) {
                intent = Intent(this, Gestio_Esdeveniment::class.java).apply {
                    putExtra("modificar", true)
                }
                startActivity(intent)
            } else if (modificar) {
                //TODO //modificar esdeveniment
            } else if (nou) {
                //TODO //escriure esdeveniment nou
            }
        }
    }
    private fun llegirEsdeveniment():Esdeveniment {

        var esdeveniment = Esdeveniment()

        //comprovar si rebem esdeveniment o se n'ha de crear un de nou
        if (detall || modificar){
            esdeveniment = Esdeveniment_Manager.esdeveniments[Esdeveniment_Manager.index]
        } // si es nou retornarà un esdeveniment buit
        return esdeveniment
    }//funció per buidar camps específics

    private fun inserirImatgeSR (imatge: String){
        var imgElementPathPNG = this.getFilesDir().toString() + "/img/" + "sr" + imatge + ".png"
        var imgElementPathJPG = this.getFilesDir().toString() + "/img/" + "sr" + imatge + ".jpg"
        var bitmap = if (File(imgElementPathPNG).exists()) {
            BitmapFactory.decodeFile(imgElementPathPNG)
        } else if (File(imgElementPathJPG).exists()) {
            BitmapFactory.decodeFile(imgElementPathJPG)
        } else {
            BitmapFactory.decodeResource(resources, R.drawable.noimg_sr)
        }
        val ivSR = findViewById<ImageView>(R.id.ivSR)
        ivSR.setImageBitmap(bitmap)
    }
    private fun inserirImatgeHR (imatge: String){
        var imgElementPathPNG = this.getFilesDir().toString() + "/img/" + "hr" + imatge + ".png"
        var imgElementPathJPG = this.getFilesDir().toString() + "/img/" + "hr" + imatge + ".jpg"
        var bitmap = if (File(imgElementPathPNG).exists()) {
            BitmapFactory.decodeFile(imgElementPathPNG)
        } else if (File(imgElementPathJPG).exists()) {
            BitmapFactory.decodeFile(imgElementPathJPG)
        } else {
            BitmapFactory.decodeResource(resources, R.drawable.noimg_hr)
        }
        val ivHR = findViewById<ImageView>(R.id.ivHR)
        ivHR.setImageBitmap(bitmap)
    }
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
        val tvTitol = findViewById<TextView>(R.id.tvTitol)
        val etTitol = findViewById<EditText>(R.id.etTitol)

        val tvCarregaImgHR = findViewById<TextView>(R.id.tvCarregaImgHR)
        val btnCarregaImgHR = findViewById<Button>(R.id.btnCarregaImgHR)

        val llCarregaImgSR = findViewById<LinearLayout>(R.id.llCarregaImgSR)
        val ivSR = findViewById<ImageView>(R.id.ivSR)
        val tvCarregaImgSR = findViewById<TextView>(R.id.tvCarregaImgSR)
        val btnCarregaImgSR = findViewById<Button>(R.id.btnCarregaImgHR)


        val ivCalendari = findViewById<ImageView>(R.id.ivCalendari)
        val etDia = findViewById<EditText>(R.id.etDia)
        val etMes = findViewById<EditText>(R.id.etMes)
        val etAny = findViewById<EditText>(R.id.etAny)
        val etHora = findViewById<EditText>(R.id.etHora)
        val etMinuts = findViewById<EditText>(R.id.etMinuts)

        val etIdioma = findViewById<EditText>(R.id.etIdioma)
        val etPreu = findViewById<EditText>(R.id.etPreu)

        val tvAforament = findViewById<TextView>(R.id.tvAforament)
        val rgNumerat = findViewById<RadioGroup>(R.id.rgNumerat)
        val rbNumerat = findViewById<RadioButton>(R.id.rbNumerat)
        val rbNoNumerat = findViewById<RadioButton>(R.id.rbNoNumerat)

        val rgEsdeveniment = findViewById<RadioGroup>(R.id.rgEsdeveniment)
        val rbPeli = findViewById<RadioButton>(R.id.rbPeli)
        val rbXerrada = findViewById<RadioButton>(R.id.rbXerrada)
        val rbConcert = findViewById<RadioButton>(R.id.rbConcert)
        val tvTipus = findViewById<TextView>(R.id.tvTipus)

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

        val btnModifiCrear = findViewById<Button>(R.id.btnModifiCrear)
        val btnEliminar = findViewById<Button>(R.id.btnEliminar)

        var titolEspecific1 = ""
        var titolEspecific2 = ""
        var titolEspecific3 = ""
        var titolEspecific4 = ""

        var tipus = esdeveniment.tipus
        val mesFormatat = String.format("%02d", esdeveniment.data.monthValue)

        //activació de radiobutton tipus
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
            titolEspecific4 = "Participants: "
        } else if (tipus == "Concert") {
            titolEspecific1 = "Compositor:"
            titolEspecific2 = "Cor:"
            titolEspecific3 = "Orquestra:"
            titolEspecific4 = "Solistes principals:"
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
        } else { //detall
            tvTitol.visibility = View.GONE
            etTitol.isEnabled = false
            tvCarregaImgHR.visibility = View.GONE
            btnCarregaImgHR.visibility = View.GONE
            llCarregaImgSR.visibility = View.GONE
            etAny.isEnabled = false
            etMes.isEnabled = false
            etDia.isEnabled = false
            etHora.isEnabled = false
            etMinuts.isEnabled = false
            etIdioma.isEnabled = false
            etPreu.isEnabled = false
            if (esdeveniment.numerat){
                tvAforament.text = "Aforament numerat"
            } else {
                tvAforament.text = "Aforament no numerat"
            }
            rgNumerat.visibility = View.GONE
            tvTipus.visibility = View.VISIBLE
            tvTipus.text = "Tipus d'esdeveniment: " + esdeveniment.tipus
            rgEsdeveniment.visibility = View.GONE
            if (esdeveniment.tipus == "Pel·lícula"){
                rbPeli.isChecked = true
            } else if (esdeveniment.tipus == "Xerrada"){
                rbXerrada.isChecked = true
            } else if (esdeveniment.tipus == "Concert") {
                rbConcert.isChecked = true
            }
            btnModifiCrear.text = "Modificar"
            btnEliminar.visibility = View.GONE
            ivCalendari.visibility = View.GONE

            //tvTipus.text = esdeveniment.tipus
            etEspecific1.isEnabled = false
            etEspecific2.isEnabled = false
            etEspecific3.isEnabled = false
            etEspecific4.isEnabled = false
        }
        if (modificar || detall) {
            etTitol.text = Editable.Factory.getInstance().newEditable(esdeveniment.nom)
            etAny.text = Editable.Factory.getInstance().newEditable(esdeveniment.data.year.toString())
            //etMes.text = Editable.Factory.getInstance().newEditable(esdevenimentThis.data.month.toString())
            etMes.text = Editable.Factory.getInstance().newEditable(mesFormatat)
            etDia.text = Editable.Factory.getInstance().newEditable(esdeveniment.data.dayOfMonth.toString())
            etHora.text = Editable.Factory.getInstance().newEditable(esdeveniment.data.hour.toString())
            etMinuts.text = Editable.Factory.getInstance().newEditable(esdeveniment.data.minute.toString())
            etIdioma.text = Editable.Factory.getInstance().newEditable(esdeveniment.idioma)
            etPreu.text = Editable.Factory.getInstance().newEditable(esdeveniment.preu.toString())

            etEspecific1.text = Editable.Factory.getInstance().newEditable(especific1)
            etEspecific2.text = Editable.Factory.getInstance().newEditable(especific2)
            etEspecific3.text = Editable.Factory.getInstance().newEditable(especific3)
            etEspecific4.text = Editable.Factory.getInstance().newEditable(especific4)
        }
        if (nou) {
            btnModifiCrear.text = "Crear"
            btnEliminar.visibility = View.GONE
        }
        if (modificar) {
            if (esdeveniment.numerat){
                rbNumerat.isChecked = true
            } else {
                rbNoNumerat.isChecked = true
            }
            rgEsdeveniment.visibility = View.GONE
            btnModifiCrear.text = "Desar canvis"
        }


        //cribo segons tipus d'esdeveniment per habilitar camps específics
        if (tipus == "Pel·lícula" || tipus == "Concert") {
            llEspecific1a3.visibility = View.VISIBLE
            llEspecificEntre3i4.visibility = View.VISIBLE
            tvEspecific4.visibility = View.VISIBLE
        }
    }
}