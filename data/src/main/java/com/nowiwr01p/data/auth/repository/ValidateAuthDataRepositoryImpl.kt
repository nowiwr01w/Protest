package com.nowiwr01p.data.auth.repository

import com.nowiwr01p.domain.AppDispatchers
import com.nowiwr01p.domain.auth.data.error.AuthTextFieldType
import com.nowiwr01p.domain.auth.data.error.AuthTextFieldType.*
import com.nowiwr01p.domain.auth.data.error.EmptyFieldsError
import com.nowiwr01p.domain.auth.data.error.PasswordError.*
import com.nowiwr01p.domain.auth.data.user.UserData
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

    override suspend fun isAuthDataValid(userData: UserData) = withContext(dispatchers.io) {
        val empty = mutableListOf<AuthTextFieldType>()

        if (userData.email.isEmpty()) empty.add(EMAIL)
        if (userData.password.isEmpty()) empty.add(PASSWORD)
        if (userData is UserDataSignUp && userData.passwordRepeated.isEmpty()) empty.add(PASSWORD_REPEAT)

        if (empty.isNotEmpty()) {
            return@withContext EmptyFieldsError(empty)
        }

        emailValidator.validate(userData.email)?.let { emailError ->
            return@withContext emailError
        }
        passwordValidator.validate(userData.password)?.let { passwordError ->
            return@withContext passwordError
        }

        if (userData is UserDataSignUp && userData.password != userData.passwordRepeated) {
            return@withContext PasswordMatchError()
        }

        null
    }
}