package com.nowiwr01p.domain.auth.data.error

import com.nowiwr01p.domain.auth.data.error.AuthTextFieldType.*

sealed class PasswordError(
    override val list: List<AuthTextFieldType> = listOf(PASSWORD, PASSWORD_REPEAT),
    override val message: String = "Неверный пароль"
): AuthError {

    data class ShortPasswordError(
        override val list: List<AuthTextFieldType> = listOf(PASSWORD, PASSWORD_REPEAT),
        override val message: String = "Пароль должен содержать минимум 8 символов"
    ): PasswordError(list, message)

    data class PasswordNumberError(
        override val list: List<AuthTextFieldType> = listOf(PASSWORD, PASSWORD_REPEAT),
        override val message: String = "Пароль должен содержать хотя бы одну цифру"
    ): PasswordError(list, message)

    data class PasswordUpperLetterError(
        override val list: List<AuthTextFieldType> = listOf(PASSWORD, PASSWORD_REPEAT),
        override val message: String = "Пароль должен содержать хотя бы одну заглавную букву"
    ): PasswordError(list, message)

    data class PasswordSpecialSymbolError(
        override val list: List<AuthTextFieldType> = listOf(PASSWORD, PASSWORD_REPEAT),
        override val message: String = "Пароль должен содержать хотя бы один специальный символ"
    ): PasswordError(list, message)

    data class PasswordMatchError(
        override val list: List<AuthTextFieldType> = listOf(PASSWORD, PASSWORD_REPEAT),
        override val message: String = "Пароли должны совпадать"
    ): PasswordError(list, message)
}