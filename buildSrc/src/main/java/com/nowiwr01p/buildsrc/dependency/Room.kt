package com.nowiwr01p.buildsrc.dependency

import com.nowiwr01p.buildsrc.dependency.Room.Version.ROOM

object Room {

    const val KTX = "androidx.room:room-ktx:$ROOM"
    const val RUNTIME = "androidx.room:room-runtime:$ROOM"
    const val COMPILER = "androidx.room:room-compiler:$ROOM"

    private object Version {
        const val ROOM = "2.4.3"
    }
}