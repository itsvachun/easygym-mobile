package com.easygym.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.easygym.domain.model.Athlete
import java.time.LocalDate
import java.util.*

@Entity(tableName = "athlete")
data class AthleteEntity(
    @PrimaryKey
    val id: String = UUID.randomUUID().toString(),
    val firstName: String,
    val lastName: String,
    val email: String,
    val phone: String,
    val enabled: Boolean,
    val birthDate: String,
    val taxCode: String,
    val address: String,
    val city: String,
    val postalCode: String,
    val medicalExpDate: String,
    val medicalStatus: String,
) {
    fun toDomain(): Athlete = Athlete(
        firstName, lastName, email, phone, enabled, LocalDate.parse(birthDate),
        taxCode, address, city, postalCode, LocalDate.parse(medicalExpDate), medicalStatus
    )
}