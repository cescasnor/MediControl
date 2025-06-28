package com.example.proyecto_final.Utils

import android.content.Context
import androidx.appcompat.app.AlertDialog

class AlertUtils(private val context: Context) {

    fun mostrarDialogo(title: String, message : String) {
        val builder = AlertDialog.Builder(this.context)
        builder.setTitle(title)
        builder.setMessage(message)
        builder.setPositiveButton("Aceptar", null)
        builder.show()
    }
}