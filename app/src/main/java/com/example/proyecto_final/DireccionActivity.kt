package com.example.proyecto_final

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
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
import com.example.proyecto_final.Client.Client
import com.example.proyecto_final.Client.ClientDBHelper
import com.example.proyecto_final.DetalleVenta.DetalleVentaDBHelper
import com.example.proyecto_final.Utils.AlertUtils
import com.example.proyecto_final.Venta.VentaDBHelper
import com.example.proyecto_final.user.User
import com.example.proyecto_final.user.UserDBHelper
import java.time.LocalDateTime


class DireccionActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var addressAdapter: AddressAdapter
    val dialogUtis = AlertUtils(this)
    val clientDBHelper = ClientDBHelper(this)
    val userDBHelper = UserDBHelper(this)
    val addressDBHelper = AddressDBHelper(this)
    val ventaDBHelper = VentaDBHelper(this)
    val detalleVentaDBHelper = DetalleVentaDBHelper(this)

    private var idAddress: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_direccion)

        idAddress = intent.getIntExtra("address_key", -1)

        if (idAddress != -1) {
            var adressSelected = addressDBHelper.getDireccionById(idAddress)

            val calle = findViewById<EditText>(R.id.txtCalle)
            val numero = findViewById<EditText>(R.id.txtNumero)
            val referencia = findViewById<EditText>(R.id.txtReferencia)
            val infoAdicional = findViewById<EditText>(R.id.txtInfoAdicional)

            calle.setText(adressSelected.get(0).calle)
            numero.setText(adressSelected.get(0).number)
            referencia.setText(adressSelected.get(0).reference)
            infoAdicional.setText(adressSelected.get(0).aditionalInfo)
        }

        //Boton guardar
        val btnSaveAddress = findViewById<Button>(R.id.btnSaveAddress)
        btnSaveAddress.setOnClickListener{
            buttonSaveListener()
        }

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
            val intent = Intent(this,CuentaActivity::class.java)
            startActivity(intent)
        }
    }

    fun buttonSaveListener(){
        val calle = findViewById<EditText>(R.id.txtCalle)
        val numero = findViewById<EditText>(R.id.txtNumero)
        val referencia = findViewById<EditText>(R.id.txtReferencia)
        val infoAdicional = findViewById<EditText>(R.id.txtInfoAdicional)

        if(
            calle.text.toString().isBlank() ||
            numero.text.toString().isBlank() ||
            referencia.text.toString().isBlank() ||
            infoAdicional.text.toString().isBlank()
        )
        {
            dialogUtis.mostrarDialogo("Advertencia", "Falta Ingresar Datos Obligatorios.")
            return
        }

        var userFounded = getUserLoged()
        var clientFounded = clientDBHelper.getClientById(userFounded.idClient)

        if (idAddress != -1) {
            val address = Address(
                idAddress = idAddress,
                idClient = clientFounded.get(0).idClient,
                calle = calle.text.toString(),
                number = numero.text.toString(),
                reference = referencia.text.toString(),
                aditionalInfo = infoAdicional.text.toString(),
                createdDate = LocalDateTime.now(),
                lastModifiedDate = LocalDateTime.now(),
            )
            addressDBHelper.actualizarDireccion(address)

        }else{
            val address = Address(
                idClient = clientFounded.get(0).idClient,
                calle = calle.text.toString(),
                number = numero.text.toString(),
                reference = referencia.text.toString(),
                aditionalInfo = infoAdicional.text.toString(),
                createdDate = LocalDateTime.now(),
                lastModifiedDate = LocalDateTime.now(),
            )
            addressDBHelper.insertarDireccion(address)
        }

        dialogUtis.mostrarDialogo("Satisfactorio", "Registro Exitoso!")
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, CuentaActivity::class.java)
            startActivity(intent)
            finish()
        }, 2000)
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