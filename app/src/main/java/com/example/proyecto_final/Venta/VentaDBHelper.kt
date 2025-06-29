package com.example.proyecto_final.Venta

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.time.LocalDateTime

class VentaDBHelper(context: Context) : SQLiteOpenHelper(context, "venta.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE venta (
                idVenta INTEGER PRIMARY KEY AUTOINCREMENT,
                idCliente TEXT,
                fecha TEXT,
                totalAmount TEXT,
                status TEXT
            )
        """
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS venta")
        onCreate(db)
    }

    fun insertarVenta(venta: Venta): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("idVenta", venta.idVenta)
            put("idCliente", venta.idCliente)
            put("fecha", venta.fecha.toString())
            put("totalAmount", venta.totalAmount.toString())
            put("status", venta.status)
        }

        return db.insert("venta", null, values)
    }

    fun obtenerVentas(): List<Venta> {
        val lista = mutableListOf<Venta>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM venta", null)
        while (cursor.moveToNext()) {
            val venta = Venta(
                idVenta = cursor.getInt(0),
                idCliente = cursor.getInt(1),
                fecha = LocalDateTime.parse(cursor.getString(2)),
                totalAmount = cursor.getString(3).toDouble(),
                status = cursor.getString(4),
            )
            lista.add(venta)
        }
        cursor.close()
        return lista
    }

    fun actualizarVenta(venta: Venta): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("idCliente", venta.idCliente)
            put("fecha", venta.fecha.toString())
            put("totalAmount", venta.totalAmount.toString())
        }
        return db.update("venta", values, "idVenta=?", arrayOf(venta.idVenta.toString()))
    }

    fun eliminarVenta(idVenta: Int): Int {
        val db = writableDatabase
        return db.delete("venta", "idVenta=?", arrayOf(idVenta.toString()))
    }

    fun getVentaByClient(idClient : Int): List<Venta> {
        val lista = mutableListOf<Venta>()
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM venta WHERE idCliente = ? ",
            arrayOf(idClient.toString())
        )
        while (cursor.moveToNext()) {
            val venta = Venta(
                idVenta = cursor.getInt(0),
                idCliente = cursor.getInt(1),
                fecha = LocalDateTime.parse(cursor.getString(2)),
                totalAmount = cursor.getString(3).toDouble(),
                status = cursor.getString(4),
            )
            lista.add(venta)
        }
        cursor.close()
        return lista
    }
}
