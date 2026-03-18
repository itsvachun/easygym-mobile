package com.easygym.ui.screens.createuser

import com.easygym.ui.navigation.UserRole
import java.time.LocalDate

sealed class CreateUser(
    open val role: UserRole,
    open var firstName: String,
    open var lastname: String,
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
        override var lastname: String = "",
        override var email: String = "",
        override var password: String = "",
        override var phone: String = "",
    ) : CreateUser(UserRole.ATHLETE, firstName, lastname, email, password, phone)


    data class Coach(
        val bio: String = "",

        override var firstName: String = "",
        override var lastname: String = "",
        override var email: String = "",
        override var password: String = "",
        override var phone: String = "",
    ) : CreateUser(UserRole.COACH, firstName, lastname, email, password, phone)
}

