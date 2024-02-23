package asb.m07im08.espai_cultural_alexs

import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.io.File

class EsdevenimentAdapter(val elements: List<Esdeveniment>, val onItemClick: (Esdeveniment) -> Unit) :
    RecyclerView.Adapter<EsdevenimentAdapter.ElementViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ElementViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.esdeveniment_layout, parent, false)
        return ElementViewHolder(view)
    }

    override fun onBindViewHolder(holder: ElementViewHolder, position: Int) {
        val element = elements[position]
        holder.bindElement(element)

        // Configurar el clic del elemento
        holder.itemView.setOnClickListener {
            onItemClick(element)
        }
    }

    override fun getItemCount(): Int = elements.size

    class ElementViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bindElement(esdeveniment: Esdeveniment) {
            val imgVwElement = itemView.findViewById<ImageView>(R.id.ImgListEsdeveniment)
            val imgElementPath = itemView.context.getFilesDir().toString() + "/imgelements/" + esdeveniment.image
            //val bitmap = BitmapFactory.decodeFile(imgElementPath)
            //val bitmap = BitmapFactory.decodeResource(itemView.resources, R.drawable.defaultelement)

            val bitmap = if (File(imgElementPath).exists()) {
                BitmapFactory.decodeFile(imgElementPath)
            } else {
                BitmapFactory.decodeResource(itemView.resources, R.drawable.escut)
            }
            imgVwElement.setImageBitmap(bitmap)


            val elementNom = itemView.findViewById<TextView>(R.id.NomListElement)
            elementNom.text = esdeveniment.nom
        }
    }
}