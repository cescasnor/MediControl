package com.example.proyecto_final

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_final.Product.Product
import com.example.proyecto_final.Product.ProductAdapter
import com.example.proyecto_final.Product.ProductDBHelper
import com.example.proyecto_final.Utils.AlertUtils
import com.example.proyecto_final.user.UserDBHelper

class MenuPrincipalActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var productoAdapter: ProductAdapter
    val dialogUtis = AlertUtils(this)
    val productDBHelper = ProductDBHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_principal)

        //Obtenemos todos los productos de la BD
        var productos = productDBHelper.obtenerProductos();

        //Formamos los Card Views de cada Producto
        recyclerView = findViewById(R.id.recyclerViewProds)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        productoAdapter = ProductAdapter(productos)
        recyclerView.adapter = productoAdapter
    }
}