package com.nowiwr01p.domain.auth.main.data.error

interface AuthError {
    val list: List<AuthTextFieldType>
    val message: String
}