package com.example.proyecto_final.user

import java.time.LocalDateTime

data class User (
    var idUser: Int = 0,
    var idClient: Int,
    var username: String,
    var password: String,
    var createdDate: LocalDateTime,
    var lastModifiedDate: LocalDateTime
)