package com.nowiwr01p.domain.auth.main.data.error

import com.nowiwr01p.domain.auth.main.data.error.AuthTextFieldType.*

sealed class EmailError(
    override val list: List<AuthTextFieldType> = listOf(EMAIL),
    override val message: String = "Аккаунт с такой почтой не зарегистрирован"
): AuthError {

    data class WrongEmailFormatError(
        override val list: List<AuthTextFieldType> = listOf(EMAIL),
        override val message: String = "Неверный формат электронной почты"
    ): EmailError(list, message)

    data class WeakDomainError(
        override val list: List<AuthTextFieldType> = listOf(EMAIL),
        override val message: String = "Не используйте почты государственных компаний"
    ): EmailError(list, message)
}