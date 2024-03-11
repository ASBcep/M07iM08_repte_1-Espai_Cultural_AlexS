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

        val btnModifiCrear = findViewById<Button>(R.id.btnModifiCrear)
        val btnEliminar = findViewById<Button>(R.id.btnEliminar)

        if (nou){
            rgEsdeveniment.visibility = View.VISIBLE
            btnModifiCrear.text = "Crear"
            btnEliminar.visibility = View.GONE
        }
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
        ivCalendari.setOnClickListener{
            obrirDatePicker()
        }

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
        val imatgeHR = ""
        val imatgeSR = ""
        val descripcio = ""
        val data = LocalDateTime.now()
        val preu = 0.00
        val numerat = false
        val tipus = ""
        val entrades: MutableList<Entrada> = mutableListOf()
        val especific1 = ""
        val especific2 = ""
        val especific3 = ""
        val especific4: MutableList<String> = mutableListOf()
        var esdeveniment = Esdeveniment(id, nom, imatgeHR, imatgeSR, descripcio, data, preu, numerat, tipus, entrades, especific1, especific2, especific3, especific4)

        //comprovar si rebem esdeveniment o se n'ha de crear un de nou
        if (detall){
            esdeveniment = Esdeveniment_Manager.esdeveniments[Esdeveniment_Manager.index]
        } else if (modificar) {
            esdeveniment = Esdeveniment_Manager.esdeveniments[Esdeveniment_Manager.index]
        } // si es nou retornarà un esdeveniment buit
        return esdeveniment
    }

    private fun obrirDatePicker() {//falta assignar data als editext
        // Obre el selector de data
        var currentDate = Calendar.getInstance()
        var year = currentDate.get(Calendar.YEAR)
        var month = currentDate.get(Calendar.MONTH)
        var day = currentDate.get(Calendar.DAY_OF_MONTH)

        val currentTime = Calendar.getInstance()
        var hour = currentTime.get(Calendar.HOUR_OF_DAY)
        var minute = currentTime.get(Calendar.MINUTE)

        val datePickerDialog = DatePickerDialog(this,
            DatePickerDialog.OnDateSetListener { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
                // Obre el selector d'hora quan es selecciona la data

                hour = currentTime.get(Calendar.HOUR_OF_DAY)
                minute = currentTime.get(Calendar.MINUTE)

                val timePickerDialog = TimePickerDialog(this,
                    TimePickerDialog.OnTimeSetListener { _: TimePicker, selectedHour: Int, selectedMinute: Int ->
                        // Aquí pots fer el que vulguis amb la data i hora seleccionades
                        val selectedDateTime = Calendar.getInstance()
                        selectedDateTime.set(selectedYear, selectedMonth, selectedDay, selectedHour, selectedMinute)
                        println("Data i Hora seleccionades: ${selectedDateTime.time}")
                    }, hour, minute, true)

                timePickerDialog.show()
            }, year, month, day)

        datePickerDialog.show()
    }

    //funció per buidar camps específics
    private fun amagarCampsEspecifics(){
        //declaro Views
        val rgEsdeveniment = findViewById<RadioGroup>(R.id.rgEsdeveniment)

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
        rgEsdeveniment.visibility = View.GONE

        tvEspecific1.visibility = View.GONE
        tvEspecific2.visibility = View.GONE
        tvEspecific3.visibility = View.GONE
        tvEspecific4.visibility = View.GONE

        etEspecific1.visibility = View.GONE
        etEspecific2.visibility = View.GONE
        etEspecific3.visibility = View.GONE
        etEspecific4.visibility = View.GONE

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