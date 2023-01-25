package com.nowiwr01p.core.extenstion

import com.google.firebase.auth.AuthResult
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.getValue
import com.nowiwr01p.core.model.User

fun AuthResult.toUser() = User(
    id = user?.uid.orEmpty(),
    email = user?.email.orEmpty(),
    verified = user?.isEmailVerified ?: false
)

fun DataSnapshot.getAccount() = getValue(User::class.java)!!

inline fun <reified T> createEventListener(crossinline callback: (user: T) -> Unit) = object : ValueEventListener {
    override fun onCancelled(p0: DatabaseError) {}

    override fun onDataChange(snapshot: DataSnapshot) {
        snapshot.getValue<T>().let { user ->
            if (user != null) {
                callback.invoke(user)
            } else {
                throw IllegalStateException("createUserEventListener(), value == null")
            }
        }
    }
}