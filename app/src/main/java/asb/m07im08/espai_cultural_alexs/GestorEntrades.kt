package asb.m07im08.espai_cultural_alexs

import android.content.Context
import androidx.core.text.buildSpannedString

object GestorEntrades {
    //retorna quantes entrades té algú
    fun entradesPerPersonaNumero(esdeveniment: Esdeveniment, nomReserva: String): Int {
        var numero = 0
        for (entrada in esdeveniment.entrades){
            if (entrada.nom_reserva == nomReserva){
                numero++
            }
        }
        return numero
    }
    fun entradesPerPersonaLlistat(esdeveniment: Esdeveniment, nomReserva: String): MutableList<Int> {
        var llistat: MutableList<Int> = mutableListOf()
        for (entrada in esdeveniment.entrades){
            if (entrada.nom_reserva == nomReserva){
                llistat.add(entrada.id)
            }
        }
        return llistat
    }
    // retorna el número d'entrades que no s'han reservat
    fun entradesDisponiblesNumero(esdeveniment: Esdeveniment): Int {
        var entradesReservades = esdeveniment.entrades.count()
        var entradesDisponibles = Esdeveniment_Manager.aforament - entradesReservades

        return entradesDisponibles
    }
    //retorna el llistat d'entrades disponibles
    fun entradesDisponiblesLlistat(esdeveniment: Esdeveniment): MutableList<Int> {
        val entradesOcupades = entradesReservadesLlistat(esdeveniment)
        val entradesDisponibles = mutableListOf<Int>()
        for (i in 1..100){
           if (!entradesOcupades.contains(i)){
               entradesDisponibles.add(i)
           }
        }
        return entradesDisponibles
    }
    //retorna el número d'entrades reservades
    fun entradesReservadesNumero(esdeveniment: Esdeveniment): Int {
        var entradesReservades = esdeveniment.entrades.count()
        return entradesReservades
    }
    //retorna el llistat d'entrades reservades
    fun entradesReservadesLlistat(esdeveniment: Esdeveniment): MutableList<Int> {
        var llistatEntradesReservades = mutableListOf<Int>()
        for (entrada in esdeveniment.entrades)
        {
            if (entrada.id > 0 && entrada.id <= 100){
                llistatEntradesReservades.add(entrada.id)
            }
        }
        return llistatEntradesReservades
    }
    fun trobarIdsDisponibles(entrades: MutableList<Entrada>, entradesAReservar: Int, aforament: Int): MutableList<Int> {
        val idsDisponibles = mutableListOf<Int>()
        var idsOcupades = trobarIdsOcupades(entrades,aforament)

        for (seient in 1..aforament) {//recorro cada seient de l'aforament
            if (!idsOcupades.contains(seient)) {//si el seient no està ocupat
                if (idsDisponibles.count() < entradesAReservar){//si encara no hem trobat el total de seients que volem assignar
                    idsDisponibles.add(seient)
                }
            }
        }
        return idsDisponibles
    }
    fun trobarIdsOcupades(entrades: MutableList<Entrada>, aforament: Int): MutableList<Int> {
        val idsOcupades = mutableListOf<Int>()
        for (entrada in entrades) {
            if (entrada.id in 1..aforament) {
                idsOcupades.add(entrada.id)
            }
        }
        return idsOcupades
    }
    fun assignarEntrada(esdevenimentAReservar: Esdeveniment, entradaAReservar: Entrada): Esdeveniment{
        var idUsat = false
        if (entradaAReservar.id >= 0 && entradaAReservar.id < Esdeveniment_Manager.aforament){
            for (entrada in esdevenimentAReservar.entrades){
                if (entradaAReservar.id == entrada.id)
                    idUsat = true
            }
            if (idUsat == false){
                esdevenimentAReservar.entrades.add(entradaAReservar)
            }
            idUsat = false
        }
        return esdevenimentAReservar
        //no es desa al json!!
    }
    fun assignarEntrades(esdevenimentAReservar: Esdeveniment, entrades: MutableList<Entrada>): Esdeveniment{
        entrades.forEachIndexed{ index, value, ->
            assignarEntrada(esdevenimentAReservar,value)
        }
        return esdevenimentAReservar
    }
    //localitzo entrada existent
    fun cercarEntrada(esdeveniment: Esdeveniment, entradaATrobar: Entrada): Int {
        var posicio: Int = -1
        var index = 0
        for (entrada in esdeveniment.entrades) {
            if (entrada == entradaATrobar){
                posicio = index
            }
            index++
        }
        return posicio
    }
    //elimino entrada existent, passant l'entrada com a paràmetre
    fun eliminarEntrada(context: Context, esdeveniment: Esdeveniment, entradaAEliminar: Entrada): Boolean {
        var eliminada = false
        var midaOriginal = -1
        midaOriginal = esdeveniment.entrades.count()
        var llistatModificat = mutableListOf<Entrada>()
        for (entrada in esdeveniment.entrades){
            if (entrada.id != entradaAEliminar.id){
                llistatModificat.add(entrada)
            }
        }
        esdeveniment.entrades.clear()
        val esdevenimentModificat = assignarEntrades(esdeveniment, llistatModificat)
        if (midaOriginal == (esdevenimentModificat.entrades.count() + 1)){
            eliminada = JsonIO.modificarEsdeveniment(context, esdevenimentModificat,JsonIO.cercarEsdeveniment(esdevenimentModificat))
        }
        return eliminada
    }
    fun eliminarEntrades(context: Context, esdevenimentOriginal: Esdeveniment, entradesAEliminar: MutableList<Entrada>): Boolean {
        var eliminada = false
        var midaOriginal = -1
        midaOriginal = esdevenimentOriginal.entrades.count()
        var llistatModificat = mutableListOf<Entrada>()
        for (entradaAEliminar in entradesAEliminar){//per cada entrada a eliminar
            for (entradaActual in esdevenimentOriginal.entrades){//per cada entrada de l'esdeveniment
                if (entradaActual.id != entradaAEliminar.id){//si les entrades d'esdeveniment i a eliminar no coincideixen
                    if (!llistatModificat.contains(entradaActual)){//si l'entrada no és al llistat definitiu d'entrades
                        llistatModificat.add(entradaActual)//afegir l'entrada actual al llistat definitiu, ja que aquesta no s'elimina
                    }
                }
            }
        }
        esdevenimentOriginal.entrades.clear()
        val esdevenimentModificat = assignarEntrades(esdevenimentOriginal, llistatModificat)
        //if (midaOriginal == (esdevenimentModificat.entrades.count() + 1)){
        if (midaOriginal != (esdevenimentModificat.entrades.count())){
            eliminada = JsonIO.modificarEsdeveniment(context, esdevenimentModificat,JsonIO.cercarEsdeveniment(esdevenimentModificat))
        } else {
            for (entradaE in entradesAEliminar){
                if (!esdevenimentModificat.entrades.contains(entradaE)) {
                    eliminada = true
                }
            }
        }
        return eliminada
    }

    fun modificarEntrada(context: Context, esdeveniment: Esdeveniment, entradaAEliminar: Entrada, entradaAModificar: Entrada): Boolean {
        var modificat = false
        var entradaEnLlista = mutableListOf<Entrada>()
        val indexEsdeveniment = JsonIO.cercarEsdeveniment(esdeveniment)
        var esdevenimentModificat: Esdeveniment
        entradaEnLlista.add(entradaAModificar)
        modificat = eliminarEntrada(context, esdeveniment, entradaAEliminar)
        if (modificat){
            esdevenimentModificat = assignarEntrades(Esdeveniment_Manager.esdeveniments[indexEsdeveniment], entradaEnLlista)
            modificat = JsonIO.modificarEsdeveniment(context, esdevenimentModificat, indexEsdeveniment)
        }
        return modificat
    }
    fun treureClaudatorsDelLlistat(string: String):String {
        var stringSenseClaudators: String
        stringSenseClaudators = string.substring(1, string.length - 1) // Elimina els caràcters de claudàtors

        return stringSenseClaudators
    }

    fun ordenarEntrades(entradesDesordenades: MutableList<Entrada>): MutableList<Entrada>{
        var idsOrdenats = mutableListOf<Int>()
        for (entrada in entradesDesordenades) {
            idsOrdenats.add(entrada.id)//Omplo el llistat desordenat
        }
        idsOrdenats = JsonIO.ordenarMutableListInt(idsOrdenats)
        var entradesOrdenades = mutableListOf<Entrada>()
        for (id in idsOrdenats) {
            for (entrada in entradesDesordenades) {
                if (entrada.id == id) {
                    if (!entradesOrdenades.contains(entrada)){
                        entradesOrdenades.add(entrada)
                    }
                }
            }
        }
        return entradesOrdenades
    }
}