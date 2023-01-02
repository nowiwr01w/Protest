package com.nowiwr01p.data.auth.validators

import com.nowiwr01p.domain.auth.validators.PasswordValidator

/**
 * [PasswordValidator] based on regular expression.
 * Requirements:
 * - 8+ characters,
 * - min 1 uppercase,
 * - min 1 number
 * - min 1 special character
 * */
class PasswordValidatorImpl : PasswordValidator {

    private val numberRegex = ".*[0-9].*".toRegex()
    private val upperCaseRegex = ".*[A-Z].*".toRegex()
    private val specialCharRegex = ".*[@#!$%^&+=].*".toRegex()

    override fun validate(value: String) = value.length >= 8
            && value.matches(numberRegex)
            && value.matches(upperCaseRegex)
            && value.matches(specialCharRegex)
}