package com.example.proyecto_final.Address

import java.time.LocalDateTime

data class Address (
    var idAddress: Int = 0,
    var idClient: Int ,
    var calle: String,
    var number: String,
    var reference: String,
    var aditionalInfo: String,
    var createdDate: LocalDateTime,
    var lastModifiedDate: LocalDateTime
)