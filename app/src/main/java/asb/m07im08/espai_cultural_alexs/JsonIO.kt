package asb.m07im08.espai_cultural_alexs

import android.content.Context
import android.widget.Toast
import org.json.JSONArray
import org.json.JSONObject
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException
import java.time.LocalDateTime

object JsonIO {
    fun llegirLlistat(context: Context, reserves: Boolean){
        val jsonFilePath = context.filesDir.toString() + "/json/esdeveniments.json"
        var esdeveniments: MutableList<Esdeveniment> = mutableListOf()
        try {
            val jsonFile = FileReader(jsonFilePath)
            val jsonArray = JSONArray(jsonFile.readText()) // Llegir com un JSONArray

            // Recórrer cada esdeveniment de la llista JSON
            for (i in 0 until jsonArray.length()) {
                val jsonObject = jsonArray.getJSONObject(i)

                // Llegir les entrades per a cada esdeveniment
                val entradesJsonArray = jsonObject.getJSONArray("entrades")
                val entrades: MutableList<Entrada> = mutableListOf()
                for (j in 0 until entradesJsonArray.length()) {
                    val entradaJsonObject = entradesJsonArray.getJSONObject(j)
                    val entrada = Entrada(
                        id = entradaJsonObject.getInt("id"),
                        nom_reserva = entradaJsonObject.getString("nom_reserva")
                    )
                    entrades.add(entrada)
                }

                // Crear l'objecte Esdeveniment i afegir-lo a la llista
                val esdeveniment = Esdeveniment(
                    id = jsonObject.getInt("id"),
                    nom = jsonObject.getString("nom"),
                    //imatge = jsonObject.getString("imatge"),
                    descripcio = jsonObject.getString("descripcio"),
                    data = LocalDateTime.parse(jsonObject.getString("data")),
                    idioma = jsonObject.getString("idioma"),
                    preu = jsonObject.getDouble("preu"),
                    numerat = jsonObject.getBoolean("numerat"),
                    tipus = jsonObject.getString("tipus"),
                    entrades = entrades,
                    especific1 = jsonObject.getString("especific1"),
                    especific2 = jsonObject.getString("especific2"),
                    especific3 = jsonObject.getString("especific3"),
                    especific4 = mutableListOf<String>().apply {
                        val especific4Array = jsonObject.getJSONArray("especific4")
                        for (j in 0 until especific4Array.length()) {
                            add(especific4Array.getString(j))
                        }
                    }
                )
                if (reserves){
                    if(esdeveniment.entrades.count() > 0) {
                        //afegirEsdeveniment(context,esdeveniment)
                        esdeveniments.add(esdeveniment)
                    }
                } else {
                    //afegirEsdeveniment(context,esdeveniment)
                    esdeveniments.add(esdeveniment)
                }
            }

        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(
                context,
                "Error en llegir el JSON d'esdeveniments: ${e.message}",
                Toast.LENGTH_LONG
            ).show()
            esdeveniments.clear()
        }
        Esdeveniment_Manager.esdeveniments = esdeveniments
    }
    fun escriureLlistat(context: Context) {
        val jsonFilePath = context.filesDir.toString() + "/json/esdeveniments.json"
        var esdeveniments: MutableList<Esdeveniment> = mutableListOf()
        try {
            val jsonArray = JSONArray()

            // Recórrer la llista d'esdeveniments i afegir-los a l'array JSON
            for (esdeveniment in Esdeveniment_Manager.esdeveniments) {
                val jsonObject = JSONObject().apply {
                    put("id", esdeveniment.id)
                    put("nom", esdeveniment.nom)
                    //put("imatge", esdeveniment.imatge)
                    put("descripcio", esdeveniment.descripcio)
                    put("data", esdeveniment.data.toString())
                    put("idioma", esdeveniment.idioma.toString())
                    put("preu", esdeveniment.preu.toDouble())
                    put("numerat", esdeveniment.numerat)
                    put("tipus", esdeveniment.tipus)
                    put("especific1", esdeveniment.especific1)
                    put("especific2", esdeveniment.especific2)
                    put("especific3", esdeveniment.especific3)
                    put("especific4", JSONArray(esdeveniment.especific4))

                    // Afegir les entrades a l'objecte JSON de l'esdeveniment
                    val entradesJsonArray = JSONArray()
                    for (entrada in esdeveniment.entrades) {
                        val entradaJsonObject = JSONObject().apply {
                            put("id", entrada.id)
                            put("nom_reserva", entrada.nom_reserva)
                        }
                        entradesJsonArray.put(entradaJsonObject)
                    }
                    put("entrades", entradesJsonArray)
                }

                jsonArray.put(jsonObject)
            }

            // Escriure l'array JSON a l'arxiu
            val fileWriter = FileWriter(jsonFilePath)
            fileWriter.use { it.write(jsonArray.toString()) }
            Toast.makeText(
                context,
                "Llistat d'esdeveniments desat",
                Toast.LENGTH_LONG
            ).show()
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(
                context,
                "Error en escriure el JSON d'esdeveniments: ${e.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }
    fun cercarEsdeveniment(esdevenimentCercat: Esdeveniment): Int{
        var index = -1
        var esdeveniments = Esdeveniment_Manager.esdeveniments
        for (i in 0 until esdeveniments.count()){
            if (esdevenimentCercat.id == esdeveniments[i].id){
                index = i
            }
        }
        return index
    }
    fun afegirEsdeveniment(context: Context, esdevenimentNou: Esdeveniment){
        Esdeveniment_Manager.esdeveniments.add(esdevenimentNou)
        escriureLlistat(context)
        llegirLlistat(context, false)
    }
    fun afegirEsdeveniment(context: Context, esdevenimentModificat: Esdeveniment, index: Int):Boolean{
        var afegit: Boolean
        try {
            Esdeveniment_Manager.esdeveniments[index] = esdevenimentModificat
            escriureLlistat(context)
            afegit = true
        } catch (e: Exception) {
            afegit = false
        }
        return afegit
    }
}