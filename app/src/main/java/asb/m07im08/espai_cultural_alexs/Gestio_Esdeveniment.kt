package asb.m07im08.espai_cultural_alexs

import android.app.Activity
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
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
import java.io.FileOutputStream
import java.io.InputStream
import java.time.LocalDateTime
import java.time.Month
import java.time.MonthDay
import java.time.Year
import java.util.Date

class Gestio_Esdeveniment : AppCompatActivity() {
    private var esdevenimentThis: Esdeveniment = Esdeveniment()
    private var detall: Boolean = false
    private var nou: Boolean = false
    private var modificar: Boolean = false

    val PICK_IMAGE_REQUEST_CODE = 1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //rebo putextra de l'intent
        esdevenimentThis = intent.getSerializableExtra("esdeveniment") as Esdeveniment? ?: Esdeveniment()
        detall = intent.getBooleanExtra("detall", false)
        nou = intent.getBooleanExtra("nou", false)
        modificar = intent.getBooleanExtra("modificar", false)

        if (Esdeveniment_Manager.esdeveniments.isNotEmpty() && nou){
            esdevenimentThis.id = Esdeveniment_Manager.esdeveniments.last().id + 1
        } else if (Esdeveniment_Manager.esdeveniments.isEmpty() && nou){
            esdevenimentThis.id = 1
        }

        setContentView(R.layout.activity_gestio_esdeveniment)

        //val actualitzarLlistat = ActualitzarLlistat(this, false)


        //var esdevenimentThis = llegirEsdeveniment()

        //declaro views
        val tvTitol = findViewById<TextView>(R.id.tvTitol)
        val etTitol = findViewById<EditText>(R.id.etTitol)

        val ivSR = findViewById<ImageView>(R.id.ivSR)
        val btnCarregaImgSR = findViewById<Button>(R.id.btnCarregaImgSR)

        val ivHR = findViewById<ImageView>(R.id.ivHR)
        val btnCarregaImgHR = findViewById<Button>(R.id.btnCarregaImgHR)

        val etDescripcio = findViewById<EditText>(R.id.etDescripcio)
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

        val etEspecific1 = findViewById<EditText>(R.id.etEspecific1)
        val etEspecific2 = findViewById<EditText>(R.id.etEspecific2)
        val etEspecific3 = findViewById<EditText>(R.id.etEspecific3)
        val llEspecific4 = findViewById<LinearLayout>(R.id.llEspecific4)
        val llEspecificDreta4 = findViewById<LinearLayout>(R.id.llEspecificDreta4)
        val etEspecific4 = findViewById<EditText>(R.id.etEspecific4)

        val btnEnrere = findViewById<Button>(R.id.btnEnrere)
        val btnModifiCrear = findViewById<Button>(R.id.btnModifiCrear)
        val btnEliminar = findViewById<Button>(R.id.btnEliminar)
        val btnReservar = findViewById<Button>(R.id.btnReservar)

        //TODO //si puc faré el datetimepicker
        ivCalendari.visibility = View.GONE


        //inserirImatgeSR(esdevenimentThis.imatge)
        //inserirImatgeHR(esdevenimentThis.imatge)
        GestorImatge.inserirImatgeSR(esdevenimentThis.id.toString(), this, ivSR)
        GestorImatge.inserirImatgeHR(esdevenimentThis.id.toString(), this, ivHR)

        resetCamps()
        habilitarCamps(esdevenimentThis)
        val mesFormatat = String.format("%02d", esdevenimentThis.data.monthValue)

        var aforamentTriat = false
        rgNumerat.setOnCheckedChangeListener{_, isChecked ->
            aforamentTriat = true
        }

        var tipusTriat = false
        rgEsdeveniment.setOnCheckedChangeListener{_, isChecked ->
            tipusTriat = true
            resetCamps()
            habilitarCamps(esdevenimentThis)
        }
        var highRes = true
        btnCarregaImgHR.setOnClickListener{
            highRes = true
            openImagePicker()
        }
        btnCarregaImgSR.setOnClickListener{
            highRes = false
        }


        btnModifiCrear.setOnClickListener {
            if (detall) {
                val intent: Intent
                intent = Intent(this, Gestio_Esdeveniment::class.java).apply {
                    putExtra("esdeveniment", esdevenimentThis)
                    putExtra("modificar", true)
                }
                startActivity(intent)
            } else if (modificar) {
                //TODO("modificar esdeveniment")
            } else if (nou) {
                //TODO("escriure esdeveniment nou")
                var esdevenimentCreable = true
                if (aforamentTriat && tipusTriat){
                    val tipus: String = (if (rbPeli.isChecked){
                        "Pel·lícula"
                    } else if (rbXerrada.isChecked){
                        "Xerrada"
                    } else if (rbConcert.isChecked) {
                        "Concert"
                    } else {
                        ""
                    })

                    val text = etEspecific4.text.toString()
                    val linies = text.split("\n")
                    val llistaLinies = mutableListOf<String>()
                    for (linia in linies) {
                        llistaLinies.add(linia)
                    }
                    /*var seguentId = 1
                    if (Esdeveniment_Manager.esdeveniments.count() > 0){
                        seguentId = Esdeveniment_Manager.esdeveniments.last().id + 1
                    }*/
                    var any = -1
                    var mes = -1
                    var dia = -1
                    var hora = -1
                    var minuts = -1
                    if (etAny.text.isNotEmpty() &&
                            etMes.text.isNotEmpty() &&
                            etDia.text.isNotEmpty() &&
                            etHora.text.isNotEmpty() &&
                            etMinuts.text.isNotEmpty()){
                        any = etAny.text.toString().toInt()
                        mes = etMes.text.toString().toInt()
                        dia = etDia.text.toString().toInt()
                        hora = etHora.text.toString().toInt()
                        minuts = etMinuts.text.toString().toInt()
                    } else {
                        esdevenimentCreable = false
                    }
                    var data = LocalDateTime.now()
                    var preu = 1.11
                    try {
                        data =  LocalDateTime.of(any, mes, dia, hora, minuts)
                        val preuString = etPreu.text.toString()
                        preu = preuString.toDouble()
                    } catch (e: Exception) {
                        esdevenimentCreable = false
                    }
                    if (esdevenimentCreable){
                        //ActualitzarLlistat(this, false)
                        JsonIO.llegirLlistat(this, false)
                        val nouEsdeveniment = Esdeveniment(
                            //seguentId,//id
                            esdevenimentThis.id, //id
                            etTitol.text.toString(),//nom
                            //seguentId.toString(),//imatge
                            etDescripcio.text.toString(),// descripcio
                            data,// data
                            etIdioma.text.toString(),// idioma
                            preu,// preu
                            rbNumerat.isChecked,// numerat
                            tipus,// tipus
                            mutableListOf(Entrada()),// entrades
                            etEspecific1.text.toString(),// especific1
                            etEspecific2.text.toString(),// especific2
                            etEspecific3.text.toString(),// especific3
                            llistaLinies// especific4
                        )
                        //TODO("comprovar i desar les imatges")
                        //actualitzarLlistat.afegirEsdeveniment(this, nouEsdeveniment)
                        JsonIO.afegirEsdeveniment(this, nouEsdeveniment)
                        //Toast.makeText(this, nouEsdeveniment.nom + " " + nouEsdeveniment.data.toString(), Toast.LENGTH_SHORT).show()
                    }

                } else {
                    esdevenimentCreable = false
                }
                if (esdevenimentCreable == false) {
                    Toast.makeText(this, "Si us plau, omple tots els camps", Toast.LENGTH_SHORT).show()
                }
                //ActualitzarLlistat(this, false)
            }
        }
        var eliminar = false
        btnEliminar.setOnClickListener{
            if (eliminar){
                //TODO("cercar esdeveniment al json i eliminar-lo")
            } else {
                eliminar = true
                btnEliminar.setBackgroundColor(Color.RED)
                btnEliminar.text = "CONFIRMAR ELIMINACIÓ"
            }
        }
        btnReservar.setOnClickListener{
            val intent = Intent(this, Reservar::class.java).apply {
                putExtra("novaReserva", true)
                putExtra("esdeveniment", esdevenimentThis)
            }
            startActivity(intent)
        }
    }
    fun openImagePicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE)
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                val inputStream: InputStream? = contentResolver.openInputStream(uri)
                inputStream?.use { input ->
                    saveImageToFile(input)
                }
            }
        }
    }

    private fun saveImageToFile(inputStream: InputStream) {
        val file = File(filesDir, "image.jpg")
        val outputStream = FileOutputStream(file)
        inputStream.use { input ->
            outputStream.use { output ->
                input.copyTo(output)
            }
        }
        // Aquí ja tens la imatge desada a la ruta filesDir/image.jpg
    }
    private fun llegirEsdeveniment():Esdeveniment {

        var esdeveniment = Esdeveniment()

        //comprovar si rebem esdeveniment o se n'ha de crear un de nou
        if (detall || modificar){
            esdeveniment = Esdeveniment_Manager.esdeveniments[Esdeveniment_Manager.index]
        } // si es nou retornarà un esdeveniment buit
        return esdeveniment
    }
    //funció per buidar camps específics
    private fun resetCamps(){
        //declaro Views
        val tvTitol = findViewById<TextView>(R.id.tvTitol)
        val etTitol = findViewById<TextView>(R.id.etTitol)
        val etDescripcio = findViewById<EditText>(R.id.etDescripcio)

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

        val btnReservar = findViewById<Button>(R.id.btnReservar)

        //mostro camps
        tvTitol.visibility = View.VISIBLE

        //habilito camps
        etTitol.isEnabled = true
        etDescripcio.isEnabled = true

        //Amago els camps
        rgEsdeveniment.visibility = View.GONE
        tvTipus.visibility = View.GONE
        btnReservar.visibility = View.GONE

        //Buido els camps
        etDescripcio.text = null

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
        val tvTitol = findViewById<TextView>(R.id.tvTitol)
        val etTitol = findViewById<EditText>(R.id.etTitol)
        val etDescripcio = findViewById<EditText>(R.id.etDescripcio)

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
        val btnReservar = findViewById<Button>(R.id.btnReservar)

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
        //val especific4 = listOf(esdeveniment.especific4).joinToString("\n") // Converteix la llista a una cadena de text, amb cada element en una nova línia
        val especific4 = esdeveniment.especific4.joinToString(separator = "\n", prefix = "", postfix = "")

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
            etDescripcio.isEnabled = false
            etAny.isEnabled = false
            etMes.isEnabled = false
            etDia.isEnabled = false
            ivCalendari.visibility = View.GONE
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
            rgEsdeveniment.visibility = View.GONE
            if (esdeveniment.tipus == "Pel·lícula"){
                rbPeli.isChecked = true
            } else if (esdeveniment.tipus == "Xerrada"){
                rbXerrada.isChecked = true
            } else if (esdeveniment.tipus == "Concert") {
                rbConcert.isChecked = true
            }
            etEspecific1.isEnabled = false
            etEspecific2.isEnabled = false
            etEspecific3.isEnabled = false
            etEspecific4.isEnabled = false

            btnModifiCrear.text = "Modificar"
            btnEliminar.visibility = View.GONE
            btnReservar.visibility = View.VISIBLE

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
            tvTipus.visibility = View.VISIBLE
            tvTipus.text = "Tipus d'esdeveniment: " + esdeveniment.tipus

            etEspecific1.text = Editable.Factory.getInstance().newEditable(especific1)
            etEspecific2.text = Editable.Factory.getInstance().newEditable(especific2)
            etEspecific3.text = Editable.Factory.getInstance().newEditable(especific3)
            etEspecific4.text = Editable.Factory.getInstance().newEditable(especific4)
        } else { //nou
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
            btnEliminar.visibility =View.VISIBLE
        }


        //cribo segons tipus d'esdeveniment per habilitar camps específics
        if (tipus == "Pel·lícula" || tipus == "Concert") {
            llEspecific1a3.visibility = View.VISIBLE
            llEspecificEntre3i4.visibility = View.VISIBLE
            //tvEspecific4.visibility = View.VISIBLE
            llEspecific4.visibility = View.VISIBLE
        } else if (tipus == "Xerrada") {
            llEspecific4.visibility = View.VISIBLE
        }

    }
}