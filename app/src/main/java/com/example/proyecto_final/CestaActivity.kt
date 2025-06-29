package com.example.proyecto_final

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_final.DetalleVenta.DetalleVenta
import com.example.proyecto_final.DetalleVenta.DetalleVentaDBHelper
import com.example.proyecto_final.Product.OnProductCompactClickListener
import com.example.proyecto_final.Product.Product
import com.example.proyecto_final.Product.ProductCompactAdapter
import com.example.proyecto_final.Product.ProductDBHelper
import com.example.proyecto_final.Utils.AlertUtils
import com.example.proyecto_final.Venta.Venta
import com.example.proyecto_final.Venta.VentaDBHelper
import com.example.proyecto_final.user.User
import com.example.proyecto_final.user.UserDBHelper
import java.time.LocalDateTime

class CestaActivity : AppCompatActivity(), OnProductCompactClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var productoCompactAdapter: ProductCompactAdapter
    val productDBHelper = ProductDBHelper(this)
    val ventaDBHelper = VentaDBHelper(this)
    val detalleVentaDBHelper = DetalleVentaDBHelper(this)
    val userDBHelper = UserDBHelper(this)
    var dialogUtil = AlertUtils(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cesta)

        //Ejecutamos Recycler Product
        manageDataRecyclerProduct()

        //Boton Pagar
        val btnProcederPago = findViewById<Button>(R.id.btnProcederaPagar)
        btnProcederPago.setOnClickListener{
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "message/rfc822"
                putExtra(Intent.EXTRA_EMAIL, arrayOf("angela.castro.noriega@gmail.com"))
                putExtra(Intent.EXTRA_SUBJECT, "Se realizó el Pago exitosamente")
                putExtra(Intent.EXTRA_TEXT, "Comparte tu compra!")
            }

            startActivity(Intent.createChooser(intent, "Enviar correo con:"))
            dialogUtil.mostrarDialogo("Exitoso","Venta Exitosa, se envió un correo con el detalle")
        }

        //Redirecciones del Footer
        val imageHouse = findViewById<ImageView>(R.id.imageCasa)
        imageHouse.setOnClickListener{
            val intent = Intent(this,MenuPrincipalActivity::class.java)
            startActivity(intent)
        }

        val imageCar = findViewById<ImageView>(R.id.imageCarrito)
        imageCar.setOnClickListener{
            val intent = Intent(this,CestaActivity::class.java)
            startActivity(intent)
        }

        val imageCat = findViewById<ImageView>(R.id.imageCat)
        imageCat.setOnClickListener{
            val intent = Intent(this,CuentaActivity::class.java)
            startActivity(intent)
        }

    }

    fun manageDataRecyclerProduct(){
        //Precargamos el Numero de Carrito
        loadNumbersProducts()

        //Obtenemos el Usuario
        var userFounded = getUserLoged();

        //Obtenemos la Venta guardada
        var ventaFounded = ventaDBHelper.getVentaByClient(userFounded.idClient)

        //Obtenemos los Detalles de Venta
        var detalleVentaFounded = detalleVentaDBHelper.getDetalleVentaByVenta(ventaFounded.get(0).idVenta)

        val mapProd = mutableMapOf<Int, Int>()
        for (detalle in detalleVentaFounded) {
            val idProd = detalle.idProducto
            val priceProd = detalle.subTotal
            if(mapProd.get(idProd) != null){
                mapProd.set(idProd , (mapProd[idProd] ?: 0) + 1)
            }else{
                mapProd.set(idProd,1)
            }
        }

        //Obtenemos todos los productos de la BD
        var productos = productDBHelper.obtenerProductos();
        val mapProdMaster = mutableMapOf<Int, Product>()
        for (prod in productos) {
            mapProdMaster.set(prod.idProduct,prod)
        }

        //Obtenemos la lista de Productos a Enviar
        val listProductsUnique = mutableListOf<Product>()
        for ((idProd, cantidad) in mapProd) {
            mapProdMaster[idProd]?.let { product ->
                listProductsUnique.add(product)
            }
        }

        //Enviamos la data al Recycler View
        buildRecyclerViewsByProduct(listProductsUnique, mapProd, R.id.recyclerViewProductsCesta)

        //Redirecciones del Footer
        val totalAmountText = findViewById<TextView>(R.id.txtTotalMount)
        totalAmountText.text = "Total: S/. " + String.format("%.2f", ventaFounded.get(0).totalAmount)
    }

    fun buildRecyclerViewsByProduct(listaProductos: List<Product>, mapQuantityProd : MutableMap<Int, Int>,  id : Int){
        recyclerView = findViewById(id)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        productoCompactAdapter = ProductCompactAdapter(listaProductos, mapQuantityProd,this)
        recyclerView.adapter = productoCompactAdapter
    }

    override fun onProductAddClick(product: Product) {
        var userFounded = getUserLoged();

        //Obtenemos la Venta guardada
        var ventaFounded = ventaDBHelper.getVentaByClient(userFounded.idClient)

        val venta = Venta(
            idVenta = ventaFounded.get(0).idVenta,
            idCliente = userFounded.idClient,
            fecha = LocalDateTime.now(),
            totalAmount = ventaFounded.get(0).totalAmount + product.price,
            status = "PRE_COMPRA"
        )
        ventaDBHelper.actualizarVenta(venta)

        val detalleVenta = DetalleVenta(
            idVenta = ventaFounded.get(0).idVenta,
            idProducto = product.idProduct,
            cantidad = 1,
            precioUnitario = product.price,
            subTotal = product.price
        )
        detalleVentaDBHelper.insertarDetalleVenta(detalleVenta)

        loadNumbersProducts()
        manageDataRecyclerProduct()
    }

    override fun onProductLessClick(product: Product) {
        var userFounded = getUserLoged();

        //Obtenemos la Venta guardada
        var ventaFounded = ventaDBHelper.getVentaByClient(userFounded.idClient)

        val venta = Venta(
            idVenta = ventaFounded.get(0).idVenta,
            idCliente = userFounded.idClient,
            fecha = LocalDateTime.now(),
            totalAmount = ventaFounded.get(0).totalAmount - product.price,
            status = "PRE_COMPRA"
        )
        ventaDBHelper.actualizarVenta(venta)

        //Buscamos los detalles de Venta asociados
        val detalleVenta = detalleVentaDBHelper.getDetalleVentaByVenta(venta.idVenta)
        for(dventa in detalleVenta){
            if(dventa.idProducto == product.idProduct){
                detalleVentaDBHelper.eliminarDetalleVenta(dventa.idDetalleVenta)
                break
            }
        }

        loadNumbersProducts()
        manageDataRecyclerProduct()
    }

    fun getUserLoged() : User {
        val prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val idUser = prefs.getInt("userId", -1)

        //Obtenemos el Usuario
        var userFounded = userDBHelper.getUserById(idUser);
        return userFounded.get(0)
    }

    fun loadNumbersProducts(){
        //Obtenemos el Usuario
        var userFounded = getUserLoged()

        //Obtenemos la Venta guardada
        var ventaFounded = ventaDBHelper.getVentaByClient(userFounded.idClient)

        if(!ventaFounded.isEmpty()){
            //Si la venta no es vacia obtenemos los detalles de venta
            var detallesDeVentas = detalleVentaDBHelper.getDetalleVentaByVenta(ventaFounded.get(0).idVenta)

            val numberProdsSelected = findViewById<TextView>(R.id.txtNumProdBar)
            numberProdsSelected.text = "( " + detallesDeVentas.size.toString() + " )"
        }
    }
}