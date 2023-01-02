package com.nowiwr01p.domain.auth.data.error

import com.nowiwr01p.domain.auth.data.error.AuthTextFieldType.*

fun List<AuthTextFieldType>.getFieldNames() = mutableListOf<String>().apply {
    this@getFieldNames.forEach {
        when (it) {
            EMAIL -> add("email")
            PASSWORD -> add("password")
            PASSWORD_REPEAT -> add("password_repeat")
        }
    }
}

enum class AuthTextFieldType {
    EMAIL, PASSWORD, PASSWORD_REPEAT
}