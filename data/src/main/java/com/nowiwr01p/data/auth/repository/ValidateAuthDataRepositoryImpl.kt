package com.nowiwr01p.data.auth.repository

import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.domain.auth.data.error.AuthTextFieldType
import com.nowiwr01p.domain.auth.data.error.AuthTextFieldType.*
import com.nowiwr01p.domain.auth.data.error.SignInError
import com.nowiwr01p.domain.auth.data.error.SignInError.*
import com.nowiwr01p.domain.auth.data.error.SignUpError
import com.nowiwr01p.domain.auth.data.error.SignUpError.*
import com.nowiwr01p.domain.auth.data.user.UserDataSignIn
import com.nowiwr01p.domain.auth.data.user.UserDataSignUp
import com.nowiwr01p.domain.auth.repository.ValidateAuthDataRepository
import com.nowiwr01p.domain.auth.validators.EmailValidator
import com.nowiwr01p.domain.auth.validators.PasswordValidator
import kotlinx.coroutines.withContext

class ValidateAuthDataRepositoryImpl(
    private val dispatchers: AppDispatchers,
    private val emailValidator: EmailValidator,
    private val passwordValidator: PasswordValidator
): ValidateAuthDataRepository {

    override suspend fun isSignInDataValid(userData: UserDataSignIn): SignInError? {
        return withContext(dispatchers.io) {
            val emptyFieldsList = mutableListOf<AuthTextFieldType>()

            if (userData.email.isEmpty()) emptyFieldsList.add(EMAIL)
            if (userData.password.isEmpty()) emptyFieldsList.add(PASSWORD)

            if (emptyFieldsList.isNotEmpty()) {
                return@withContext SignInEmptyFieldError(emptyFieldsList)
            }
            if (!emailValidator.validate(userData.email)) {
                return@withContext SignInInvalidEmailError()
            }
            if (!passwordValidator.validate(userData.password)) {
                return@withContext SignInIncorrectPasswordError()
            }

            null
        }
    }

    override suspend fun isSignUpDataValid(userData: UserDataSignUp): SignUpError? {
        return withContext(dispatchers.io) {
            val emptyFieldsList = mutableListOf<AuthTextFieldType>()

            if (userData.email.isEmpty()) emptyFieldsList.add(EMAIL)
            if (userData.password.isEmpty()) emptyFieldsList.add(PASSWORD)
            if (userData.passwordRepeated.isEmpty()) emptyFieldsList.add(PASSWORD_REPEAT)

            if (emptyFieldsList.isNotEmpty()) {
                return@withContext SignUpEmptyFieldError(emptyFieldsList)
            }
            if (!emailValidator.validate(userData.email)) {
                return@withContext SignUpInvalidEmailError()
            }
            if (!passwordValidator.validate(userData.password) || !passwordValidator.validate(userData.passwordRepeated)) {
                return@withContext SignUpIncorrectPasswordError()
            }
            if (userData.password != userData.passwordRepeated) {
                return@withContext SignUpNotEqualPasswordError()
            }

            null
        }
    }
}