package com.example.proyecto_final

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto_final.Product.Product
import com.example.proyecto_final.Product.ProductAdapter
import com.example.proyecto_final.Product.ProductDBHelper
import com.example.proyecto_final.Utils.AlertUtils
import com.example.proyecto_final.Utils.DataDumpUtils
import com.example.proyecto_final.user.UserDBHelper

class MainActivity : AppCompatActivity() {

    val dialogUtis = AlertUtils(this)
    val dataDumpFake = DataDumpUtils(this)
    val userDBHelper = UserDBHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        val btnIniciarSesion = findViewById<Button>(R.id.buttonIniciar)
        btnIniciarSesion.setOnClickListener{
            listenerBtnInicioSesion()
        }

        val btnRegistrate = findViewById<TextView>(R.id.textRegistarte)
        btnRegistrate.setOnClickListener{
            val intent = Intent(this,RegistrarActivity::class.java)
            startActivity(intent)
        }

        dataDumpFake.validateAndCreateProductos(this);
    }

    private fun listenerBtnInicioSesion() {

        val usuario = findViewById<EditText>(R.id.editTextUsuario)
        val password = findViewById<EditText>(R.id.editTextPassword)

        if(usuario.text.toString().isBlank() || password.text.toString().isBlank()){
            dialogUtis.mostrarDialogo("Advertencia", "Falta Ingresar Datos Obligatorios.")
            return
        }

        val listResult = userDBHelper.getUserByUsername(usuario.text.toString().uppercase())

        if(listResult.isEmpty()){
            dialogUtis.mostrarDialogo("Error", "El usuario ingresado no existe. Por favor proceder a registrarse")
            return
        }

        if(!listResult.get(0).password.equals(password.text.toString())){
            dialogUtis.mostrarDialogo("Error de Autenticación", "La constraseña ingresada es incorrecta.")
            return
        }

        // Guardarmos el Id del Usuario
        val prefs = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        prefs.edit().putInt("userId", listResult.get(0).idUser).apply()

        val intent = Intent(this,MenuPrincipalActivity::class.java)
        startActivity(intent)

    }

}