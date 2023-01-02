package com.nowiwr01p.domain.auth.data.user

sealed interface UserData {
    val email: String
    val password: String
}