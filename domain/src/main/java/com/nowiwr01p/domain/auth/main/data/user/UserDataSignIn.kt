package com.nowiwr01p.domain.auth.main.data.user

data class UserDataSignIn(
    override val email: String,
    override val password: String
): UserData