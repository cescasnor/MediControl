package com.example.proyecto_final.Address

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_final.R

class AddressAdapter(private val lista: List<Address>, private val listener: OnAddressClickListener) : RecyclerView.Adapter<AddressAdapter.AddressViewHolder>() {

    class AddressViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val btnEditarDireccion: ImageView = itemView.findViewById(R.id.ivEditCasa)
        val name: TextView = itemView.findViewById(R.id.txtNameAddress)
        val description: TextView = itemView.findViewById(R.id.txtDescriptionAddress)
        val aditionalInfo: TextView = itemView.findViewById(R.id.txtAditionalInfoAddress)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AddressViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.layout_address_card, parent, false)
        return AddressViewHolder(view)
    }

    override fun onBindViewHolder(holder: AddressViewHolder, position: Int) {
        val direccion = lista[position]
        holder.name.text = direccion.calle + ' ' + direccion.number
        holder.description.text =
            """
                Calle: ${direccion.calle} . Numero: ${direccion.number}, ${direccion.aditionalInfo}
            """.trimIndent()
        holder.aditionalInfo.text = direccion.reference

        holder.btnEditarDireccion.setOnClickListener {
            listener.onAddressEditClick(direccion)
        }
    }

    override fun getItemCount(): Int = lista.size
}
