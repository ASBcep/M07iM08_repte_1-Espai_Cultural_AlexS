package asb.m07im08.espai_cultural_alexs

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView

class Detall : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detall)

        val txtVwDescripcio: TextView = findViewById(R.id.TxtVwDescripcio)
        //permetre scroll en el textview
        txtVwDescripcio.movementMethod = android.text.method.ScrollingMovementMethod.getInstance()
    }
}