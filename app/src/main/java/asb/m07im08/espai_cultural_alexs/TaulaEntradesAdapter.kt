package asb.m07im08.espai_cultural_alexs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class TaulaEntradesAdapter(val entrades: List<Entrada>, val onItemClick: (Entrada) -> Unit) :
    RecyclerView.Adapter<TaulaEntradesAdapter.TaulaEntradesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TaulaEntradesViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.entrades_layout, parent, false)
        return TaulaEntradesViewHolder(view)
    }

    override fun onBindViewHolder(holder: TaulaEntradesViewHolder, position: Int) {
        val entrada = entrades[position]
        holder.bindElement(entrada)

        // Configurar el clic del elemento
        holder.itemView.setOnClickListener {
            onItemClick(entrada)
        }
    }

    override fun getItemCount(): Int = entrades.size

    class TaulaEntradesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindElement(entrada: Entrada) {
            //assigno dades als textviews
            val tvNomReserva = itemView.findViewById<TextView>(R.id.tvNomReserva)
            tvNomReserva.text = entrada.nom_reserva
            val tvNumEntrada = itemView.findViewById<TextView>(R.id.tvNumEntrada)
            tvNumEntrada.text = entrada.id.toString()


        }
    }
}