package com.example.proyecto_final.DetalleVenta

data class DetalleVenta (
    var idDetalleVenta: Int = 0,
    var idVenta: Int,
    var idProducto: Int,
    var cantidad: Int,
    var precioUnitario: Double,
    var subTotal: Double
)
