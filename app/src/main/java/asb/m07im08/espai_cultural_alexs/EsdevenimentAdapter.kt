package asb.m07im08.espai_cultural_alexs

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class EsdevenimentAdapter(val esdeveniments: List<Esdeveniment>, val onItemClick: (Esdeveniment, Int) -> Unit) :
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
            //onItemClick(esdeveniment)
            onItemClick(esdeveniment,position)
        }
    }

    /*private fun onItemClick(esdeveniment: Esdeveniment, position: Int) {
        Esdeveniment_Manager.index = position
    }*/

    override fun getItemCount(): Int = esdeveniments.size

    class EsdevenimentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindElement(esdeveniment: Esdeveniment) {
            val imgVwElement = itemView.findViewById<ImageView>(R.id.ImgListEsdeveniment)
            val imgElementPath = itemView.context.getFilesDir().toString() + "/imgelements/" + "hr." + esdeveniment.image
            //val bitmap = BitmapFactory.decodeFile(imgElementPath)
            //val bitmap = BitmapFactory.decodeResource(itemView.resources, R.drawable.defaultelement)

            val bitmap = if (File(imgElementPath).exists()) {
                BitmapFactory.decodeFile(imgElementPath)
            } else {
                BitmapFactory.decodeResource(itemView.resources, R.drawable.escut)
            }
            imgVwElement.setImageBitmap(bitmap)

            //assigno dades als textviews
            val esdevenimentNom = itemView.findViewById<TextView>(R.id.NomListEsdeveniment)
            esdevenimentNom.text = esdeveniment.nom
            val esdevenimentData = itemView.findViewById<TextView>(R.id.dataHoraEsdeveniment)
            //esdevenimentData.text = esdeveniment.data.toString()
            esdevenimentData.text = ConversorDateTime.convertir(esdeveniment.data)
            val esdevenimentPreu = itemView.findViewById<TextView>(R.id.preuEsdeveniment)
            esdevenimentPreu.text = "Preu: " + esdeveniment.preu.toString() + "€"


        }
    }
}