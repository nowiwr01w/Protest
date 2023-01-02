package com.nowiwr01p.domain.auth.data.user

data class UserDataSignIn(
    override val email: String,
    override val password: String
): UserData