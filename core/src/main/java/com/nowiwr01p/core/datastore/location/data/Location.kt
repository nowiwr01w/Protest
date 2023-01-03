package com.nowiwr01p.core.datastore.location.data

sealed interface Location {
    val name: String
    val selected: Boolean
}