package com.example.proyecto_final.Product

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_final.R

class ProductCompactAdapter(private val lista: List<Product>,private val quantityProdMap : MutableMap<Int, Int>, private val listener: OnProductCompactClickListener) : RecyclerView.Adapter<ProductCompactAdapter.ProductCompactViewHolder>() {

    class ProductCompactViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val btnAddProd: TextView = itemView.findViewById(R.id.txtAddProducto)
        val btnLessProd: TextView = itemView.findViewById(R.id.txtLessProducto)
        val txtNumberProds: TextView = itemView.findViewById(R.id.txtNumberProduct)

        val name: TextView = itemView.findViewById(R.id.txtNombreProd)
        val sizeProduct: TextView = itemView.findViewById(R.id.textTamanoProducto)
        val precio: TextView = itemView.findViewById(R.id.textPrecio)
        val image: ImageView = itemView.findViewById(R.id.ivProductPhoto)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductCompactViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.activity_product_compact_card, parent, false)
        return ProductCompactViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProductCompactViewHolder, position: Int) {
        val producto = lista[position]
        println("Producto : " + producto)
        holder.name.text = producto.name
        holder.sizeProduct.text =  producto.sizeProduct
        holder.precio.text = producto.price.toString()
        holder.txtNumberProds.text = quantityProdMap[producto.idProduct].toString()
        holder.image.setImageResource(producto.image)

        holder.btnAddProd.setOnClickListener {
            listener.onProductAddClick(producto)
        }

        holder.btnLessProd.setOnClickListener {
            listener.onProductLessClick(producto)
        }
    }

    override fun getItemCount(): Int = lista.size
}
