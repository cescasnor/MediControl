package com.example.proyecto_final.PayMethod

import com.example.proyecto_final.Client.Client
import java.time.LocalDate
import java.time.LocalDateTime

data class PayMethod (
    var idPayMethod: Int = 0,
    var idClient: Int ,
    var number: String,
    var ccv: String,
    var ownerName: String,
    var endDate: LocalDate,
    var createdDate: LocalDateTime,
    var lastModifiedDate: LocalDateTime
)