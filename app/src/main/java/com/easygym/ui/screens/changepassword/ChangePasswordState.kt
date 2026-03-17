package com.easygym.ui.screens.changepassword

data class ChangePasswordState(
    val updatePassword: UpdatePassword = UpdatePassword("", ""),
    val confirmNewPassword: String = "",
    val errorMessage: String? = null
)