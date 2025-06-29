package com.example.proyecto_final.Product

data class Product (
    var idProduct: Int = 0,
    var sizeProduct: String,
    var name: String,
    var description: String,
    var price: Double,
    var stock: Int = 0,
    var category: String,
    var image: Int
)
