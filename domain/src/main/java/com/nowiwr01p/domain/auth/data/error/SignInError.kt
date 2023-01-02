package com.nowiwr01p.domain.auth.data.error

import com.nowiwr01p.domain.auth.data.error.AuthTextFieldType.*

sealed class SignInError: AuthError {

    abstract override val list: List<AuthTextFieldType>
    abstract override val message: String

    class SignInInvalidEmailError: SignInError() {
        override val list = listOf(EMAIL)
        override val message = "Неверный формат электронной почты"
    }

    class SignInIncorrectPasswordError: SignInError() {
        override val list = listOf(PASSWORD, PASSWORD_REPEAT)
        override val message = "Неверный пароль. Он должен содержать 8 символов " +
                "и хотя бы одну заглавную букву, цифру и спец символ"
    }

    data class SignInEmptyFieldError(
        val emptyFields: List<AuthTextFieldType>
    ): SignInError() {
        override val list = emptyFields
        override val message = "У вас ${emptyFields.size} незаполненных поля. " +
                "Заполните поля ${emptyFields.getFieldNames()}"
    }
}