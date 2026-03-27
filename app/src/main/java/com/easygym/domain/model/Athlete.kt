package com.easygym.domain.model

import com.easygym.utils.enums.MedicalStatus
import com.easygym.utils.pagination.Searchable
import java.time.LocalDate

data class Athlete(
    override val firstName: String,
    override val lastName: String,
    val email: String,
    val phone: String,
    val enabled: Boolean,
    val birthDate: LocalDate,
    val taxCode: String,
    val address: String,
    val city: String,
    val postalCode: String,
    val medicalExpDate: LocalDate,
    val medicalStatus: MedicalStatus
) : Searchable