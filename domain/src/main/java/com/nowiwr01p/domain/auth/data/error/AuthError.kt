package com.nowiwr01p.domain.auth.data.error

interface AuthError {
    val list: List<AuthTextFieldType>
    val message: String
}