package com.example.proyecto_final.Venta

import java.time.LocalDateTime

data class Venta (
    var idVenta: Int = 0,
    var idCliente: Int,
    var fecha: LocalDateTime,
    var totalAmount: Double,
)
