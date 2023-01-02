package com.nowiwr01p.domain.auth.data.error

import com.nowiwr01p.domain.auth.data.error.AuthTextFieldType.*

sealed class SignUpError: AuthError {

    abstract override val list: List<AuthTextFieldType>
    abstract override val message: String

    class SignUpInvalidEmailError: SignUpError() {
        override val list = listOf(EMAIL)
        override val message = "Неверный формат электронной почты"
    }

    class SignUpIncorrectPasswordError: SignUpError() {
        override val list = listOf(PASSWORD, PASSWORD_REPEAT)
        override val message = "Неверный пароль. Он должен содержать 8 символов " +
                "и хотя бы одну заглавную букву, цифру и спец символ"
    }

    class SignUpNotEqualPasswordError: SignUpError() {
        override val list = listOf(PASSWORD, PASSWORD_REPEAT)
        override val message = "Пароли должны совпадать"
    }

    data class SignUpEmptyFieldError(
        val emptyFields: List<AuthTextFieldType>
    ): SignUpError() {
        override val list = emptyFields
        override val message = "У вас ${emptyFields.size} незаполненных поля. " +
                "Заполните поля ${emptyFields.getFieldNames()}"
    }
}