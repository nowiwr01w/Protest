package com.nowiwr01p.data.auth.validators

import com.nowiwr01p.domain.auth.data.error.EmailError.WeakDomainError
import com.nowiwr01p.domain.auth.data.error.EmailError.WrongEmailFormatError
import com.nowiwr01p.domain.auth.validators.EmailValidator
import java.util.regex.Pattern

class EmailValidatorImpl: EmailValidator {

    private val pattern = Pattern.compile(
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}" +
                "\\@" +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}" +
                "(" +
                "\\." +
                "[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25}" +
                ")+"
    )

    override fun validate(value: String) = when {
        isValidDomain(value).not() -> WeakDomainError()
        pattern.matcher(value).matches().not() -> WrongEmailFormatError()
        else -> null
    }

    private fun isValidDomain(value: String): Boolean {
        invalidDomainsList.forEach { domain ->
            if (value.contains(domain)) {
                return false
            }
        }
        return true
    }

    private val invalidDomainsList = listOf("@vk", "@mail", "@yandex")
}