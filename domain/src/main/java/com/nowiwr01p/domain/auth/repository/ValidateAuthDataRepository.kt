package com.nowiwr01p.domain.auth.repository

import com.nowiwr01p.domain.auth.data.error.SignInError
import com.nowiwr01p.domain.auth.data.error.SignUpError
import com.nowiwr01p.domain.auth.data.user.UserDataSignIn
import com.nowiwr01p.domain.auth.data.user.UserDataSignUp

interface ValidateAuthDataRepository {
    suspend fun isSignUpDataValid(userData: UserDataSignUp): SignUpError?
    suspend fun isSignInDataValid(userData: UserDataSignIn): SignInError?
}