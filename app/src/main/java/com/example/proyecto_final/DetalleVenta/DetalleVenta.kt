package com.example.proyecto_final.DetalleVenta

import java.time.LocalDateTime

data class DetalleVenta (
    var idDetalleVenta: Int = 0,
    var idVenta: Int,
    var idProducto: Int,
    var cantidad: Int,
    var precioUnitario: Double,
    var subTotal: Double
)
