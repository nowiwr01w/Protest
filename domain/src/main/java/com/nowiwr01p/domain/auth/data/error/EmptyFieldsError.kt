package com.nowiwr01p.domain.auth.data.error

data class EmptyFieldsError(
    val emptyFields: List<AuthTextFieldType>
): AuthError {
    override val list = emptyFields
    override val message = "У вас ${emptyFields.size} незаполненных поля. " +
            "Заполните поля ${emptyFields.getFieldNames()}"
}