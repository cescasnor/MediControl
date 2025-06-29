package com.example.proyecto_final

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_final.Client.ClientDBHelper
import com.example.proyecto_final.DetalleVenta.DetalleVentaDBHelper
import com.example.proyecto_final.Product.OnProductCompactClickListener
import com.example.proyecto_final.Product.Product
import com.example.proyecto_final.Product.ProductAdapter
import com.example.proyecto_final.Product.ProductCompactAdapter
import com.example.proyecto_final.Product.ProductDBHelper
import com.example.proyecto_final.Utils.AlertUtils
import com.example.proyecto_final.Venta.VentaDBHelper
import com.example.proyecto_final.user.UserDBHelper

class CestaActivity : AppCompatActivity(), OnProductCompactClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var productoCompactAdapter: ProductCompactAdapter
    val dialogUtis = AlertUtils(this)
    val productDBHelper = ProductDBHelper(this)
    val ventaDBHelper = VentaDBHelper(this)
    val detalleVentaDBHelper = DetalleVentaDBHelper(this)
    val clientDBHelper = ClientDBHelper(this)
    val userDBHelper = UserDBHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cesta)

        //

        //Obtenemos todos los productos de la BD
        var productos = productDBHelper.obtenerProductos();

        buildRecyclerViewsByProduct(productos, R.id.recyclerViewProductsCesta)

    }

    fun buildRecyclerViewsByProduct(listaProductos: List<Product>, id : Int){
        println("listaProductos -> " + listaProductos)
        recyclerView = findViewById(id)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        productoCompactAdapter = ProductCompactAdapter(listaProductos, 23,this)
        recyclerView.adapter = productoCompactAdapter
    }

    override fun onProductAddClick(product: Product) {

    }

    override fun onProductLessClick(product: Product) {

    }
}