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

class DireccionActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var addressAdapter: AddressAdapter
    val dialogUtis = AlertUtils(this)
    val clientDBHelper = ClientDBHelper(this)
    val userDBHelper = UserDBHelper(this)
    val addressDBHelper = AddressDBHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_direccion)
    }


    fun getUserLoged() : User {
        val prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val idUser = prefs.getInt("userId", -1)

        //Obtenemos el Usuario
        var userFounded = userDBHelper.getUserById(idUser);
        return userFounded.get(0)
    }
}