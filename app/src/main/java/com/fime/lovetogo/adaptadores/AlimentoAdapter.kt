package com.fime.lovetogo.adaptadores

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.fime.lovetogo.R
import com.fime.lovetogo.domain.structs.StructAlimento
import java.util.*
import kotlin.collections.ArrayList

class AlimentoAdapter : RecyclerView.Adapter<AlimentoAdapter.ViewHolder>(){


    var alimentos: ArrayList<StructAlimento> = ArrayList()


    override fun onCreateViewHolder(viewGroup: ViewGroup, i: Int): ViewHolder {
        val v = LayoutInflater.from(viewGroup.context).inflate(R.layout.alimento_item, viewGroup, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, i: Int) {
        viewHolder.itemNombre.text = alimentos[i].nombre
        viewHolder.itemCantidad.text = "Cantidad: " + alimentos[i].cantidad.toString() + "Kg"
        viewHolder.itemCaducidad.text = "Caducidad: " + alimentos[i].caducidad
        viewHolder.itemImage.setImageResource(R.drawable.splash)
    }

    override fun getItemCount(): Int {
        return alimentos.size
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var itemImage: ImageView
        var itemNombre: TextView
        var itemCantidad: TextView
        var itemCaducidad: TextView
        var itemCategoria: TextView

        init {
            itemImage = view.findViewById(R.id.imagenAlimento)
            itemNombre = view.findViewById(R.id.nombreAlimento)
            itemCantidad = view.findViewById(R.id.cantidadAlimento)
            itemCaducidad = view.findViewById(R.id.caducidadAlimento)
            itemCategoria = view.findViewById(R.id.categoriaAlimento)
        }
    }

}