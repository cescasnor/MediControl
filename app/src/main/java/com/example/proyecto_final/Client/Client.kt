package com.example.proyecto_final.Client

import java.time.LocalDateTime

data class Client (
    var idClient: Int = 0,
    var firstName: String,
    var lastName: String,
    var createdDate: LocalDateTime,
    var lastModifiedDate: LocalDateTime
)
