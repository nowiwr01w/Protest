package com.nowiwr01p.domain.auth.main.data.user

data class UserDataSignUp(
    override val email: String,
    override val password: String,
    val passwordRepeated: String
): UserData