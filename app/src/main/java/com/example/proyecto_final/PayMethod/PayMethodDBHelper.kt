package com.example.proyecto_final.PayMethod

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.time.LocalDate
import java.time.LocalDateTime

class PayMethodDBHelper(context: Context) : SQLiteOpenHelper(context, "paymethod.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE paymethod (
                idPayMethod INTEGER PRIMARY KEY AUTOINCREMENT,
                idClient TEXT,
                number TEXT,
                ccv TEXT,
                ownerName TEXT,
                endDate TEXT,
                createdDate TEXT,
                lastModifiedDate TEXT
            )
        """
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS paymethod")
        onCreate(db)
    }

    fun insertarMetodoPago(paymethod: PayMethod): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("idClient", paymethod.idClient.toString())
            put("number", paymethod.number)
            put("ccv", paymethod.ccv)
            put("ownerName", paymethod.ownerName)
            put("endDate", paymethod.endDate.toString())
            put("createdDate", paymethod.createdDate.toString())
            put("lastModifiedDate", paymethod.lastModifiedDate.toString())
        }

        return db.insert("paymethod", null, values)
    }

    fun obtenerMetodosDePagos(): List<PayMethod> {
        val lista = mutableListOf<PayMethod>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM paymethod", null)
        while (cursor.moveToNext()) {
            val paymethod = PayMethod(
                idPayMethod = cursor.getInt(0),
                idClient = cursor.getInt(1),
                number = cursor.getString(2),
                ccv = cursor.getString(3),
                ownerName = cursor.getString(4),
                endDate = LocalDate.parse(cursor.getString(5)),
                createdDate = LocalDateTime.parse(cursor.getString(6)),
                lastModifiedDate = LocalDateTime.parse(cursor.getString(7))
            )
            lista.add(paymethod)
        }
        cursor.close()
        return lista
    }

    fun actualizarMetodoPago(paymethod: PayMethod): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("idClient", paymethod.idClient.toString())
            put("number", paymethod.number)
            put("ccv", paymethod.ccv)
            put("ownerName", paymethod.ownerName)
            put("endDate", paymethod.endDate.toString())
            put("createdDate", paymethod.createdDate.toString())
            put("lastModifiedDate", paymethod.lastModifiedDate.toString())
        }
        return db.update("paymethod", values, "idPayMethod=?", arrayOf(paymethod.idPayMethod.toString()))
    }

    fun eliminarMetodoPago(idpaymethod : Int): Int {
        val db = writableDatabase
        return db.delete("paymethod", "idPayMethod=?", arrayOf(idpaymethod.toString()))
    }

    fun getMetodoPagoByClient(idClient : Int): List<PayMethod> {
        val lista = mutableListOf<PayMethod>()
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM paymethod WHERE idClient = ? ",
            arrayOf(idClient.toString())
        )
        while (cursor.moveToNext()) {
            val paymethod = PayMethod(
                idPayMethod = cursor.getInt(0),
                idClient = cursor.getInt(1),
                number = cursor.getString(2),
                ccv = cursor.getString(3),
                ownerName = cursor.getString(4),
                endDate = LocalDate.parse(cursor.getString(5)),
                createdDate = LocalDateTime.parse(cursor.getString(6)),
                lastModifiedDate = LocalDateTime.parse(cursor.getString(7))
            )
            lista.add(paymethod)
        }
        cursor.close()
        return lista
    }
}
