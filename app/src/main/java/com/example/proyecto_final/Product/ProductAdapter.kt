package com.example.proyecto_final.Product

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_final.R

class ProductAdapter(private val lista: List<Product>) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: ImageView = itemView.findViewById(R.id.txtNombreProd)
        val size: TextView = itemView.findViewById(R.id.textTamanoProducto)
        val description: TextView = itemView.findViewById(R.id.textDescription)
        val precio: TextView = itemView.findViewById(R.id.textPrecio)
        val image: ImageView = itemView.findViewById(R.id.ivProductPhoto)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_cliente_layout, parent, false)
        return ClienteViewHolder(view)
    }

    override fun onBindViewHolder(holder: ClienteViewHolder, position: Int) {
        val cliente = lista[position]

        holder.imagen.setImageURI(Uri.parse(""))
        holder.nombre.text = cliente.nombre
        holder.codigo.text = "Código: " + cliente.codigo
        holder.lineaCredito.text = "Línea Crédito: " + cliente.lineaCredito
        holder.celular.text = "Celular: " + cliente.celular
        holder.estado.text = "Estado: " + cliente.estado
        holder.genero.text = "Genero: " + cliente.genero
        holder.distrito.text = "Distrito : " + cliente.distrito
    }

    override fun getItemCount(): Int = lista.size
}
