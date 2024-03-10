package asb.m07im08.espai_cultural_alexs

import android.content.Context
import android.widget.Toast
import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.reflect.TypeToken
import java.io.FileReader
import com.google.gson.Gson
import java.time.LocalDateTime

//aquesta classe permet llegir i escriure desde i a JSON

class ActualitzarLlistat (context: Context) {

    private val jsonFilePath = context.filesDir.toString() + "/json/esdeveniments.json"
    private val esdeveniments: MutableList<Esdeveniment>

    //inicialitzo la llista LOCAL d'esdeveniments
    init {
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
    }

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
        }*/
    }
}