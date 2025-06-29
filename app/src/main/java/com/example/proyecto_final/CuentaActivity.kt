package com.example.proyecto_final

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
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
import com.example.proyecto_final.Utils.AlertUtils
import com.example.proyecto_final.user.User
import com.example.proyecto_final.user.UserDBHelper

class CuentaActivity : AppCompatActivity(), OnAddressClickListener {

    private lateinit var recyclerView: RecyclerView
    private lateinit var addressAdapter: AddressAdapter
    val dialogUtis = AlertUtils(this)
    val clientDBHelper = ClientDBHelper(this)
    val userDBHelper = UserDBHelper(this)
    val addressDBHelper = AddressDBHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_cuenta)

        var userFounded = getUserLoged()
        var clientFounded = clientDBHelper.getClientById(userFounded.idClient)
        var addressFounded = addressDBHelper.getDireccionByClient(clientFounded.get(0).idClient)

        val txtNameClient = findViewById<TextView>(R.id.textNombreCliente)
        txtNameClient.text = clientFounded.get(0).firstName + ' ' + clientFounded.get(0).lastName

        buildRecyclerViewsByAddress(addressFounded, R.id.recyclerViewAddress)

        //Boton Añadir Dirección
        val btnAddAddress = findViewById<Button>(R.id.btnDireccion)
        btnAddAddress.setOnClickListener{
            val intent = Intent(this,DireccionActivity::class.java)
            startActivity(intent)
        }
    }

    fun buildRecyclerViewsByAddress(listaDireccion: List<Address>, id : Int){
        recyclerView = findViewById(id)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        addressAdapter = AddressAdapter(listaDireccion,this)
        recyclerView.adapter = addressAdapter
    }

    override fun onAddressEditClick(address: Address) {

    }

    fun getUserLoged() : User {
        val prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val idUser = prefs.getInt("userId", -1)

        //Obtenemos el Usuario
        var userFounded = userDBHelper.getUserById(idUser);
        return userFounded.get(0)
    }
}