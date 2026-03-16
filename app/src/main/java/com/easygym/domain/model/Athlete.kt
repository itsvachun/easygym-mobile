package com.easygym.domain.model

import java.time.LocalDate

data class Athlete(

    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val enabled: Boolean,
    val birthDate: LocalDate,
    val taxCode: String,
    val address: String,
    val city: String,
    val postalCode: String,
    val medicalExpDate: LocalDate,
    val medicalStatus: String,

    )