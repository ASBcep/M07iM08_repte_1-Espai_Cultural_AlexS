package asb.m07im08.espai_cultural_alexs

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.marginBottom
import androidx.core.view.marginLeft
import androidx.core.view.marginRight
import androidx.core.view.marginTop
import java.io.File
import java.io.FileOutputStream
import java.time.LocalDateTime

class Gestio_Esdeveniment : AppCompatActivity() {
    private var esdevenimentThis: Esdeveniment = Esdeveniment()
    private var detall = false
    private var nou: Boolean = false
    private var modificar: Boolean = false
    var highRes = true
    val PICK_IMAGE_REQUEST_CODE = 1
    private var resultatResult = false
    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult())
        {
            if(it.resultCode == AppCompatActivity.RESULT_OK) {
                Toast.makeText(this, "Esdeveniment modificat", Toast.LENGTH_SHORT).show()
                resultatResult = true
                carregaViews()
            }
        }
    private fun carregaViews() {
        if (resultatResult){
            esdevenimentThis = Esdeveniment_Manager.esdeveniments[JsonIO.cercarEsdeveniment(esdevenimentThis)]
        }
        resetCamps()
        habilitarCamps(esdevenimentThis)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //rebo putextra de l'intent
        esdevenimentThis = intent.getSerializableExtra("esdeveniment") as Esdeveniment? ?: Esdeveniment()
        detall = intent.getBooleanExtra("detall", false)
        nou = intent.getBooleanExtra("nou", false)
        modificar = intent.getBooleanExtra("modificar", false)

        if (Esdeveniment_Manager.esdeveniments.isNotEmpty() && nou){
            //esdevenimentThis.id = Esdeveniment_Manager.esdeveniments.last().id + 1
            esdevenimentThis.id = JsonIO.idPerNouEsdeveniment()
        } else if (Esdeveniment_Manager.esdeveniments.isEmpty() && nou){
            esdevenimentThis.id = 1
        }

        setContentView(R.layout.activity_gestio_esdeveniment)

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

        //val tvAforament = findViewById<TextView>(R.id.tvAforament)
        val rgNumerat = findViewById<RadioGroup>(R.id.rgNumerat)
        val rbNumerat = findViewById<RadioButton>(R.id.rbNumerat)
        val rbNoNumerat = findViewById<RadioButton>(R.id.rbNoNumerat)

        val rbPeli = findViewById<RadioButton>(R.id.rbPeli)
        val rbXerrada = findViewById<RadioButton>(R.id.rbXerrada)
        val rbConcert = findViewById<RadioButton>(R.id.rbConcert)
        val rgEsdeveniment = findViewById<RadioGroup>(R.id.rgEsdeveniment)
        //val tvTipus = findViewById<TextView>(R.id.tvTipus)

        val etEspecific1 = findViewById<EditText>(R.id.etEspecific1)
        val etEspecific2 = findViewById<EditText>(R.id.etEspecific2)
        val etEspecific3 = findViewById<EditText>(R.id.etEspecific3)
        //val llEspecific4 = findViewById<LinearLayout>(R.id.llEspecific4)
        //val llEspecificDreta4 = findViewById<LinearLayout>(R.id.llEspecificDreta4)
        val etEspecific4 = findViewById<EditText>(R.id.etEspecific4)

        val btnEnrere = findViewById<Button>(R.id.btnEnrere)
        val btnModifiCrear = findViewById<Button>(R.id.btnModifiCrear)
        val btnEliminar = findViewById<Button>(R.id.btnEliminar)
        val btnReservar = findViewById<Button>(R.id.btnReservar)

        //TODO ("si puc faré el datetimepicker")

        ivCalendari.visibility = View.GONE

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

        btnCarregaImgHR.setOnClickListener{
            highRes = true
            openImagePicker()
            GestorImatge.inserirImatgeHR(esdevenimentThis.id.toString(),this,ivHR)
        }
        btnCarregaImgSR.setOnClickListener{
            highRes = false
            openImagePicker()
            GestorImatge.inserirImatgeSR(esdevenimentThis.id.toString(),this,ivSR)
        }
        btnEnrere.setOnClickListener{
            setResult(RESULT_OK)
            finish()
        }
        //botó crear o modificar
        btnModifiCrear.setOnClickListener {
            if (detall) {
                val intent: Intent
                intent = Intent(this, Gestio_Esdeveniment::class.java).apply {
                    putExtra("esdeveniment", esdevenimentThis)
                    putExtra("modificar", true)
                }
                //startActivity(intent)
                getResult.launch(intent)
            } else if (modificar) {
                var esdevenimentModificable = true
                if (rbNumerat.isChecked || rbNoNumerat.isChecked) {aforamentTriat = true}
                if (aforamentTriat){
                    val text = etEspecific4.text.toString()
                    val linies = text.split("\n")
                    val llistaLinies = mutableListOf<String>()
                    for (linia in linies) {
                        llistaLinies.add(linia)
                    }
                    var descripcio = ""
                    var any = -1
                    var mes = -1
                    var dia = -1
                    var hora = -1
                    var minuts = -1
                    if (etDescripcio.text.isNotEmpty() &&
                        etAny.text.isNotEmpty() &&
                        etMes.text.isNotEmpty() &&
                        etDia.text.isNotEmpty() &&
                        etHora.text.isNotEmpty() &&
                        etMinuts.text.isNotEmpty())
                    {
                        descripcio = etDescripcio.text.toString()
                        any = etAny.text.toString().toInt()
                        mes = etMes.text.toString().toInt()
                        dia = etDia.text.toString().toInt()
                        hora = etHora.text.toString().toInt()
                        minuts = etMinuts.text.toString().toInt()
                    } else {
                        esdevenimentModificable = false
                    }
                    var data = LocalDateTime.now()
                    var preu = 1.11
                    try {
                        data =  LocalDateTime.of(any, mes, dia, hora, minuts)
                        val preuString = etPreu.text.toString()
                        preu = preuString.toDouble()
                    } catch (e: Exception) {
                        esdevenimentModificable = false
                    }
                    if (esdevenimentModificable){
                        JsonIO.llegirLlistat(this, false)
                        val EsdevenimentModificat = Esdeveniment(
                            esdevenimentThis.id, //id
                            etTitol.text.toString(),//nom
                            descripcio,// descripcio
                            data,// data
                            etIdioma.text.toString(),// idioma
                            preu,// preu
                            rbNumerat.isChecked,// numerat
                            esdevenimentThis.tipus,// tipus
                            esdevenimentThis.entrades,// entrades
                            etEspecific1.text.toString(),// especific1
                            etEspecific2.text.toString(),// especific2
                            etEspecific3.text.toString(),// especific3
                            llistaLinies// especific4
                        )
                        val indexEsdevenimentOriginal = JsonIO.cercarEsdeveniment(EsdevenimentModificat)
                        var afegit: Boolean
                        afegit = JsonIO.modificarEsdeveniment(this, EsdevenimentModificat, indexEsdevenimentOriginal)
                        if (afegit) {
                            setResult(RESULT_OK)
                            finish()
                        }//tanco activity
                    }
                } else {
                    esdevenimentModificable = false
                }
                if (esdevenimentModificable == false) {
                    Toast.makeText(this, "Si us plau, omple tots els camps", Toast.LENGTH_SHORT).show()
                }
            } else if (nou) {
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
                    var descripcio = ""
                    var any = -1
                    var mes = -1
                    var dia = -1
                    var hora = -1
                    var minuts = -1
                    if (etDescripcio.text.isNotEmpty() &&
                        etAny.text.isNotEmpty() &&
                        etMes.text.isNotEmpty() &&
                        etDia.text.isNotEmpty() &&
                        etHora.text.isNotEmpty() &&
                        etMinuts.text.isNotEmpty())
                    {
                        descripcio = etDescripcio.text.toString()
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
                        JsonIO.llegirLlistat(this, false)
                        var entradesBuit = mutableListOf(Entrada())
                        entradesBuit.clear()
                        val nouEsdeveniment = Esdeveniment(
                            esdevenimentThis.id, //id
                            etTitol.text.toString(),//nom
                            descripcio,// descripcio
                            data,// data
                            etIdioma.text.toString(),// idioma
                            preu,// preu
                            rbNumerat.isChecked,// numerat
                            tipus,// tipus
                            entradesBuit,// entrades
                            etEspecific1.text.toString(),// especific1
                            etEspecific2.text.toString(),// especific2
                            etEspecific3.text.toString(),// especific3
                            llistaLinies// especific4
                        )
                        JsonIO.afegirEsdeveniment(this, nouEsdeveniment)
                        //Toast.makeText(this, nouEsdeveniment.nom + " " + nouEsdeveniment.data.toString(), Toast.LENGTH_SHORT).show()
                        setResult(RESULT_OK)
                        finish()//tanco activity
                    }
                } else {
                    esdevenimentCreable = false
                }
                if (esdevenimentCreable == false) {
                    Toast.makeText(this, "Si us plau, omple tots els camps", Toast.LENGTH_SHORT).show()
                }
            }
        }
        var eliminar = false
        btnEliminar.setOnClickListener{
            if (eliminar){
                if (JsonIO.eliminarEsdeveniment(this, esdevenimentThis)) {
                    Toast.makeText(this, "Esdeveniment eliminat", Toast.LENGTH_SHORT).show()
                    setResult(RESULT_OK)
                    finish()
                } else {
                    Toast.makeText(this, "Error: esdeveniment no eliminat", Toast.LENGTH_SHORT).show()
                    eliminar = false
                    btnEliminar.setBackgroundColor(R.color.boto)
                    btnEliminar.text = "Reintentar eliminació"
                }
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
            getResult.launch(intent)
        }
    }
    //obrir selector d'imatges
    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.type = "image/*"
        startActivityForResult(intent, PICK_IMAGE_REQUEST_CODE)
    }
    //rebre el resultat del selector d'imatges
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == PICK_IMAGE_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            data?.data?.let { uri ->
                val imageType = getImageTypeFromUri(uri)
                saveImageToFile(uri, imageType)
            }
        }
    }
    //conèixer el tipus d'imatge
    private fun getImageTypeFromUri(uri: Uri): String {
        contentResolver.getType(uri)?.let { mimeType ->
            return when {
                mimeType.startsWith("image/png") -> "png"
                mimeType.startsWith("image/jpeg") -> "jpg"
                mimeType.startsWith("image/jpg") -> "jpg"
                else -> ""
            }
        }
        return ""
    }
    //desar la imatge
    private fun saveImageToFile(uri: Uri, imageType: String) {
        val inputStream = contentResolver.openInputStream(uri)
        var fileName = ""
        if (highRes) {
            fileName = esdevenimentThis.id.toString() + GestorImatge.sufixImgHR + "." + imageType
        } else {
            fileName = esdevenimentThis.id.toString() + GestorImatge.sufixImgSR + "." + imageType
        }
        val directory = this.filesDir.toString() +  "/img/"
        val file = File(directory, fileName)

        inputStream?.use { input ->
            FileOutputStream(file).use { output ->
                val buffer = ByteArray(4 * 1024)
                var read: Int
                while (input.read(buffer).also { read = it } != -1) {
                    output.write(buffer, 0, read)
                }
                output.flush()
            }
        }
        Toast.makeText(this, "Imatge desada a: ${file.absolutePath}", Toast.LENGTH_LONG).show()
        val ivHR = findViewById<ImageView>(R.id.ivHR)
        GestorImatge.inserirImatgeHR(esdevenimentThis.id.toString(),this,ivHR)
        val ivSR = findViewById<ImageView>(R.id.ivSR)
        GestorImatge.inserirImatgeSR(esdevenimentThis.id.toString(),this,ivSR)
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


        val llCarregaImgHR = findViewById<LinearLayout>(R.id.llCarregaImgHR)
        val llCarregaImgSR = findViewById<LinearLayout>(R.id.llCarregaImgSR)
        val ivHR = findViewById<ImageView>(R.id.ivHR)
        //val ivSR = findViewById<ImageView>(R.id.ivSR)
        //val tvCarregaImgSR = findViewById<TextView>(R.id.tvCarregaImgSR)
        //val btnCarregaImgSR = findViewById<Button>(R.id.btnCarregaImgHR)

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
            llCarregaImgHR.setBackgroundColor(getResources().getColor(R.color.fons))
            ivHR.setPadding(0,0,0,0)
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
            etDescripcio.text = Editable.Factory.getInstance().newEditable(esdeveniment.descripcio)
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
            llEspecific4.visibility = View.VISIBLE
        } else if (tipus == "Xerrada") {
            llEspecific4.visibility = View.VISIBLE
        }
    }
}