package com.example.proyecto_final.Address

import com.example.proyecto_final.Client.Client
import java.time.LocalDate
import java.time.LocalDateTime

data class Address (
    var idAddress: Int = 0,
    var idClient: Client,
    var number: String,
    var ccv: String,
    var ownerName: String,
    var endDate: LocalDate,
    var createdDate: LocalDateTime,
    var lastModifiedDate: LocalDateTime
)