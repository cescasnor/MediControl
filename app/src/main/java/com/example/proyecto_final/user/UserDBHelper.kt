package com.example.proyecto_final.user

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.time.LocalDateTime

class UserDBHelper(context: Context) : SQLiteOpenHelper(context, "user.db", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE user (
                iduser INTEGER PRIMARY KEY AUTOINCREMENT,
                idClient TEXT,
                username TEXT,
                password TEXT,
                createdDate TEXT,
                lastModifiedDate TEXT
            )
        """
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS user")
        onCreate(db)
    }

    fun insertarUser(user: User): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("idClient", user.idClient)
            put("username", user.username)
            put("password", user.password)
            put("createdDate", user.createdDate.toString())
            put("lastModifiedDate", user.lastModifiedDate.toString())
        }

        return db.insert("user", null, values)
    }

    fun obtenerUsers(): List<User> {
        val lista = mutableListOf<User>()
        val db = readableDatabase
        val cursor = db.rawQuery("SELECT * FROM user", null)
        while (cursor.moveToNext()) {
            val user = User(
                idUser = cursor.getInt(0),
                idClient = cursor.getInt(1),
                username = cursor.getString(2),
                password = cursor.getString(3),
                createdDate = LocalDateTime.parse(cursor.getString(4)),
                lastModifiedDate = LocalDateTime.parse(cursor.getString(5))
            )
            lista.add(user)
        }
        cursor.close()
        return lista
    }

    fun actualizarUser(user: User): Int {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("idClient", user.idClient)
            put("username", user.username)
            put("password", user.password)
            put("createdDate", user.createdDate.toString())
            put("lastModifiedDate", user.lastModifiedDate.toString())
        }
        return db.update("user", values, "idUser=?", arrayOf(user.idUser.toString()))
    }

    fun eliminarUser(idUser: Int): Int {
        val db = writableDatabase
        return db.delete("user", "idUser=?", arrayOf(idUser.toString()))
    }

    fun getUserByUsername(username : String): List<User> {
        val lista = mutableListOf<User>()
        val db = readableDatabase
        val cursor = db.rawQuery(
            "SELECT * FROM user WHERE username = ? LIMIT 1",
            arrayOf(username)
        )
        while (cursor.moveToNext()) {
            val user = User(
                idUser = cursor.getInt(0),
                idClient = cursor.getInt(1),
                username = cursor.getString(2),
                password = cursor.getString(3),
                createdDate = LocalDateTime.parse(cursor.getString(4)),
                lastModifiedDate = LocalDateTime.parse(cursor.getString(5))
            )
            lista.add(user)
        }
        cursor.close()
        return lista
    }
}
