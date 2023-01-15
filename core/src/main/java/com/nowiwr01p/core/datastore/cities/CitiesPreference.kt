package com.nowiwr01p.core.datastore.cities

import com.nowiwr01p.core.datastore.cities.data.City
import kotlinx.serialization.Serializable

@Serializable
data class CitiesPreference(
    val selectedCity: City = City()
)