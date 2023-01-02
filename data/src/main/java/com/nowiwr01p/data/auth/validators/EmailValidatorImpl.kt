package com.nowiwr01p.data.auth.validators

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

    override fun validate(value: String) = pattern.matcher(value).matches()
}