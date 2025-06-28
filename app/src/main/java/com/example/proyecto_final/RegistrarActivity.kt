package com.example.proyecto_final

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.proyecto_final.Client.Client
import com.example.proyecto_final.Client.ClientDBHelper
import com.example.proyecto_final.user.User
import com.example.proyecto_final.Utils.AlertUtils
import com.example.proyecto_final.user.UserDBHelper
import java.time.LocalDateTime

class RegistrarActivity : AppCompatActivity() {

    val dialogUtis = AlertUtils(this)
    val clientDBHelper = ClientDBHelper(this)
    val userDBHelper = UserDBHelper(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_registrar)

        val btnRegistrar = findViewById<Button>(R.id.btnRegistrar)
        btnRegistrar.setOnClickListener{
            listenerBtnRegistrar()
        }

        val btnIniciarSesion = findViewById<TextView>(R.id.textIniciarSesion)
        btnIniciarSesion.setOnClickListener{
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
        }

    }

    private fun listenerBtnRegistrar() {
        val nombres = findViewById<EditText>(R.id.txtNombres)
        val apellidos = findViewById<EditText>(R.id.txtApellidos)
        val email = findViewById<EditText>(R.id.txtEmail)
        val telefono = findViewById<EditText>(R.id.txtTelefono)
        val clave = findViewById<EditText>(R.id.txtClave)
        val claveRepeat = findViewById<EditText>(R.id.txtClaveRepeat)

        if(
            nombres.text.toString().isBlank() ||
            apellidos.text.toString().isBlank() ||
            email.text.toString().isBlank() ||
            telefono.text.toString().isBlank() ||
            clave.text.toString().isBlank() ||
            claveRepeat.text.toString().isBlank()
            )
        {
            dialogUtis.mostrarDialogo("Advertencia", "Falta Ingresar Datos Obligatorios.")
            return
        }

        if(!clave.text.toString().equals(claveRepeat.text.toString()))
        {
            dialogUtis.mostrarDialogo("Advertencia", "Las claves ingresadas no coinciden.")
            return
        }

        val listResult = userDBHelper.getUserByUsername(email.text.toString())

        if(listResult.isNotEmpty()){
            dialogUtis.mostrarDialogo("Error", "El usuario ingresado ya se encuentra registrado. Por favor seleccionar Recuperar Contraseña o Ingresar otro Correo")
            return
        }

        val client = Client(
            firstName = nombres.text.toString(),
            lastName = apellidos.text.toString(),
            createdDate = LocalDateTime.now(),
            lastModifiedDate = LocalDateTime.now(),
        )
        val resultIdClient = clientDBHelper.insertarCliente(client)

        val user = User(
            idClient = resultIdClient.toInt(),
            username = email.text.toString(),
            password = clave.text.toString(),
            createdDate = LocalDateTime.now(),
            lastModifiedDate = LocalDateTime.now(),
        )
        val resultIdUser = userDBHelper.insertarUser(user)

        dialogUtis.mostrarDialogo("Satisfactorio", "Registro Exitoso!")
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)

    }

}