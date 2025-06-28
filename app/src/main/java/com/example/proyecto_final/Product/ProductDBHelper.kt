package com.example.proyecto_final.Product

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ProductDBHelper(context: Context) : SQLiteOpenHelper(context, "product.db", null, 2) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE product (
                idProduct INTEGER PRIMARY KEY AUTOINCREMENT,
                sizeProduct TEXT,
                name TEXT,
                description TEXT,
                price TEXT,
                stock TEXT,
                category TEXT,
                image TEXT
            )
        """
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS product")
        onCreate(db)
    }

    fun insertarProducto(product: Product): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", product.name)
            put("sizeProduct", product.sizeProduct)
            put("description", product.description)
            put("price", product.price.toString())
            put("stock", product.stock.toString())
            put("category", product.category.toString())
            put("image", product.image)
        }

        return db.insert("product", null, values)
    }

    fun obtenerProductos(): List<Product> {
        val lista = mutableListOf<Product>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM product", null)
        while (cursor.moveToNext()) {
            val producto = Product(
                idProduct = cursor.getInt(0),
                name = cursor.getString(1),
                sizeProduct = cursor.getString(2),
                description = cursor.getString(3),
                price = cursor.getDouble(4),
                stock = cursor.getInt(5),
                category = cursor.getString(6),
                image = cursor.getInt(7)
            )
            lista.add(producto)
        }
        cursor.close()
        return lista
    }

    fun actualizarProducto(producto: Product): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("name", producto.name)
            put("sizeProduct", producto.sizeProduct)
            put("description", producto.description)
            put("price", producto.price)
            put("stock", producto.stock)
            put("category", producto.category)
            put("image", producto.image)
        }
        return db.update("product", values, "idProduct=?", arrayOf(producto.idProduct.toString()))
    }

    fun eliminarProducto(idProducto: Int): Int {
        val db = writableDatabase
        return db.delete("product", "idProduct=?", arrayOf(idProducto.toString()))
    }
}
