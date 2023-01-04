package com.nowiwr01p.domain.extensions

import com.google.firebase.auth.AuthResult
import com.google.firebase.database.DataSnapshot
import com.nowiwr01p.domain.auth.data.user.User

fun AuthResult.toUser() = User(
    id = user?.uid.orEmpty(),
    email = user?.email.orEmpty(),
    verified = user?.isEmailVerified ?: false
)

fun DataSnapshot.getAccount() = getValue(User::class.java)!!