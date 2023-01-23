package com.nowiwr01p.domain.auth.main.data.user

sealed interface UserData {
    val email: String
    val password: String
}