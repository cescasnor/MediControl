package com.example.proyecto_final.Address

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.proyecto_final.DetalleVenta.DetalleVenta
import com.example.proyecto_final.Venta.Venta
import java.time.LocalDateTime

class AddressDBHelper(context: Context) : SQLiteOpenHelper(context, "address.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE address (
                idAddress INTEGER PRIMARY KEY AUTOINCREMENT,
                idClient TEXT,
                calle TEXT,
                number TEXT,
                reference TEXT,
                aditionalInfo TEXT,
                createdDate TEXT,
                lastModifiedDate TEXT
            )
        """
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS address")
        onCreate(db)
    }

    fun insertarDireccion(address: Address): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("idClient", address.idClient.toString())
            put("calle", address.calle)
            put("number", address.number)
            put("reference", address.reference)
            put("aditionalInfo", address.aditionalInfo)
            put("createdDate", address.createdDate.toString())
            put("lastModifiedDate", address.lastModifiedDate.toString())
        }

        return db.insert("address", null, values)
    }

    fun obtenerDirecciones(): List<Address> {
        val lista = mutableListOf<Address>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM address", null)
        while (cursor.moveToNext()) {
            val address = Address(
                idAddress = cursor.getInt(0),
                idClient = cursor.getInt(1),
                calle = cursor.getString(2),
                number = cursor.getString(3),
                reference = cursor.getString(4),
                aditionalInfo = cursor.getString(5),
                createdDate = LocalDateTime.parse(cursor.getString(6)),
                lastModifiedDate = LocalDateTime.parse(cursor.getString(7))
            )
            lista.add(address)
        }
        cursor.close()
        return lista
    }

    fun actualizarDireccion(address: Address): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("idClient", address.idClient.toString())
            put("calle", address.calle)
            put("number", address.number)
            put("reference", address.reference)
            put("aditionalInfo", address.aditionalInfo)
            put("createdDate", address.createdDate.toString())
            put("lastModifiedDate", address.lastModifiedDate.toString())
        }
        return db.update("address", values, "idAddress=?", arrayOf(address.idAddress.toString()))
    }

    fun eliminarDireccion(idAddress : Int): Int {
        val db = writableDatabase
        return db.delete("address", "idAddress=?", arrayOf(idAddress.toString()))
    }

    fun getDireccionByClient(idClient : Int): List<Address> {
        val lista = mutableListOf<Address>()
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM address WHERE idClient = ? ",
            arrayOf(idClient.toString())
        )
        while (cursor.moveToNext()) {
            val address = Address(
                idAddress = cursor.getInt(0),
                idClient = cursor.getInt(1),
                calle = cursor.getString(2),
                number = cursor.getString(3),
                reference = cursor.getString(4),
                aditionalInfo = cursor.getString(5),
                createdDate = LocalDateTime.parse(cursor.getString(6)),
                lastModifiedDate = LocalDateTime.parse(cursor.getString(7))
            )
            lista.add(address)
        }
        cursor.close()
        return lista
    }

    fun getDireccionById(idAddress : Int): List<Address> {
        val lista = mutableListOf<Address>()
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM address WHERE idAddress = ? ",
            arrayOf(idAddress.toString())
        )
        while (cursor.moveToNext()) {
            val address = Address(
                idAddress = cursor.getInt(0),
                idClient = cursor.getInt(1),
                calle = cursor.getString(2),
                number = cursor.getString(3),
                reference = cursor.getString(4),
                aditionalInfo = cursor.getString(5),
                createdDate = LocalDateTime.parse(cursor.getString(6)),
                lastModifiedDate = LocalDateTime.parse(cursor.getString(7))
            )
            lista.add(address)
        }
        cursor.close()
        return lista
    }
}
