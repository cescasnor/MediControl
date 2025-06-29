package com.example.proyecto_final.DetalleVenta

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.time.LocalDateTime

class DetalleVentaDBHelper(context: Context) : SQLiteOpenHelper(context, "detalleventa.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE detalleventa (
                idDetalleVenta INTEGER PRIMARY KEY AUTOINCREMENT,
                idVenta TEXT,
                idProducto TEXT,
                cantidad TEXT,
                precioUnitario TEXT,
                subTotal TEXT
            )
        """
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS detalleventa")
        onCreate(db)
    }

    fun insertarDetalleVenta(detalleventa: DetalleVenta): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("idVenta", detalleventa.idVenta)
            put("idProducto", detalleventa.idProducto)
            put("cantidad", detalleventa.cantidad.toString())
            put("precioUnitario", detalleventa.precioUnitario.toString())
            put("subTotal", detalleventa.subTotal.toString())
        }

        return db.insert("detalleventa", null, values)
    }

    fun obtenerDetalleVentas(): List<DetalleVenta> {
        val lista = mutableListOf<DetalleVenta>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM detalleventa", null)
        while (cursor.moveToNext()) {
            val detalleventa = DetalleVenta(
                idDetalleVenta = cursor.getInt(0),
                idVenta = cursor.getInt(1),
                idProducto = cursor.getInt(2),
                cantidad = cursor.getInt(3),
                precioUnitario = cursor.getString(4).toDouble(),
                subTotal = cursor.getString(5).toDouble()
            )
            lista.add(detalleventa)
        }
        cursor.close()
        return lista
    }

    fun actualizarDetalleVenta(detalleventa: DetalleVenta): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("idVenta", detalleventa.idVenta)
            put("idProducto", detalleventa.idProducto)
            put("cantidad", detalleventa.cantidad)
            put("precioUnitario", detalleventa.precioUnitario)
            put("subTotal", detalleventa.subTotal)
        }
        return db.update("detalleventa", values, "idDetalleVenta=?", arrayOf(detalleventa.idDetalleVenta.toString()))
    }

    fun eliminarDetalleVenta(idDetalleVenta: Int): Int {
        val db = writableDatabase
        return db.delete("detalleventa", "idDetalleVenta=?", arrayOf(idDetalleVenta.toString()))
    }

    fun getDetalleVentaByVenta(idVenta : Int): List<DetalleVenta> {
        val lista = mutableListOf<DetalleVenta>()
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM detalleventa WHERE idVenta = ? ",
            arrayOf(idVenta.toString())
        )
        while (cursor.moveToNext()) {
            val detalleventa = DetalleVenta(
                idDetalleVenta = cursor.getInt(0),
                idVenta = cursor.getInt(1),
                idProducto = cursor.getInt(2),
                cantidad = cursor.getInt(3),
                precioUnitario = cursor.getString(4).toDouble(),
                subTotal = cursor.getString(5).toDouble()
            )
            lista.add(detalleventa)
        }
        cursor.close()
        return lista
    }
}
