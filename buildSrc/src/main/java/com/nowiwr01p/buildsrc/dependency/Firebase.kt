package com.nowiwr01p.buildsrc.dependency

import com.nowiwr01p.buildsrc.dependency.Firebase.Version.AUTH
import com.nowiwr01p.buildsrc.dependency.Firebase.Version.DATABASE

object Firebase {

    const val FIREBASE_AUTH = "com.google.firebase:firebase-auth-ktx:$AUTH"
    const val FIREBASE_DATABASE = "com.google.firebase:firebase-database-ktx:$DATABASE"

    private object Version {
        const val AUTH = "21.1.0"
        const val DATABASE = "20.1.0"
    }
}