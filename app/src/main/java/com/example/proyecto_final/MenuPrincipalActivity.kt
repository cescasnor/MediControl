package com.example.proyecto_final

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.proyecto_final.Client.Client
import com.example.proyecto_final.Client.ClientDBHelper
import com.example.proyecto_final.DetalleVenta.DetalleVenta
import com.example.proyecto_final.DetalleVenta.DetalleVentaDBHelper
import com.example.proyecto_final.Product.OnProductClickListener
import com.example.proyecto_final.Product.Product
import com.example.proyecto_final.Product.ProductAdapter
import com.example.proyecto_final.Product.ProductDBHelper
import com.example.proyecto_final.Utils.AlertUtils
import com.example.proyecto_final.Venta.Venta
import com.example.proyecto_final.Venta.VentaDBHelper
import com.example.proyecto_final.user.User
import com.example.proyecto_final.user.UserDBHelper
import java.time.LocalDateTime

class MenuPrincipalActivity : AppCompatActivity(), OnProductClickListener  {

    private lateinit var recyclerView: RecyclerView
    private lateinit var productoAdapter: ProductAdapter
    val dialogUtis = AlertUtils(this)
    val productDBHelper = ProductDBHelper(this)
    val ventaDBHelper = VentaDBHelper(this)
    val detalleVentaDBHelper = DetalleVentaDBHelper(this)
    val clientDBHelper = ClientDBHelper(this)
    val userDBHelper = UserDBHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_menu_principal)

        //Obtenemos todos los productos de la BD
        var productos = productDBHelper.obtenerProductos();

        //Filtramos productos por Categoria
        var productFarmacia = filtrarProductosPorCategoria(productos, "Farmacia")
        buildRecyclerViewsByProduct(productFarmacia, R.id.recyclerViewProdsFarmacia)

        var productSalud = filtrarProductosPorCategoria(productos, "Salud")
        buildRecyclerViewsByProduct(productSalud, R.id.recyclerViewProdsSalud)

        var productCuidadoCabello = filtrarProductosPorCategoria(productos, "Cuidado del cabello")
        buildRecyclerViewsByProduct(productCuidadoCabello, R.id.recyclerViewProdsCabello)

        //Recargamos el # De productos asociado al Cliente
        loadNumbersProducts()

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
            val intent = Intent(this,MenuPrincipalActivity::class.java)
            startActivity(intent)
        }

    }

    fun buildRecyclerViewsByProduct(listaProductos: List<Product>, id : Int){
        recyclerView = findViewById(id)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        productoAdapter = ProductAdapter(listaProductos,this)
        recyclerView.adapter = productoAdapter
    }

    fun filtrarProductosPorCategoria(listaProductos: List<Product>, categoria: String): List<Product> {
        return listaProductos.filter { it.category.equals(categoria, ignoreCase = true) }
    }

    fun loadNumbersProducts(){
        println("Loading Number Prods ::: ")
        //Obtenemos el Usuario
        var userFounded = getUserLoged()

        //Obtenemos la Venta guardada
        var ventaFounded = ventaDBHelper.getVentaByClient(userFounded.idClient)
        println("Venta -> " + ventaFounded)

        if(!ventaFounded.isEmpty()){
            //Si la venta no es vacia obtenemos los detalles de venta
            var detallesDeVentas = detalleVentaDBHelper.getDetalleVentaByVenta(ventaFounded.get(0).idVenta)

            val numberProdsSelected = findViewById<TextView>(R.id.txtNumProdBar)
            numberProdsSelected.text = "( " + detallesDeVentas.size.toString() + " )"
        }
    }

    override fun onProductAddClick(product: Product) {
        println("Productio Agregado -> " + product);

        var userFounded = getUserLoged();

        //Obtenemos la Venta guardada
        var ventaFounded = ventaDBHelper.getVentaByClient(userFounded.idClient)

        if(ventaFounded.isEmpty()){
            val venta = Venta(
                idCliente = userFounded.idClient,
                fecha = LocalDateTime.now(),
                totalAmount = product.price,
                status = "PRE_COMPRA"
            )
            ventaDBHelper.insertarVenta(venta)

            val detalleVenta = DetalleVenta(
                idVenta = venta.idVenta,
                idProducto = product.idProduct,
                cantidad = 1,
                precioUnitario = product.price,
                subTotal = product.price
            )
            val detalleVentaId = detalleVentaDBHelper.insertarDetalleVenta(detalleVenta)

        }else{

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
            val detalleVentaId = detalleVentaDBHelper.insertarDetalleVenta(detalleVenta)

        }

        dialogUtis.mostrarDialogo("Exitoso!", "El producto fue añadido correctamente" )

        loadNumbersProducts();
    }

    fun getUserLoged() : User {
        val prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val idUser = prefs.getInt("userId", -1)

        //Obtenemos el Usuario
        var userFounded = userDBHelper.getUserById(idUser);
        return userFounded.get(0)
    }

}