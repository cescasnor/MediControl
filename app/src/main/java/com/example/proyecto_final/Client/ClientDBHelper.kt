package com.example.proyecto_final.Client

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.time.LocalDateTime

class ClientDBHelper(context: Context) : SQLiteOpenHelper(context, "client.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE client (
                idClient INTEGER PRIMARY KEY AUTOINCREMENT,
                firstName TEXT,
                lastName TEXT,
                createdDate TEXT,
                lastModifiedDate TEXT
            )
        """
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS client")
        onCreate(db)
    }

    fun insertarCliente(client: Client): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("firstName", client.firstName)
            put("lastName", client.lastName)
            put("createdDate", client.createdDate.toString())
            put("lastModifiedDate", client.lastModifiedDate.toString())
        }

        return db.insert("client", null, values)
    }

    fun obtenerClientes(): List<Client> {
        val lista = mutableListOf<Client>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM client", null)
        while (cursor.moveToNext()) {
            val cliente = Client(
                idClient = cursor.getInt(0),
                firstName = cursor.getString(1),
                lastName = cursor.getString(2),
                createdDate = LocalDateTime.parse(cursor.getString(3)),
                lastModifiedDate = LocalDateTime.parse(cursor.getString(4))
            )
            lista.add(cliente)
        }
        cursor.close()
        return lista
    }

    fun actualizarCliente(cliente: Client): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("firstName", cliente.firstName)
            put("lastName", cliente.lastName)
            put("createdDate", cliente.createdDate.toString())
            put("lastModifiedDate", cliente.lastModifiedDate.toString())
        }
        return db.update("cliente", values, "idClient=?", arrayOf(cliente.idClient.toString()))
    }

    fun eliminarCliente(idClient: Int): Int {
        val db = writableDatabase
        return db.delete("cliente", "idClient=?", arrayOf(idClient.toString()))
    }
}
