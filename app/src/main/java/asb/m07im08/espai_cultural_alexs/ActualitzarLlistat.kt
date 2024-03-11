package asb.m07im08.espai_cultural_alexs

import android.content.Context
import android.widget.Toast
import java.io.FileReader
import org.json.JSONArray
import org.json.JSONObject
import java.io.FileWriter
import java.io.IOException
import java.time.LocalDateTime

//aquesta classe permet llegir i escriure desde i a JSON

class ActualitzarLlistat (context: Context) {

    private val jsonFilePath = context.filesDir.toString() + "/json/esdeveniments.json"
    private var esdeveniments: MutableList<Esdeveniment> = mutableListOf()

    init {
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
                    imatgeHR = jsonObject.getString("imageHR"),
                    imatgeSR = jsonObject.getString("imageSR"),
                    descripcio = jsonObject.getString("descripcio"),
                    data = LocalDateTime.parse(jsonObject.getString("data")),
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
                esdeveniments.add(esdeveniment)
            }

        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(
                context,
                "Error en llegir el JSON d'esdeveniments: ${e.message}",
                Toast.LENGTH_LONG
            ).show()
        }
        Esdeveniment_Manager.esdeveniments = esdeveniments
    }


    fun guardarEsdeveniments(context: Context) {
        try {
            val jsonArray = JSONArray()

            // Recorrer la llista d'esdeveniments i afegir-los a l'array JSON
            for (esdeveniment in esdeveniments) {
                val jsonObject = JSONObject().apply {
                    put("id", esdeveniment.id)
                    put("nom", esdeveniment.nom)
                    put("imageHR", esdeveniment.imatgeHR)
                    put("imageSR", esdeveniment.imatgeSR)
                    put("descripcio", esdeveniment.descripcio)
                    put("data", esdeveniment.data.toString())
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
        } catch (e: IOException) {
            e.printStackTrace()
            Toast.makeText(
                context,
                "Error en escriure el JSON d'esdeveniments: ${e.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}
    /*
        esdeveniments = try {
            val jsonFile = FileReader(jsonFilePath)
            val listEsdevenimentsType = object : TypeToken<List<Esdeveniment>>() {}.type

            // Realizar la deserialización y almacenarla en una lista inmutable
            val esdevenimentsList: List<Esdeveniment> =
                Gson().fromJson(jsonFile, listEsdevenimentsType)

            // Convertir la lista inmutable a una lista mutable
            esdevenimentsList.toMutableList()
        } catch (e: Exception) {
            // Manejar la excepción aquí
            //if(Esdeveniment_Manager.debug){
            Toast.makeText(
                context,
                "Error en llegir el JSON d'esdeveniments: ${e.message}",
                Toast.LENGTH_LONG
            ).show()
            //}

            // Retorna una lista con un esdeveniment de muestra en caso de excepción para evitar valores nulos
            // Creació de la llista mutable d'Esdeveniments en cas de no aconseguir llegir el json
            mutableListOf<Esdeveniment>(
                Esdeveniment(
                    id = 1,
                    nom = "Peli Esdeveniment 1",
                    image = "imatge1.jpg",
                    descripcio = "Descripció esdeveniment 1",
                    data = LocalDateTime.now(),
                    preu = 10.5f,
                    numerat = false,
                    tipus = "Pel·lícula",
                    //entrades = MutableList(Esdeveniment_Manager.aforament) { _ -> Entrada(0, "Alexito Numerario") },
                    entrades = MutableList(Esdeveniment_Manager.aforament) { index ->
                        Entrada(
                            index,
                            "Alexito Numerario"
                        )
                    },
                    especific1 = "Frenando Prueba",
                    especific2 = 1999.toString(),
                    especific3 = 111.toString(),
                    especific4 = mutableListOf("Kenoa Ribes", "Catering Zeta Joan", "Garrison Porc")
                ),
                Esdeveniment(
                    id = 2,
                    nom = "Xerrada Esdeveniment 2",
                    image = "imatge1.jpg",
                    descripcio = "Descripció esdeveniment 2",
                    data = LocalDateTime.now(),
                    preu = 10.5f,
                    numerat = false,
                    tipus = "Xerrada",
                    entrades = MutableList(Esdeveniment_Manager.aforament) { _ ->
                        Entrada(
                            0,
                            "Alexito Numerista"
                        )
                    },
                    especific1 = "",
                    especific2 = "",
                    especific3 = "",
                    especific4 = mutableListOf("Alex Bolea", "Marcos Garcia", "Luis José")
                ),
                Esdeveniment(
                    id = 3,
                    nom = "Concert Esdeveniment 3",
                    image = "imatge1.jpg",
                    descripcio = "Descripció esdeveniment 3",
                    data = LocalDateTime.now(),
                    preu = 10.5f,
                    numerat = false,
                    tipus = "Pel·lícula",
                    entrades = MutableList(Esdeveniment_Manager.aforament) { _ ->
                        Entrada(
                            0,
                            "Alexito Numeroso"
                        )
                    },
                    especific1 = "Joan Guillems",
                    especific2 = "Orfeó Ca Talà",
                    especific3 = "Orquesta Simfònica del Va Llest",
                    especific4 = mutableListOf(
                        "Josep Violante",
                        "Anna Guitarrín",
                        "Mariví Piana",
                        "Guillermina Flautènsia",
                        "Ramon Tambort"
                    )
                ),
                Esdeveniment(
                    id = 4,
                    nom = "Nom Esdeveniment 4 sense entrades reservades",
                    image = "imatge1.jpg",
                    descripcio = "Descripció esdeveniment 4",
                    data = LocalDateTime.now(),
                    preu = 10.5f,
                    numerat = false,
                    tipus = "Pel·lícula",
                    entrades = MutableList(Esdeveniment_Manager.aforament) { _ ->
                        Entrada(
                            0,
                            "Alexito Numercio"
                        )
                    },
                    especific1 = "Frenando Prueba",
                    especific2 = 1999.toString(),
                    especific3 = 111.toString(),
                    especific4 = mutableListOf("Kenoa Ribes", "Catering Zeta Joan", "Garrison Porc")
                )
            ) // Retorna una llista amb 4 esdeveniments per defecte en cas d'excepció per evitar valors nuls
        }
        Esdeveniment_Manager.esdeveniments = esdeveniments
    }*/

    /*public fun llegirEsdeveniments(
        context: Context,
        jsonFilePath: String
    ): MutableList<Esdeveniment> {
        return try {
            val jsonFile = FileReader(jsonFilePath)
            val listEsdevenimentsType = object : TypeToken<List<Esdeveniment>>() {}.type

            // Realitzar la deserialització i emmagatzemar-la en una llista immutable
            val esdevenimentsList: List<Esdeveniment> =
                Gson().fromJson(jsonFile, listEsdevenimentsType)

            // Convertir la llista immutable a una llista mutable i retornar-la
            esdevenimentsList.toMutableList()
        } catch (e: Exception) {
            Toast.makeText(
                context,
                "Error en llegir el JSON d'esdeveniments: ${e.message}",
                Toast.LENGTH_LONG
            ).show()
            // Retornar una llista buida en cas d'error o fer alguna altra gestió d'errors
            mutableListOf()
        }*/
        /*//Función para filtrar una lista de esdevenimentos según ámbito
        fun loadField (field: Int): List<Esdeveniment>{
            var esdevenimentsField = mutableListOf<Esdeveniment>()

            //cribo per àmbit els esdeveniments a mostrar a la llista
            if (field > 0){
                for(i in 0 until esdeveniments.count()) {
                    if(esdeveniments[i].field == field){
                        esdevenimentsField.add(esdeveniments[i])
                    }
                }
            } else {
                esdevenimentsField = esdeveniments
            }
            return esdevenimentsField
        }

}*/