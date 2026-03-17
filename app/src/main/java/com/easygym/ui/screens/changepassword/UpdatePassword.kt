package com.easygym.ui.screens.changepassword

import com.easygym.data.remote.model.user.UpdatePasswordRequest

data class UpdatePassword(
    val oldPassword: String,
    val newPassword: String
) {
    fun toRequest(): UpdatePasswordRequest = UpdatePasswordRequest(
        oldPassword = oldPassword,
        newPassword = newPassword
    )
}