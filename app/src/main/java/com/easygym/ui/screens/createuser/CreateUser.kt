package com.easygym.ui.screens.createuser

import com.easygym.data.remote.model.athlete.AthleteRequest
import com.easygym.data.remote.model.coach.CoachRequest
import com.easygym.utils.enums.UserRole
import java.time.LocalDate

sealed class CreateUser(
    open val role: UserRole,
    open var firstName: String,
    open var lastName: String,
    open var email: String,
    open var password: String,
    open var phone: String,
) {
    data class Athlete(
        val birthDate: LocalDate = LocalDate.now(),
        val taxCode: String = "",
        val address: String = "",
        val city: String = "",
        val postalCode: String = "",
        val medicalExpDate: LocalDate = LocalDate.now(),
        val notes: String = "",

        override var firstName: String = "",
        override var lastName: String = "",
        override var email: String = "",
        override var password: String = "",
        override var phone: String = "",
    ) : CreateUser(UserRole.ATHLETE, firstName, lastName, email, password, phone) {
        fun toRequest(
            groupId: String
        ) = AthleteRequest(
            firstName = firstName,
            lastName = lastName,
            birthDate = birthDate.toString(),
            taxCode = taxCode,
            email = email,
            password = password,
            phone = phone,
            address = address,
            city = city,
            postalCode = postalCode,
            medicalExpDate = medicalExpDate.toString(),
            groupId = groupId,
            notes = notes
        )
    }


    data class Coach(
        val bio: String = "",

        override var firstName: String = "",
        override var lastName: String = "",
        override var email: String = "",
        override var password: String = "",
        override var phone: String = "",
    ) : CreateUser(UserRole.COACH, firstName, lastName, email, password, phone) {
        fun toRequest(
            groupId: String
        ) = CoachRequest(
            firstName = firstName,
            lastName = lastName,
            email = email,
            bio = bio,
            password = password,
            groupId = groupId,
        )
    }
}

