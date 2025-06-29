package com.example.proyecto_final.Product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_final.R

class ProductAdapter(private val lista: List<Product>, private val listener: OnProductClickListener) : RecyclerView.Adapter<ProductAdapter.ProductViewHolder>() {

    class ProductViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val btnAgregar: TextView = itemView.findViewById(R.id.btnAgregarCarrito)
        val name: TextView = itemView.findViewById(R.id.txtNombreProd)
        val sizeProduct: TextView = itemView.findViewById(R.id.textTamanoProducto)
        val description: TextView = itemView.findViewById(R.id.textDescription)
        val precio: TextView = itemView.findViewById(R.id.textPrecio)
        val image: ImageView = itemView.findViewById(R.id.ivProductPhoto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_product_card, parent, false)
        return ProductViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        val producto = lista[position]
        holder.name.text = producto.name
        holder.sizeProduct.text =  producto.sizeProduct
        holder.description.text = producto.description
        holder.precio.text = producto.price.toString()
        holder.image.setImageResource(producto.image)

        holder.btnAgregar.setOnClickListener {
            listener.onProductAddClick(producto)
        }
    }

    override fun getItemCount(): Int = lista.size
}
