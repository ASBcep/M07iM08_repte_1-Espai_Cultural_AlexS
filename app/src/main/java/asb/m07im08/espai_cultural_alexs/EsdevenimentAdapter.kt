package asb.m07im08.espai_cultural_alexs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class EsdevenimentAdapter(val esdeveniments: List<Esdeveniment>, val onItemClick: (Esdeveniment) -> Unit) :
    RecyclerView.Adapter<EsdevenimentAdapter.EsdevenimentViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EsdevenimentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.esdeveniment_layout, parent, false)
        return EsdevenimentViewHolder(view)
    }

    override fun onBindViewHolder(holder: EsdevenimentViewHolder, position: Int) {
        val esdeveniment = esdeveniments[position]
        holder.bindElement(esdeveniment)

        // Configurar el clic del elemento
        holder.itemView.setOnClickListener {
            onItemClick(esdeveniment)
        }
    }

    override fun getItemCount(): Int = esdeveniments.size

    class EsdevenimentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindElement(esdeveniment: Esdeveniment) {
            val ivListEsdeveniment = itemView.findViewById<ImageView>(R.id.ivListEsdeveniment)

            GestorImatge.inserirImatgeSR(esdeveniment.id.toString(), itemView.context, ivListEsdeveniment)

            //assigno dades als textviews
            val esdevenimentNom = itemView.findViewById<TextView>(R.id.NomListEsdeveniment)
            esdevenimentNom.text = esdeveniment.nom
            val esdevenimentData = itemView.findViewById<TextView>(R.id.dataHoraEsdeveniment)
            //esdevenimentData.text = esdeveniment.data.toString()
            esdevenimentData.text = ConversorDateTime.convertir(esdeveniment.data)
            val esdevenimentPreu = itemView.findViewById<TextView>(R.id.preuEsdeveniment)
            esdevenimentPreu.text = "Preu: " + esdeveniment.preu.toString() + "€"
            val esdevenimentEntrades = itemView.findViewById<TextView>(R.id.entradesDisponibles)
            esdevenimentEntrades.text  = "Localitats disponibles: " + GestorEntrades.entradesDisponiblesNumero(esdeveniment)
        }
    }
}
