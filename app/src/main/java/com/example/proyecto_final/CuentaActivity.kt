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
import com.example.proyecto_final.Address.Address
import com.example.proyecto_final.Address.AddressAdapter
import com.example.proyecto_final.Address.AddressDBHelper
import com.example.proyecto_final.Address.OnAddressClickListener
import com.example.proyecto_final.Client.ClientDBHelper
import com.example.proyecto_final.DetalleVenta.DetalleVentaDBHelper
import com.example.proyecto_final.Utils.AlertUtils
import com.example.proyecto_final.Venta.VentaDBHelper
import com.example.proyecto_final.user.User
import com.example.proyecto_final.user.UserDBHelper

class CuentaActivity : AppCompatActivity(), OnAddressClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var addressAdapter: AddressAdapter
    val dialogUtis = AlertUtils(this)
    val clientDBHelper = ClientDBHelper(this)
    val userDBHelper = UserDBHelper(this)
    val addressDBHelper = AddressDBHelper(this)
    val ventaDBHelper = VentaDBHelper(this)
    val detalleVentaDBHelper = DetalleVentaDBHelper(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cuenta)

        loadPageActivity();

        //Precargamoos el # de Productos
        loadNumbersProducts()

        //Boton Añadir Dirección
        val btnAddAddress = findViewById<Button>(R.id.btnDireccion)
        btnAddAddress.setOnClickListener{
            val intent = Intent(this,DireccionActivity::class.java)
            startActivity(intent)
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

    fun loadPageActivity(){
        var userFounded = getUserLoged()
        var clientFounded = clientDBHelper.getClientById(userFounded.idClient)
        var addressFounded = addressDBHelper.getDireccionByClient(clientFounded.get(0).idClient)


        val txtNameClient = findViewById<TextView>(R.id.textNombreCliente)
        txtNameClient.text = clientFounded.get(0).firstName + ' ' + clientFounded.get(0).lastName

        println("Direccion encontrada para el client Id " + clientFounded.get(0).idClient)
        println(addressFounded)
        println("TOTAL " + addressFounded.size)

        buildRecyclerViewsByAddress(addressFounded, R.id.recyclerViewAddress)
    }

    fun buildRecyclerViewsByAddress(listaDireccion: List<Address>, id : Int){
        recyclerView = findViewById(id)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        addressAdapter = AddressAdapter(listaDireccion,this)
        recyclerView.adapter = addressAdapter
    }

    override fun onAddressEditClick(address: Address) {
        val intent = Intent(this, DireccionActivity::class.java)
        intent.putExtra("address_key", address.idAddress)
        startActivity(intent)

    }

    override fun onAddressDeleteClick(address: Address) {
        addressDBHelper.eliminarDireccion(address.idAddress)
        loadPageActivity()
    }

    fun getUserLoged() : User {
        val prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val idUser = prefs.getInt("userId", -1)

        //Obtenemos el Usuario
        var userFounded = userDBHelper.getUserById(idUser);
        return userFounded.get(0)
    }

    fun loadNumbersProducts(){
        println("Loading Number Prods ::: ")
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